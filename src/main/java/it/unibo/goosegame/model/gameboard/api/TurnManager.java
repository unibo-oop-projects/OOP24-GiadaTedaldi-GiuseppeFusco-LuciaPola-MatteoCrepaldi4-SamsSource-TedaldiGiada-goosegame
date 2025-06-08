package it.unibo.goosegame.model.gameboard.api;

import it.unibo.goosegame.model.player.api.Player;

/**
 * Interface responsible for managing the turns of players during the game.
 */
public interface TurnManager {
    /**
     * @return the current player.
     */
    Player getCurrentPlayer();

    /**
     * Advances to the next player's turn.
     * If a player has any turns to skip, their skip count is decreased.
     * 
     * @return the next player who will take their turn 
     */
    Player nextTurn();

}
