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

    /**
     * Increments the number of turns the specified player must skip by a given amount.
     *
     * @param player the player who will skip their next turn
     * @param turns the number of turns the player will skip
     * @throws IllegalArgumentException if the player is not part of the current player list or if turns is negative or zero
     */
    void skipNextTurn(Player player, int turns);

}
