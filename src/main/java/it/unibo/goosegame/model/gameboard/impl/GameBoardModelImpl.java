package it.unibo.goosegame.model.gameboard.impl;

import it.unibo.goosegame.model.gameboard.api.GameBoardModel;
import it.unibo.goosegame.model.player.api.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link GameBoardModel}.
 */
public class GameBoardModelImpl implements GameBoardModel {
    private final List<Player> players;
    private final int playerNum;

    /**
     * Constructor for the gameboard model element.
     *
     * @param playerNum number of players
     */
    public GameBoardModelImpl(final int playerNum) {
        this.playerNum = playerNum;
        this.players = new ArrayList<>(playerNum);
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
    public int getNumberOfPlayers() {
        return playerNum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> getPlayers() {
        return List.of();
    }
}
