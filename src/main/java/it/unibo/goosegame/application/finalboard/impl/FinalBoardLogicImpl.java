package it.unibo.goosegame.application.finalboard.impl;

import java.util.Map;

import it.unibo.goosegame.application.finalboard.api.FinalBoardLogic;

/**
 * Implementation of the FinalBoardLogic interface.
 * This class provides the logic for retrieving the final board of the game.
 */
public class FinalBoardLogicImpl implements FinalBoardLogic {

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Integer> getFinalBoard() {
        return Map.of("Player1", 1, "Player2", 2, "Player3", 3);
    }

}
