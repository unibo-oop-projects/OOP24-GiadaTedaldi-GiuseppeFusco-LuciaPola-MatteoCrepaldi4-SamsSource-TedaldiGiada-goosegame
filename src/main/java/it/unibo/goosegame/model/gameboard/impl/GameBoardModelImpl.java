package it.unibo.goosegame.model.gameboard.impl;

import it.unibo.goosegame.controller.cell.api.Cell;
import it.unibo.goosegame.model.gameboard.api.DoubleDice;
import it.unibo.goosegame.model.gameboard.api.GameBoardModel;
import it.unibo.goosegame.model.player.api.Player;
import it.unibo.goosegame.model.turnmanager.api.TurnManager;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 * Implementation of {@link GameBoardModel}.
 */
public final class GameBoardModelImpl implements GameBoardModel {
    private final TurnManager turnManager;
    private final DoubleDice dice;
    private final List<Cell> cells;

    /**
     * Constructor for the gameboard model element.
     *
     * @param turnManager number of players
     * @param cells list of cells representing the game board
     */
    public GameBoardModelImpl(final TurnManager turnManager, final List<Cell> cells) {
        this.turnManager = turnManager;
        this.cells = cells;
        this.dice = new DoubleDiceImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWindowTitle() {
        return "Goose Game";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void throwDices() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                dice.rollDices();
                return null;
            }

            @Override
            protected void done() {
                final int result = dice.getResult();
                JOptionPane.showMessageDialog(null, result);

                searchPlayer(turnManager.getCurrentPlayer()).movePlayer(
                        cells.get(calcMovement(result, true)),
                        turnManager.getCurrentPlayer()
                );

                turnManager.nextTurn();
            }
        }.execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(Player player, int steps, boolean isForward) {
        Cell currentCell = searchPlayer(player);
        int newPosition = calcMovement(steps, isForward);
        Cell newCell = cells.get(newPosition);

        player.setIndex(newPosition);
        currentCell.movePlayer(newCell, player);
    }

    /**
     * Searches for the cell containing the specified player.
     *
     * @param player the player to search for
     * @return the cell containing the player
     */
    private Cell searchPlayer(final Player player) {
        for (final Cell cell : cells) {
            if (cell.containsPlayer(player)) {
                return cell;
            }
        }

        throw new IllegalArgumentException("Player not found on the board.");
    }

    /**
     * Calculates the new position of the player based on the number of steps.
     *
     * @param steps number of steps to move
     * @param isForward true if the movement is forward, false if backward
     * @return the new position of the player on the board
     */
    private int calcMovement(final int steps, final boolean isForward) {
        final int cellsNum = cells.size();
        final int position = cells.indexOf(searchPlayer(turnManager.getCurrentPlayer()));

        if (isForward) {
            return Math.min(position + steps, cellsNum - 1);
        }

        return Math.max(position - steps, 0);
    }
}
