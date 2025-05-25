package it.unibo.goosegame.controller.gameboard.impl;

import it.unibo.goosegame.controller.gameboard.api.GameBoard;
import it.unibo.goosegame.model.gameboard.api.GameBoardModel;
import it.unibo.goosegame.model.gameboard.impl.GameBoardModelImpl;
import it.unibo.goosegame.view.gameboard.api.GameBoardView;
import it.unibo.goosegame.view.gameboard.impl.GameBoardViewImpl;

/**
 * Classed use to represent the gameboard.
 */
public class GameBoardImpl implements GameBoard {
    private final GameBoardModel model;
    private final GameBoardView view;

    /**
     * GameBoard constructor method.
     * @param numPlayer number of players
     */
    public GameBoardImpl(final int numPlayer) {
        this.model = new GameBoardModelImpl(numPlayer);
        this.view = new GameBoardViewImpl(model);

        this.view.show();
    }
}
