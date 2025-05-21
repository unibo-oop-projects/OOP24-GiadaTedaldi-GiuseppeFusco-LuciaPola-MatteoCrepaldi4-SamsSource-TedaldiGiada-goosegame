package it.unibo.goosegame.controller.minigames.puzzle.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.goosegame.controller.minigames.puzzle.api.PuzzleController;
import it.unibo.goosegame.model.minigames.puzzle.api.PuzzleModel;
import it.unibo.goosegame.utilities.Position;
import it.unibo.goosegame.view.minigames.puzzle.api.PuzzleView;
import it.unibo.goosegame.view.minigames.puzzle.api.ReadPuzzleView;

/**
 * Implementation of the {@link PuzzleController} interface.
 * It manages the communication between the model and the view of a Puzzle minigame.
 */
public class PuzzleControllerImpl implements PuzzleController {
    private final PuzzleModel model;
    private final ReadPuzzleView view;

    /**
     * Constructs a new instance of {@link PuzzleControllerImpl}.
     * 
     * @param model the {@link PuzzleModel} representing the game logic
     * @param view the {@link PuzzleView} representing the game view
     */
    public PuzzleControllerImpl(final PuzzleModel model, final ReadPuzzleView view) {
        if (model == null || view == null) {
            throw new IllegalArgumentException("model and view cannot be null");
        }
        this.model = model.getCopy();
        this.view = view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clickHandler(final Position pos) {
        if (this.model.hit(pos)) {
            this.view.updateView();
            if (this.model.isOver()) {
                this.view.stopTimer();
                this.view.showWinMessage();
            }
        }
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
}
