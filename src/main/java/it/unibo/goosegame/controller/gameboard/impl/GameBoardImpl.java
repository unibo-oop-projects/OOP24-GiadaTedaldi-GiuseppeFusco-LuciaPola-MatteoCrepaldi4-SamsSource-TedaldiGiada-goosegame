package it.unibo.goosegame.controller.gameboard.impl;

import it.unibo.goosegame.controller.cell.api.Cell;
import it.unibo.goosegame.controller.cell.impl.CellImpl;
import it.unibo.goosegame.controller.gameboard.api.GameBoard;
import it.unibo.goosegame.model.gameboard.api.GameBoardModel;
import it.unibo.goosegame.model.gameboard.impl.GameBoardModelImpl;
import it.unibo.goosegame.view.gameboard.api.GameBoardView;
import it.unibo.goosegame.view.gameboard.impl.GameBoardViewImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Classed use to represent the gameboard.
 */
public class GameBoardImpl implements GameBoard {
    private static final int CELLS_NUM = 60;

    private final GameBoardModel model;
    private final GameBoardView view;
    private final List<Cell> gameCells;

    /**
     * GameBoard constructor method.
     * @param numPlayer number of players
     */
    public GameBoardImpl(final int numPlayer) {
        this.gameCells = new ArrayList<>();
        initGameCells();

        this.model = new GameBoardModelImpl(numPlayer);
        this.view = new GameBoardViewImpl(model, gameCells);

        this.view.show();
    }

    private void initGameCells() {
        for (int i = 0; i < CELLS_NUM; i++) {
            gameCells.add(new CellImpl());
        }
    }
}
