package it.unibo.goosegame.application.finalboard.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
        final Map<String, Integer> finalBoard = new HashMap<>();
        finalBoard.put("Player1", 1);
        finalBoard.put("Player2", 2);
        finalBoard.put("Player3", 3);
        return finalBoard.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .collect(
                    LinkedHashMap::new,
                    (map, entry) -> map.put(entry.getKey(), entry.getValue()),
                    LinkedHashMap::putAll
            );
    }

}
