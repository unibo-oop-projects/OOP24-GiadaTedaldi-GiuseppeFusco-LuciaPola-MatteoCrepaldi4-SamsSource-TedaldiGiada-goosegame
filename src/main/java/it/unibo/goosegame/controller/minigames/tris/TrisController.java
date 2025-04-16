package it.unibo.goosegame.controller.minigames.tris;

import it.unibo.goosegame.model.minigames.tris.api.TrisModel;
import it.unibo.goosegame.utilities.Position;
import it.unibo.goosegame.view.minigames.tris.TrisView;

public class TrisController {
    private TrisModel model;
    private TrisView view;
    private boolean gameOver = false;

    public TrisController(TrisModel model) {
        this.model = model;
        this.view = new TrisView(this);
    }


    public void movesMaker(Position position) {
        if(gameOver) {
            return;
        }

        if(this.model.makeHumanMove(position)) {
            this.view.updateButton(position, "X");
            if(this.model.isOver()) {
                endGame();
                return;
            }
            this.model.makePcMove();
            updateView();
        }

    }

    private void updateView() {
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                Position pos = new Position(i, j);
                if(this.model.isPc(pos)) {
                    this.view.updateButton(pos, "O");
                }
            }
        }
        if(this.model.isOver()) {
            endGame();
        }
    }

    private void endGame() {
        gameOver=true;
        this.view.setStatus("The game is over!");
        this.view.disableButtons();
        int result = this.model.getResult();
        switch (result) {
            case 1: this.view.closeGame("You win!"); break;
            case -1: this.view.closeGame("PC wins!"); break;
            case 3: this.view.closeGame("Draw!"); break;
            case 0: this.view.closeGame("Still playing..."); break;
        }
    }

}
