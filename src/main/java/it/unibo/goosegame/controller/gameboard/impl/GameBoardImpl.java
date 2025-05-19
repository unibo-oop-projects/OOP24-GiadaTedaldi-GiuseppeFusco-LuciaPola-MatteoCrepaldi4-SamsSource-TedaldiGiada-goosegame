package it.unibo.goosegame.controller.gameboard.impl;

import it.unibo.goosegame.controller.gameboard.api.GameBoard;
import it.unibo.goosegame.model.gameboard.api.GameBoardModel;
import it.unibo.goosegame.model.gameboard.impl.GameBoardModelImpl;
import it.unibo.goosegame.view.gameboard.api.GameBoardView;
import it.unibo.goosegame.view.gameboard.impl.GameBoardViewImpl;

//  TODO: rendere il numero di player non fisso, ma massimo 5

/**
 * Classed use to represent the gameboard
 */
public class GameBoardImpl implements GameBoard {
    private static int NUMBER_OF_PLAYERS = 4;

    private final GameBoardModel model;
    private final GameBoardView view;

    public GameBoardImpl() {
        this.model = new GameBoardModelImpl(NUMBER_OF_PLAYERS);
        this.view = new GameBoardViewImpl(model);

        this.view.show();
    }
}
