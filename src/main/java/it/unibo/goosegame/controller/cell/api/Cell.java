package it.unibo.goosegame.controller.cell.api;

import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.model.player.api.Player;

import javax.swing.JPanel;

/**
 * Utility class used as representation for the gameboard cells.
 */
public interface Cell {
    /**
     * Utility function used to get the cell graphical element.
     *
     * @return {@link JPanel} ready to be shown
     */
    JPanel getCellPanel();

    /**
     * Utility function to determine if the cell contains a minigame.
     *
     * @return whether the cell is a minigame cell or not
     */
    boolean isMinigameCell();

    /**
     * Adds the player to the cell.
     *
     * @param player the player to add
     */
    void addPlayer(Player player);

    /**
     * Utility function to move a player to a cell.
     *
     * @param cell the cell to move the player to
     * @param player the player to remove
     */
    void movePlayer(Cell cell, Player player);

    /**
     * Utility function to check if the cell contains a player.
     *
     * @param p the player to check
     * @return true if the cell contains the player, false otherwise
     */
    boolean containsPlayer(Player p);

    GameState triggerMinigame();
}
