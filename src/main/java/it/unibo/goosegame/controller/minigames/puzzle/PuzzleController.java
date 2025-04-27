package it.unibo.goosegame.controller.minigames.puzzle;

import it.unibo.goosegame.model.minigames.puzzle.api.PuzzleModel;
import it.unibo.goosegame.utilities.Position;
import it.unibo.goosegame.view.minigames.puzzle.PuzzleView;

public class PuzzleController {
    private final PuzzleModel model;
    private final PuzzleView view;

    public PuzzleController(PuzzleModel model, PuzzleView view) {
        this.model = model;
        this.view = view;
    }

    public void clickHandler(Position pos) {
        if(this.model.hit(pos)) {
            this.view.updateView();
            if(this.model.isOver()) {
                this.view.stopTimer();
                this.view.showMinMessage();
            }
        }
    }

    public void shufflePuzzle() {
        this.model.shuffle();
    }

    public PuzzleModel getModel() {
        return this.model;
    }
}
