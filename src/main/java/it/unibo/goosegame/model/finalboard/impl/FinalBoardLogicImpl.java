package it.unibo.goosegame.model.finalboard.impl;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import it.unibo.goosegame.controller.gameboard.api.GameBoard;
import it.unibo.goosegame.model.finalboard.api.FinalBoardLogic;
import it.unibo.goosegame.model.player.api.Player;

/**
 * Implementation of the FinalBoardLogic interface.
 * This class provides the logic for retrieving the final board of the game.
 */
public class FinalBoardLogicImpl implements FinalBoardLogic {

    private final GameBoard gameBoard;

    /**
     * Constructor for FinalBoardLogicImpl.
     * Initializes the final board logic.
     * 
     * @param gameBoard the game board model containing the players
     */
    public FinalBoardLogicImpl(final GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Integer> getFinalBoard() {
        return gameBoard.getPlayers().stream()
        .sorted(Comparator.comparingInt(Player::getPosition))
        .collect(Collectors.toMap(
            Player::getName,
            Player::getPosition,
            (a, b) -> a,
            LinkedHashMap::new
        ));
    }

}
