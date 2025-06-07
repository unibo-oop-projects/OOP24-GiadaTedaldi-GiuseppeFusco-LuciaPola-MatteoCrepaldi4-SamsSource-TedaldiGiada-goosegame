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

        initController();
    }

    private void initController() {
        this.view.updateScore(this.model.getScore());
        this.view.addStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                startNewGame();
            }
        });

        this.view.addColorButtonListener(Colors.GREEN, e -> handleButtonClick(Colors.GREEN));
        this.view.addColorButtonListener(Colors.RED, e -> handleButtonClick(Colors.RED));
        this.view.addColorButtonListener(Colors.YELLOW, e -> handleButtonClick(Colors.YELLOW));
        this.view.addColorButtonListener(Colors.BLUE, e -> handleButtonClick(Colors.BLUE));
    }

    private void startNewGame() {
        this.model.startGame();
        this.view.setGameActive(true);
        this.view.updateLevel(this.model.getLevel());
        this.view.setButtonsEnabled(false);
        this.view.showMessage(it.unibo.goosegame.utilities.HonkMandMessages.WATCH_SEQUENCE, false);

        final Timer timer = new Timer(it.unibo.goosegame.utilities.HonkMandConstants.SEQUENCE_START_DELAY, e -> {
            ((Timer) e.getSource()).stop();
            playSequence();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void playSequence() {
        this.isShowingSequence = true;
        this.view.setButtonsEnabled(false);
        final List<Colors> sequence = this.model.getSequence();

        if (this.sequenceTimer != null && this.sequenceTimer.isRunning()) {
            this.sequenceTimer.stop();
        }

        final int[] index = {0};

        this.sequenceTimer = new Timer(it.unibo.goosegame.utilities.HonkMandConstants.SEQUENCE_STEP_DELAY, null);
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

        this.sequenceTimer.addActionListener(sequenceAction);
        this.sequenceTimer.setRepeats(true);
        this.sequenceTimer.start();
    }

    private void handleButtonClick(final Colors colorId) {
        if (this.isShowingSequence) {
            return;
        }
        if (this.model.getGameState() != GameState.ONGOING) {
            return;
        }

        this.view.lightUpButton(colorId, it.unibo.goosegame.utilities.HonkMandConstants.BUTTON_CLICK_LIGHT_DURATION);
        final HonkMandModel.InputResult result = this.model.checkPlayerInput(colorId);

        switch (result) {
            case CORRECT:
                // No action, wait for more input
                break;
            case NEXT_ROUND:
                this.view.showMessage(it.unibo.goosegame.utilities.HonkMandMessages.CORRECT, false);
                this.view.updateScore(model.getScore());
                this.view.setButtonsEnabled(false);
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
                this.view.showMessage(it.unibo.goosegame.utilities.HonkMandMessages.WIN, false);
                this.view.setButtonsEnabled(false);
                celebrateVictory(() -> this.view.showGameOverPanel(true));
                break;
            case GAME_OVER:
                this.view.showMessage(it.unibo.goosegame.utilities.HonkMandMessages.GAME_OVER, true);
                this.view.setButtonsEnabled(false);
                final Timer overTimer = new Timer(it.unibo.goosegame.utilities.HonkMandConstants.NEXT_ROUND_DELAY, e -> {
                    this.view.showGameOverPanel(false);
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
