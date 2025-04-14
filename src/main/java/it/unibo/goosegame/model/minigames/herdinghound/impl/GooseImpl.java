package it.unibo.goosegame.model.minigames.herdinghound.impl;

import it.unibo.goosegame.model.minigames.herdinghound.api.Goose;
import it.unibo.goosegame.utilities.Pair;

public class GooseImpl implements Goose {
    
    private Pair<Integer, Integer> position;
    private int startX;
    private int startY;

    public GooseImpl(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
        this.position = new Pair<>(startX, startY);
    }

    @Override
    public Pair<Integer, Integer> getCoord() {
        return position;
    }

    @Override
    public void move(int dx, int dy) {
        int newX = position.getX() + dx;
        int newY = position.getY() + dy;
        this.position = new Pair<>(newX, newY);
    }

    public void reset() {
        this.position = new Pair<>(startX, startY);
    }
}

