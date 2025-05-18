package it.unibo.goosegame.model.gameboard.api;

import it.unibo.goosegame.utilities.Pair;

/**
 * Interface representing a pair of dice used in the game.
 */
public interface DoubleDice {

    /**
     * Rolls both dice, generating two new random values (between 1 and 6).
     */
    void rollDices();

    /**
     * @return the individual values of the two dices.
     */
    Pair<Integer, Integer> getDices();

    /**
     * @return the sum of the two dice values.
     */
    int getResult();

}
