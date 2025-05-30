package it.unibo.goosegame.controller.minigames.honkmand;

import it.unibo.goosegame.model.minigames.honkmand.api.HonkMandModel;
import it.unibo.goosegame.model.minigames.honkmand.impl.HonkMandModelImpl;
import it.unibo.goosegame.view.minigames.honkmand.api.HonkMandView;
import it.unibo.goosegame.view.minigames.honkmand.impl.HonkMandViewImpl;
import it.unibo.goosegame.view.minigames.honkmand.impl.HonkMandFrameImpl;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.utilities.Colors;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controller for the HonkMand (Simon Game) minigame.
 * Handles user input, game logic, and updates the view.
 * Now responsible for initializing and showing the game frame, model, and view.
 */
public class HonkMandController {
    private HonkMandModel model;
    private HonkMandView view;
    private Timer sequenceTimer;
    private boolean isShowingSequence;

    /**
     * Constructor for the HonkMandController.
     */
    public HonkMandController() {
        //empty, all in StartGame ()
    }

    /**
     * Starts the game by initializing the model, view, and frame.
     */
    public void startGame() {
        final HonkMandFrameImpl frame = new HonkMandFrameImpl();
        this.model = new HonkMandModelImpl();
        this.view = new HonkMandViewImpl();

        this.view.setFrameRef(frame);
        frame.setupGamePanel((JPanel) this.view);
        frame.setVisible(true);

        // Wiring controller, model, view (game logic)
        initController();
    }

    private void initController() {
        view.updateScore(model.getScore());
        view.addStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                startNewGame();
            }
        });

        // Add listeners to color buttons
        view.addColorButtonListener(Colors.GREEN, e -> handleButtonClick(Colors.GREEN));
        view.addColorButtonListener(Colors.RED, e -> handleButtonClick(Colors.RED));
        view.addColorButtonListener(Colors.YELLOW, e -> handleButtonClick(Colors.YELLOW));
        view.addColorButtonListener(Colors.BLUE, e -> handleButtonClick(Colors.BLUE));
    }

    private void startNewGame() {
        model.startGame();
        view.setGameActive(true);
        view.updateLevel(model.getLevel());
        view.setButtonsEnabled(false);
        view.showMessage(it.unibo.goosegame.utilities.HonkMandMessages.WATCH_SEQUENCE, false);

        // Use a Timer to play the sequence after a short delay
        final Timer timer = new Timer(it.unibo.goosegame.utilities.HonkMandConstants.SEQUENCE_START_DELAY, e -> {
            ((Timer) e.getSource()).stop();
            playSequence();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void playSequence() {
        isShowingSequence = true;
        view.setButtonsEnabled(false);
        final List<Colors> sequence = model.getSequence();

        if (sequenceTimer != null && sequenceTimer.isRunning()) {
            sequenceTimer.stop();
        }

        final int[] index = {0};

        sequenceTimer = new Timer(it.unibo.goosegame.utilities.HonkMandConstants.SEQUENCE_STEP_DELAY, null);
        final ActionListener sequenceAction = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (index[0] < sequence.size()) {
                    final Colors color = sequence.get(index[0]);
                    view.lightUpButton(color, it.unibo.goosegame.utilities.HonkMandConstants.BUTTON_LIGHT_DURATION);
                    index[0]++;
                } else {
                    sequenceTimer.stop();
                    final Timer enableButtonsTimer = new Timer(
                        it.unibo.goosegame.utilities.HonkMandConstants.BUTTON_ENABLE_DELAY, event -> {
                        isShowingSequence = false;
                        view.setButtonsEnabled(true);
                        view.showMessage(it.unibo.goosegame.utilities.HonkMandMessages.YOUR_TURN, false);
                        ((Timer) event.getSource()).stop();
                    });
                    enableButtonsTimer.setRepeats(false);
                    enableButtonsTimer.start();
                }
            }
        };

        sequenceTimer.addActionListener(sequenceAction);
        sequenceTimer.setRepeats(true);
        sequenceTimer.start();
    }

    private void handleButtonClick(final Colors colorId) {
        if (isShowingSequence) {
            return;
        }
        if (model.getGameState() != GameState.ONGOING) {
            return;
        }

        view.lightUpButton(colorId, it.unibo.goosegame.utilities.HonkMandConstants.BUTTON_CLICK_LIGHT_DURATION);
        final HonkMandModel.InputResult result = model.checkPlayerInput(colorId);

        switch (result) {
            case CORRECT:
                // No action, wait for more input
                break;
            case NEXT_ROUND:
                view.showMessage(it.unibo.goosegame.utilities.HonkMandMessages.CORRECT, false);
                view.updateScore(model.getScore());
                view.setButtonsEnabled(false);
                final Timer nextRoundTimer = new Timer(it.unibo.goosegame.utilities.HonkMandConstants.NEXT_ROUND_DELAY, e -> {
                    model.nextRound();
                    view.updateLevel(model.getLevel());
                    view.showMessage(it.unibo.goosegame.utilities.HonkMandMessages.LEVEL_LABEL + model.getLevel(), false);
                    final Timer playSequenceTimer = new Timer(
                        it.unibo.goosegame.utilities.HonkMandConstants.SEQUENCE_START_DELAY, e2 -> playSequence());
                    playSequenceTimer.setRepeats(false);
                    playSequenceTimer.start();
                    ((Timer) e.getSource()).stop();
                });
                nextRoundTimer.setRepeats(false);
                nextRoundTimer.start();
                break;
            case GAME_WIN:
                view.showMessage(it.unibo.goosegame.utilities.HonkMandMessages.WIN, false);
                view.setButtonsEnabled(false);
                celebrateVictory(() -> view.showGameOverPanel(true));
                break;
            case GAME_OVER:
                view.showMessage(it.unibo.goosegame.utilities.HonkMandMessages.GAME_OVER, true);
                view.setButtonsEnabled(false);
                final Timer overTimer = new Timer(it.unibo.goosegame.utilities.HonkMandConstants.NEXT_ROUND_DELAY, e -> {
                    view.showGameOverPanel(false);
                    ((Timer) e.getSource()).stop();
                });
                overTimer.setRepeats(false);
                overTimer.start();
                break;
        }
    }

    private void celebrateVictory(final Runnable onEnd) {
        final Colors[] colors = Colors.values();
        final int total = colors.length;
        final int[] index = {0};

        final Timer celebrationTimer = new Timer(
            it.unibo.goosegame.utilities.HonkMandConstants.CELEBRATION_STEP_DELAY, null);
        celebrationTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (index[0] > 0) {
                    final Colors prevColor = colors[index[0] - 1];
                    view.lightUpButton(prevColor, 0);
                }
                if (index[0] >= total) {
                    ((Timer) e.getSource()).stop();
                    if (onEnd != null) {
                        onEnd.run();
                    }
                    return;
                }
                view.lightUpButton(colors[index[0]],
                    it.unibo.goosegame.utilities.HonkMandConstants.BUTTON_CLICK_LIGHT_DURATION);
                index[0]++;
            }
        });
        celebrationTimer.setRepeats(true);
        celebrationTimer.start();
    }

    /**
     * Returns the state of the game.
     * @return GameState
     */
    public GameState getGameState() {
        return this.model.getGameState();
    }
}
