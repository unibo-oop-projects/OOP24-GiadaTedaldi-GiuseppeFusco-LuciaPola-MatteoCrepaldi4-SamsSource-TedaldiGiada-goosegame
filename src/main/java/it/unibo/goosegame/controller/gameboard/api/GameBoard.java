package it.unibo.goosegame.controller.gameboard.api;

import it.unibo.goosegame.model.player.api.Player;

import java.util.List;

/**
 * Interface for the {@link it.unibo.goosegame.controller.gameboard.impl.GameBoardImpl} class.
 */
public interface GameBoard {
    /**
     * Utility function to get the list of players.
     *
     * @return the list of players
     */
    List<Player> getPlayers();
}
