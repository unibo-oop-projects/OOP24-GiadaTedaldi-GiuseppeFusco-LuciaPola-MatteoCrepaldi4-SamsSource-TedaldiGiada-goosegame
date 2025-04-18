package it.unibo.goosegame.controller.minigames.tris.impl;

import it.unibo.goosegame.controller.minigames.tris.api.TrisController;
import it.unibo.goosegame.model.minigames.tris.api.TrisModel;
import it.unibo.goosegame.utilities.Position;
import it.unibo.goosegame.view.minigames.tris.api.TrisView;
import it.unibo.goosegame.view.minigames.tris.impl.TrisViewImpl;

/**
 * Implementation of the {@link TrisController} interface.
 * It manages the communication between the model and the view of a Tris(Tic-Tac-Toe) minigame.
 */
public class TrisControllerImpl implements TrisController{
    private TrisModel model;
    private TrisView view;
    private boolean gameOver = false;

    /**
     * Constructs a new instance of {@link TrisControllerImpl}
     * 
     * @param model the {@link TrisModel} representing the game logic  
     */
    public TrisControllerImpl(TrisModel model) {
        this.model = model;
        this.view = new TrisViewImpl(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateView() {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void endGame() {
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
