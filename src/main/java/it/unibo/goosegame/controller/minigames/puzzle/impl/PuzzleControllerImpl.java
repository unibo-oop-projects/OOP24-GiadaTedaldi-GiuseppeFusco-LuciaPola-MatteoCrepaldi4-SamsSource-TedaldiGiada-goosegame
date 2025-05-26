package it.unibo.goosegame.controller.minigames.puzzle.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

import it.unibo.goosegame.controller.minigames.puzzle.api.PuzzleController;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.model.minigames.puzzle.api.PuzzleModel;
import it.unibo.goosegame.model.minigames.puzzle.impl.PuzzleModelImpl;
import it.unibo.goosegame.utilities.Position;
import it.unibo.goosegame.view.minigames.puzzle.api.PuzzleView;
import it.unibo.goosegame.view.minigames.puzzle.impl.PuzzleViewImpl;

/**
 * Implementation of the {@link PuzzleController} interface.
 * It manages the communication between the model and the view of a Puzzle minigame.
 */
public class PuzzleControllerImpl implements PuzzleController {
    private static final int INIT_TIME = 150;
    private final PuzzleModel model;
    private final PuzzleView view;
    private Timer gameTimer;
    private int timeLeft;

    /**
     * Constructs a new instance of {@link PuzzleControllerImpl}.
     * 
     */
    public PuzzleControllerImpl() {
        this.model = new PuzzleModelImpl();
        this.view = new PuzzleViewImpl();
        this.timeLeft = INIT_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        this.view.setController(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clickHandler(final Position pos) {
        if (this.model.hit(pos)) {
            this.view.updateView();
            this.checkGameOver();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void timeOver() {
        this.model.setTimeOver(true);
        this.checkGameOver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shufflePuzzle() {
        this.model.shuffle();
        this.startTimer();
    }

    private void startTimer() {
        this.updateViewTimer();
        if (this.gameTimer != null && this.gameTimer.isRunning()) {
            this.gameTimer.stop();
        }
        this.gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                timeLeft--;
                updateViewTimer();
                if (timeLeft <= 0) {
                    gameTimer.stop();
                    timeOver();
                }
            }
        });
        this.gameTimer.start();
    }
    /**
     * Updates the timer label on the view with the current time left.
     */
    private void updateViewTimer() {
        final int min = this.timeLeft / 60;
        final int sec = this.timeLeft % 60;
        final String timeString = String.format("Time: %02d:%02d", min, sec);
        this.view.updateTimerLabel(timeString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Position, Integer> getGridData() {
        return new HashMap<>(this.model.getGrid());
    }

    /**
     * Checks whether the game has reached a conclusion (either win or loss),
     * it shows the apporpiate final.
     */
    private void checkGameOver() {
        final GameState state = this.model.getGameState();
        if (state == GameState.WON || state == GameState.LOST) {
            this.view.showResultMessage(state == GameState.WON);
            this.stopTimer();
            this.view.endGame();
        }
    }

    /**
     * Stops the game timer if it is currently running.
     */
    private void stopTimer() {
        if (this.gameTimer != null && this.gameTimer.isRunning()) {
            this.gameTimer.stop();
        }
    }
}
