package it.unibo.goosegame.controller.minigames.puzzle.impl;

import it.unibo.goosegame.controller.minigames.puzzle.api.PuzzleController;
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
     * @param model the {@link PuzzleModel} representing the game logic
     * @param view the {@link PuzzleViewImpl} representing the game view
     */
    public PuzzleControllerImpl(final PuzzleModel model, final PuzzleView view) {
        if (!(model instanceof PuzzleModelImpl)) {
            throw new IllegalArgumentException("model must be a PuzzleModelImpl");
        }
        this.model = new PuzzleModelImpl((PuzzleModelImpl) model);
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
    public PuzzleModel getModel() {
        return new PuzzleModelImpl((PuzzleModelImpl) this.model);
    }
}
