package it.unibo.goosegame.controller.minigames.puzzle.impl;

import java.util.HashMap;
import java.util.Map;

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
    private final PuzzleModel model;
    private final PuzzleView view;

    /**
     * Constructs a new instance of {@link PuzzleControllerImpl}.
     * 
     */
    public PuzzleControllerImpl() {
        this.model = new PuzzleModelImpl();
        this.view = new PuzzleViewImpl();
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
        final boolean isWon = this.model.isOver();
        final boolean isLost = this.model.getGameState() == GameState.LOST;
        if (isWon || isLost) {
            this.view.showResultMessage(isWon);
            this.view.stopTimer();
            this.view.endGame();
        }
    }
}
