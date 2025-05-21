package it.unibo.goosegame.application.finalboard.api;

import java.util.Map;

/**
 * Interface for the FinalBoardLogic class.
 * This interface defines the method to get the final board.
 */
public interface FinalBoardLogic {
    /**
     * Gets the final board.
     *
     * @return a map representing the final board with player names and their final positions.
     */
    Map<String, Integer> getFinalBoard();
}

