package it.unibo.goosegame.controller.herdinghound;

import it.unibo.goosegame.model.minigames.herdinghound.api.Dog.State;
import it.unibo.goosegame.model.minigames.herdinghound.api.HerdingHoundModel;
import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModelImpl;
import it.unibo.goosegame.view.minigames.herdinghound.api.HerdingHoundView;
import it.unibo.goosegame.view.minigames.herdinghound.impl.HerdingHoundFrame;
import it.unibo.goosegame.view.minigames.herdinghound.impl.HerdingHoundViewImpl;
import it.unibo.goosegame.view.minigames.herdinghound.impl.RightPanelImpl;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.utilities.Position;

import javax.swing.Timer;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

/**
 * Controller for the Herding Hound minigame.
 * Handles user input, game logic, and updates the view and right panel.
 * Now responsible for initializing and showing the game frame, model, and view.
 */
public class HerdingHoundController {
    private static final int DOG_ALERT_DELAY = 1_000;
    private static final int DOG_OTHER_DELAY_BASE = 2_000;
    private static final int DOG_OTHER_DELAY_RANDOM = 3;
    private static final int GRID_SIZE = 31;

    private HerdingHoundModel model;
    private HerdingHoundView view;
    private HerdingHoundFrame frame;
    private RightPanelImpl rightPanel;
    private Timer dogStateTimer;
    private Timer gameTimer;
    private final Random rnd = new Random();
    private boolean spacePressed;
    private boolean gameActive;

    /**
     * Default constructor. Use startGame() to initialize and show the game.
     */
    public HerdingHoundController() {
        // No initialization here; everything is done in startGame()
    }

    /**
     * Initializes and shows the game. Call this to start the Herding Hound minigame.
     */
    public void startGame() {
        // Initialize model, view, right panel, and frame
        this.model = new HerdingHoundModelImpl(GRID_SIZE);
        this.view = new HerdingHoundViewImpl();
        this.rightPanel = new RightPanelImpl();
        this.frame = new HerdingHoundFrame();

        // Wire up controller to view and right panel
        this.view.setController(this);
        this.rightPanel.setController(this);

        // Set up the frame with the view and right panel
        this.frame.setupGamePanels((Component) this.view, (Component)this.rightPanel);
        this.frame.setVisible(true);

        // Set up key listener
        setupKeyListener();

        // Start the countdown, then start the game logic
        this.view.startCountdown(() -> {
            model.startGame();
            startGameLogic();
            view.requestFocusInWindow();
        });
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

    private void startGameLogic() {
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

    private void scheduleNextDogState(final State currentState) {
        if (model.isOver()) {
            return;
        }
        final int delay = (currentState == State.ALERT)
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

    /**
     * @return the grid size of the game
     */
    public int getGridSize() {
        return model.getGrid();
    }

    /**
     * @return the current position of the goose
     */
    public Position getGoosePosition() {
        return model.getGoose().getCoord();
    }

    /**
     * @return the current state of the dog
     */
    public State getDogState() {
        return model.getDog().getState();
    }

    /**
     * @return the area visible to the dog
     */
    public List<Position> getDogVisibleArea() {
        return model.getDog().getVisibleArea();
    }

    /**
     * @return the current position of the dog
     */
    public Position getDogPosition() {
        return model.getDog().getCoord();
    }

    /**
     * @return the list of visible cells
     */
    public List<Position> getVisibleCells() {
        return model.getVisible();
    }

    /**
     * @return the list of shadowed cells
     */
    public List<Position> getShadows() {
        return model.getShadows();
    }

    /**
     * @return the list of box positions
     */
    public List<Position> getBoxes() {
        return model.getBoxes();
    }

    /**
     * @return the remaining time in the game
     */
    public long getRemainingTime() {
        return model.getRemainingTime();
    }
}
