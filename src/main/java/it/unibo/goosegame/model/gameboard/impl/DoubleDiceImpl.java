package it.unibo.goosegame.model.gameboard.impl;

import java.util.Random;

import it.unibo.goosegame.model.gameboard.api.DoubleDice;
import it.unibo.goosegame.utilities.Pair;

/**
 * Implementation of the {@link DoubleDice} interaface.
 */
public class DoubleDiceImpl implements DoubleDice {

    private static final int MIN = 1;
    private static final int MAX = 6;
    private final Random random;
    private Pair<Integer, Integer> dices;

    /**
     * Constructs a new instance of {@link DoubleDiceImpl} instance.
     */
    public DoubleDiceImpl() {
        this.random = new Random();
        this.dices = new Pair<>(0, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rollDices() {
        final int first = this.random.nextInt(MAX - MIN + 1) + MIN;
        final int second = this.random.nextInt(MAX - MIN + 1) + MIN;
        this.dices = new Pair<>(first, second);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<Integer, Integer> getDices() {
        return this.dices;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getResult() {
        return this.dices.getX() + this.dices.getY();
    }

}
