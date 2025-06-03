package it.unibo.goosegame.controller.gameboard.impl;

import it.unibo.goosegame.controller.cardsatchel.CardSatchelController;
import it.unibo.goosegame.controller.cell.api.Cell;
import it.unibo.goosegame.controller.cell.impl.CellImpl;
import it.unibo.goosegame.controller.gameboard.api.GameBoard;
import it.unibo.goosegame.model.gameboard.api.GameBoardModel;
import it.unibo.goosegame.model.gameboard.impl.GameBoardModelImpl;
import it.unibo.goosegame.model.player.api.Player;
import it.unibo.goosegame.model.turnmanager.api.TurnManager;
import it.unibo.goosegame.model.turnmanager.impl.TurnManagerImpl;
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
    private final List<Player> players;
    private final TurnManager turnManager;

    /**
     * GameBoard constructor method.
     * @param players the list of the registered players
     */
    public GameBoardImpl(final List<Player> players) {
        this.gameCells = new ArrayList<>();
        this.players = players;
        this.turnManager = new TurnManagerImpl(players);

        initGameCells();

        for(Player p : players) {
            p.setSatchel(new CardSatchelController(this)); // Initialize player positions to the first cell
        }

        this.model = new GameBoardModelImpl(turnManager, gameCells);
        this.view = new GameBoardViewImpl(model, gameCells);

        this.view.show();
    }

    /**
     * Method used to initialise the game cells.
     * It creates a list of {@link Cell} objects and adds the players to the first cell.
     */
    private void initGameCells() {
        for (int i = 0; i < CELLS_NUM; i++) {
            gameCells.add(new CellImpl());
        }

        for (final Player player : players) {
            gameCells.getFirst().addPlayer(player);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(final int steps, final boolean isForward) {
        model.move(turnManager.getCurrentPlayer(), steps, isForward);
    }
}
