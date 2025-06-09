package it.unibo.goosegame.model.minigames.tris.api;

import java.util.Map;

import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.utilities.Position;

/**
 * Interface representing the logic of a Tris(Tic-Tac-Toe) minigame.
 * Extends the generic {@link MinigamesModel} interface.
 */
public interface TrisModel extends MinigamesModel {

    /**
     * Enum representing the two possible players in the game: Human or PC.
     */
    enum Player {
        /** The human player. */
        HUMAN,
        /** The computer-controlled player. */
        PC;
    }

    /**
     * Executes a move for the PC player.
     */
    void makePcMove();

    /**
     * Makes a move for the human player at the specified position.
     * 
     * @param position the position on the board where the human wants to make the move 
     * @return true if the move was valid and successfully made, false otherwise
     */
    boolean makeHumanMove(Position position);

    /**
     * Checks whether the game board is completely filled.
     * 
     * @return true if all cells are occupied, false otherwise
     */
    boolean isFull();

    /**
     * Checks if the given position is occupied by the human player.
     * 
     * @param position the position to check
     * @return true if the position is occupied by the human player, false otherwise
     */
    boolean isHuman(Position position);

    /**
     * Checks if the given position is occupied by the PC player.
     * 
     * @param position the position to check
     * @return true if the position is occupied by the PC player, false otherwise
     */
    boolean isPc(Position position);

    /**
     * Returns the internal grid representing the current state of the game.
     * The grid maps positions to the player occupying them.
     * 
     * @return a map of positions to players 
     */
    Map<Position, Player> getGrid();

    /**
     * Updates the game grid with a new grid.
     * It is used for the test.
     * 
     * @param newGrid the new grid replacing the current grid
     */
    void updateGrid(Map<Position, Player> newGrid);

    /**
     * Checks whether the current player has won the game.
     * 
     * @return true if a winning condition is met, false otherwise
     */
    boolean checkWin();
}
