package it.unibo.goosegame.controller.herdinghound;

import it.unibo.goosegame.model.minigames.herdinghound.impl.DogImpl;
import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModelImpl;
import it.unibo.goosegame.view.minigames.herdinghound.impl.HerdingHoundViewImpl;
import it.unibo.goosegame.view.minigames.herdinghound.impl.RightPanelImpl;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.utilities.Position;

import javax.swing.JFrame;
import javax.swing.Timer;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

/**
 * Controller for the Herding Hound minigame.
 * Handles user input, game logic, and updates the view and right panel.
 */
public class HerdingHoundController {
    private static final int DOG_ALERT_DELAY = 1_000;
    private static final int DOG_OTHER_DELAY_BASE = 2_000;
    private static final int DOG_OTHER_DELAY_RANDOM = 3;

    private final HerdingHoundModelImpl model;
    private final HerdingHoundViewImpl view;
    private final JFrame frame;
    private final RightPanelImpl rightPanel;
    private Timer dogStateTimer;
    private Timer gameTimer;
    private final Random rnd = new Random();
    private boolean spacePressed;
    private boolean gameActive;

    /**
     * Constructs the controller, sets up listeners and connects model, view, and right panel.
     * @param model the game model
     * @param view the game view
     * @param frame the main JFrame
     * @param rightPanel the right panel
     */
    @SuppressFBWarnings(
    value = "EI2",
    justification = "Controller uses externally provided references to model, view, frame, and panel."
    + "Assumes trusted injection without copying."
        )
    public HerdingHoundController(
            final HerdingHoundModelImpl model,
            final HerdingHoundViewImpl view,
            final JFrame frame,
            final RightPanelImpl rightPanel
    ) {
        this.model = model;
        this.view = view;
        this.frame = frame;
        this.rightPanel = rightPanel;
        setupKeyListener();
    }

    private void setupKeyListener() {
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

    private void endGame() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        if (dogStateTimer != null) {
            dogStateTimer.stop();
        }
        final boolean hasWon = model.getGameState() == GameState.WON;
        view.startBlinking(frame, hasWon); // blink before showing the end screen
        view.setFocusable(false);
    }

    // --- GETTER per la View e il RightPanel ---
    public int getGridSize() {
        return model.getGrid();
    }

    public Position getGoosePosition() {
        return model.getGoose().getCoord();
    }

    public DogImpl.State getDogState() {
        return model.getDog().getState();
    }

    public List<Position> getDogVisibleArea() {
        return model.getDog().getVisibleArea();
    }

    public Position getDogPosition() {
        return model.getDog().getCoord();
    }

    public List<Position> getVisibleCells() {
        return model.getVisible();
    }

    public List<Position> getShadows() {
        return model.getShadows();
    }

    public List<Position> getBoxes() {
        return model.getBoxes();
    }

    public long getRemainingTime() {
        return model.getRemainingTime();
    }
}
