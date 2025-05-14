package it.unibo.goosegame.controller;

import it.unibo.goosegame.model.minigames.herdinghound.impl.DogImpl;
import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.view.HerdingHoundView;
import it.unibo.goosegame.view.RightPanel;

import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Controller for the Herding Hound minigame.
 * Handles user input, game logic, and updates the view and right panel.
 */
public class HerdingHoundController {
    private final HerdingHoundModel model;
    private final HerdingHoundView view;
    private final JFrame frame;
    private final RightPanel rightPanel;
    private Timer dogStateTimer;
    private Timer gameTimer;
    private final Random rnd = new Random();
    private boolean spacePressed;
    private boolean gameActive;

    private static final int DOG_ALERT_DELAY = 1_000;
    private static final int DOG_OTHER_DELAY_BASE = 2_000;
    private static final int DOG_OTHER_DELAY_RANDOM = 3;

    /**
     * Constructs the controller, sets up listeners and connects model, view, and right panel.
     * @param model the game model
     * @param view the game view
     * @param frame the main JFrame
     * @param rightPanel the right panel (timer, dog state)
     */
    public HerdingHoundController(final HerdingHoundModel model, final HerdingHoundView view, final JFrame frame, final RightPanel rightPanel) {
        this.model = model;
        this.view = view;
        this.frame = frame;
        this.rightPanel = rightPanel;

        // Add key listener for goose movement
        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                if (!gameActive || view.isCountdownActive()) {
                    return;
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !spacePressed && !model.isOver()) {
                    spacePressed = true;
                    model.nextGooseMove();
                    view.updateView();
                    rightPanel.updatePanel();
                    if (model.isOver()) {
                        endGame();
                    }
                }
            }
            @Override
            public void keyReleased(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    spacePressed = false;
                }
            }
        });
        view.setFocusable(true);
        view.requestFocusInWindow();
    }

    /**
     * Starts the game logic: enables input, starts dog state and game timers.
     */
    public void startGame() {
        gameActive = true;
        scheduleNextDogState(model.getDog().getState());
        gameTimer = new Timer(1000, (final java.awt.event.ActionEvent e) -> {
            view.updateView();
            rightPanel.updatePanel();
            if (model.getGameState() != GameState.ONGOING) {
                endGame();
            }
        });
        gameTimer.setInitialDelay(0);
        gameTimer.start();
    }

    /**
     * Schedules the next dog state change with a random delay, unless the game is over.
     * @param currentState the current state of the dog
     */
    private void scheduleNextDogState(final DogImpl.State currentState) {
        if (model.isOver()) {
            return;
        }
        final int delay = (currentState == DogImpl.State.ALERT)
                    ? DOG_ALERT_DELAY
                    : DOG_OTHER_DELAY_BASE + rnd.nextInt(DOG_OTHER_DELAY_RANDOM) * 1_000;
        if (dogStateTimer != null) {
            dogStateTimer.stop();
        }
        dogStateTimer = new Timer(delay, (final java.awt.event.ActionEvent e) -> {
            model.nextDogState();
            view.updateView();
            rightPanel.updatePanel();
            if (model.isOver()) {
                endGame();
            } else {
                scheduleNextDogState(model.getDog().getState());
            }
        });
        dogStateTimer.setRepeats(false);
        dogStateTimer.start();
    }

    /**
     * Ends the game: stops timers, triggers end animation and disables input.
     */
    private void endGame() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        if (dogStateTimer != null) {
            dogStateTimer.stop();
        }
        final boolean hasWon = model.getGameState() == GameState.WON;
        view.startBlinking(frame, hasWon); // lampeggia prima di mostrare la schermata finale
        view.setFocusable(false);
    }
}
