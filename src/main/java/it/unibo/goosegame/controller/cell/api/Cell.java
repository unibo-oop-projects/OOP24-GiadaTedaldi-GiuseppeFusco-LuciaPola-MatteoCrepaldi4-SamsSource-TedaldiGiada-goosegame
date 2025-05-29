package it.unibo.goosegame.controller.cell.api;

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
}
