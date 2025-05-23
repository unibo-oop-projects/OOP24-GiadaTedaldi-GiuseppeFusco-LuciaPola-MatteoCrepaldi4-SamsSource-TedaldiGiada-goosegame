package it.unibo.goosegame.controller.honkmand;

import it.unibo.goosegame.model.minigames.honkmand.api.HonkMandModel;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.utilities.Colors;
import it.unibo.goosegame.view.minigames.honkmand.api.HonkMandView;

import javax.swing.Timer;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controller for the HonkMand (Simon Game) minigame.
 * Handles user input, game logic, and updates the view.
 */
public class HonkMandController {
    /**
     * References to model and view are intentionally stored as mutable references provided externally.
     * This controller does not assume responsibility for external modifications to these objects.
     */
    private final HonkMandModel model;
    private final HonkMandView view;
    private Timer sequenceTimer;
    private boolean isShowingSequence;

    /**
     * Constructor. Connects model and view and initializes listeners.
     * @param model the game logic model
     * @param view the game view
     */
    @SuppressFBWarnings(value = "EI2",
     justification = "Controller does not modify external view/model and assumes trusted injection")
    public HonkMandController(final HonkMandModel model, final HonkMandView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    /**
     * Initializes the view and button listeners.
     */
    private void initController() {
        view.updateScore(model.getScore());
        view.addStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                startGame();
            }
        });

        // Add listeners to color buttons
        view.addColorButtonListener(Colors.GREEN, e -> handleButtonClick(Colors.GREEN));
        view.addColorButtonListener(Colors.RED, e -> handleButtonClick(Colors.RED));
        view.addColorButtonListener(Colors.YELLOW, e -> handleButtonClick(Colors.YELLOW));
        view.addColorButtonListener(Colors.BLUE, e -> handleButtonClick(Colors.BLUE));
    }

    /**
     * Starts a new game and shows the sequence.
     */
    private void startGame() {
        model.startGame();
        view.setGameActive(true);
        view.updateLevel(model.getLevel());
        view.setButtonsEnabled(false);
        view.showMessage(it.unibo.goosegame.utilities.HonkMandMessages.WATCH_SEQUENCE, false);

        // Use a Timer to play the sequence after a short delay
        final Timer timer = new Timer(it.unibo.goosegame.utilities.HonkMandConstants.SEQUENCE_START_DELAY, e -> {
            ((Timer) e.getSource()).stop(); // Stop the timer
            playSequence();
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Plays the color sequence with animation.
     */
    private void playSequence() {
        isShowingSequence = true; // <--- Set flag true at start
        view.setButtonsEnabled(false);
        final List<Colors> sequence = model.getSequence();

        // Use a Timer to simulate the delay between button lights
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
                    // Add a short delay before enabling the buttons
                    final Timer enableButtonsTimer = new Timer(
                        it.unibo.goosegame.utilities.HonkMandConstants.BUTTON_ENABLE_DELAY, event -> {
                        isShowingSequence = false; // <--- Set flag false when done
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

    /**
     * Handles the click on a color button.
     * @param colorId the selected color
     */
    private void handleButtonClick(final Colors colorId) {
        if (isShowingSequence) {
            return; // Ignore clicks during sequence display
        }
        // Use the new state management
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

    /**
     * Performs a victory celebration animation.
     * @param onEnd callback to execute at the end of the animation
     */
    private void celebrateVictory(final Runnable onEnd) {
        final Colors[] colors = Colors.values();
        final int total = colors.length;
        final int[] index = {0};

        final Timer celebrationTimer = new Timer(
            it.unibo.goosegame.utilities.HonkMandConstants.CELEBRATION_STEP_DELAY, null);
        celebrationTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                // Turn off the previous button (if not the first round)
                if (index[0] > 0) {
                    final Colors prevColor = colors[index[0] - 1];
                    // Restore the original color
                    view.lightUpButton(prevColor, 0);
                }
                // If the sequence is finished, stop the timer
                if (index[0] >= total) {
                    ((Timer) e.getSource()).stop();
                    if (onEnd != null) {
                        onEnd.run();
                    }
                    return;
                }
                // Light up the current button
                view.lightUpButton(colors[index[0]],
                    it.unibo.goosegame.utilities.HonkMandConstants.BUTTON_CLICK_LIGHT_DURATION);
                index[0]++;
            }
        });
        celebrationTimer.setRepeats(true);
        celebrationTimer.start();
    }
}
