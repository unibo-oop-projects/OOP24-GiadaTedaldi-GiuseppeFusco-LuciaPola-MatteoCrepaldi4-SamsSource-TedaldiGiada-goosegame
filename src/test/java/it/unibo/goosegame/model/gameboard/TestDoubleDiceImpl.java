package it.unibo.goosegame.model.gameboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goosegame.model.gameboard.api.DoubleDice;
import it.unibo.goosegame.model.gameboard.impl.DoubleDiceImpl;
import it.unibo.goosegame.utilities.Pair;


/**
 * Unit tests for {@link DoubleDiceImpl}.
 */
class TestDoubleDiceImpl {

    private static final int MIN = 1;
    private static final int MAX = 6;
    private DoubleDice dices;

    /**
     * Initializes a new {@link DoubleDiceImpl} instance before each test.
     */
    @BeforeEach
    void init() {
        this.dices = new DoubleDiceImpl();
    }

    /**
     * Tests that the initial values of the dices are set to zero before any roll occurs.
     */
    @Test
    void testInitialDiceValues() {
        final Pair<Integer, Integer> dicesValues = this.dices.getDices();
        assertEquals(0, dicesValues.getX());
        assertEquals(0, dicesValues.getY());
    }

    /**
     * Tests that after rolling the dice, both values are within the valid range (1,6).
     */
    @Test
    void testValuesInRange() {
        this.dices.rollDices();
        final Pair<Integer, Integer> dicesValues = this.dices.getDices();
        final int first = dicesValues.getX();
        final int second = dicesValues.getY();
        assertTrue(first >= MIN && first <= MAX);
        assertTrue(second >= MIN && second <= MAX);
    }

    /**
     * Tests that the result returned by the method getResult() is equal 
     * to the sum of the two individual dice values.
     */
    @Test
    void testGetResult() {
        this.dices.rollDices();
        final Pair<Integer, Integer> dicesValues = this.dices.getDices();
        final int expectedSum = dicesValues.getX() + dicesValues.getY();
        assertEquals(expectedSum, this.dices.getResult());
    }
}
