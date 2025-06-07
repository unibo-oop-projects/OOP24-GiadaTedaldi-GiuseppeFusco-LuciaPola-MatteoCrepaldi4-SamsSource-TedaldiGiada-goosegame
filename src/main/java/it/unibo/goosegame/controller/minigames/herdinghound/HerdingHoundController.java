package it.unibo.goosegame.controller.minigames.herdinghound;

import it.unibo.goosegame.model.minigames.herdinghound.api.Dog.State;
import it.unibo.goosegame.model.minigames.herdinghound.api.HerdingHoundModel;
import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModelImpl;
import it.unibo.goosegame.view.minigames.herdinghound.api.HerdingHoundView;
import it.unibo.goosegame.view.minigames.herdinghound.impl.HerdingHoundFrameImpl;
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
    private HerdingHoundFrameImpl frame;
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

        this.model = new HerdingHoundModelImpl(GRID_SIZE);
        this.view = new HerdingHoundViewImpl();
        this.rightPanel = new RightPanelImpl();
        this.frame = new HerdingHoundFrameImpl();

        this.view.setController(this);
        this.rightPanel.setController(this);

        this.frame.setupGamePanels((Component) this.view, (Component) this.rightPanel);
        this.frame.setVisible(true);

        setupKeyListener();

        this.view.startCountdown(() -> {
            this.model.startGame();
            startGameLogic();
            this.view.requestFocusInWindow();
        });
    }

    private void setupKeyListener() {
        this.view.addKeyListener(new KeyAdapter() {
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
        this.gameActive = true;
        this.scheduleNextDogState(this.model.getDog().getState());
        this.gameTimer = new Timer(1000, (final java.awt.event.ActionEvent e) -> {
            this.view.updateView();
            this.rightPanel.updatePanel();
            if (this.model.getGameState() != GameState.ONGOING) {
                this.endGame();
            }
        });
        this.gameTimer.setInitialDelay(0);
        this.gameTimer.start();
    }

    private void scheduleNextDogState(final State currentState) {
        if (this.model.isOver()) {
            return;
        }
        final int delay = (currentState == State.ALERT)
                ? DOG_ALERT_DELAY
                : DOG_OTHER_DELAY_BASE + rnd.nextInt(DOG_OTHER_DELAY_RANDOM) * 1_000;
        if (this.dogStateTimer != null) {
            this.dogStateTimer.stop();
        }
        this.dogStateTimer = new Timer(delay, (final java.awt.event.ActionEvent e) -> {
            this.model.nextDogState();
            this.view.updateView();
            this.rightPanel.updatePanel();
            if (this.model.isOver()) {
                this.endGame();
            } else {
                this.scheduleNextDogState(this.model.getDog().getState());
            }
        });
        this.dogStateTimer.setRepeats(false);
        this.dogStateTimer.start();
    }

    private void endGame() {
        if (this.gameTimer != null) {
            this.gameTimer.stop();
        }
        if (this.dogStateTimer != null) {
            this.dogStateTimer.stop();
        }
        final boolean hasWon = this.model.getGameState() == GameState.WON;
        this.view.startBlinking(this.frame, hasWon); // blink before showing the end screen
        this.view.setFocusable(false);
    }

    /**
     * Returns the state of the game.
     * @return GameState
     */
    public GameState getGameState() {
        return this.model.getGameState();
    }

    /**
     * @return the grid size of the game
     */
    public int getGridSize() {
        return this.model.getGrid();
    }

    /**
     * @return the current position of the goose
     */
    public Position getGoosePosition() {
        return this.model.getGoose().getCoord();
    }

    /**
     * @return the current state of the dog
     */
    public State getDogState() {
        return this.model.getDog().getState();
    }

    /**
     * @return the area visible to the dog
     */
    public List<Position> getDogVisibleArea() {
        return this.model.getDog().getVisibleArea();
    }

    /**
     * @return the current position of the dog
     */
    public Position getDogPosition() {
        return this.model.getDog().getCoord();
    }

    /**
     * @return the list of visible cells
     */
    public List<Position> getVisibleCells() {
        return this.model.getVisible();
    }

    /**
     * @return the list of shadowed cells
     */
    public List<Position> getShadows() {
        return this.model.getShadows();
    }

    /**
     * @return the list of box positions
     */
    public List<Position> getBoxes() {
        return this.model.getBoxes();
    }

    /**
     * @return the remaining time in the game
     */
    public long getRemainingTime() {
        return this.model.getRemainingTime();
    }
}
