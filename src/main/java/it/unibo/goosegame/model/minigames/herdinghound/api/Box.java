package it.unibo.goosegame.model.minigames.herdinghound.api;

import java.util.List;
import it.unibo.goosegame.utilities.Position;

/**
 * Represents a box/obstacle in the Herding Hound minigame.
 * Manages the position and generation of boxes.
 */
public interface Box {

    /**
     * Returns the list of box positions.
     * @return list of positions
     */
    List<Position> getBoxes();

    /**
     * Generates the boxes on the grid.
     */
    void generateBoxes();

}
