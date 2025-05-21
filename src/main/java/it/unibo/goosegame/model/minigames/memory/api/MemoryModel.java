package it.unibo.goosegame.model.minigames.memory.api;

import java.util.Optional;

import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.utilities.Position;

/**
 * Interface representing the model logic for the "Memory" mini-game.
 * It defines methods to manage player interactions and the state
 * of revealed and selected cells on the game board.
 */
public interface MemoryModel extends MinigamesModel {

    /**
     * Selects a cell at the given position. If two different cells are selected
     * and their values match, they are marked as found.
     * If two cells were already selected, they are cleared before selecting a new one.
     *
     * @param p the position of the selected cell
     */
    void hit(Position p);

    /**
     * Returns the value of the cell if it has been successfully matched (found).
     *
     * @param p the position of the cell
     * @return an Optional containing the value if the cell has been found;
     *         otherwise, an empty Optional
     */
    Optional<Integer> found(Position p);

    /**
     * Temporarily returns the value of a cell if it is currently selected
     * (but not yet matched).
     *
     * @param p the position of the cell
     * @return an Optional containing the value if the cell is currently selected;
     *         otherwise, an empty Optional
     */
    Optional<Integer> temporary(Position p);
}
