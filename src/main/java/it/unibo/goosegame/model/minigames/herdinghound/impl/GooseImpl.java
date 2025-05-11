package it.unibo.goosegame.model.minigames.herdinghound.impl;

import it.unibo.goosegame.model.minigames.herdinghound.api.Goose;
import it.unibo.goosegame.utilities.Position;

/**
 * Implementation of the Goose interface for the Herding Hound minigame.
 * Manages the goose's position and movement logic.
 */
public class GooseImpl implements Goose {
    
    private Position position;
    private int startX;
    private int startY;

    public GooseImpl(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
        this.position = new Position(startX, startY);
    }

    @Override
    public Position getCoord() {
        return position;
    }

    @Override
    public void move(int dx, int dy) {
        int newX = position.x() + dx;
        int newY = position.y() + dy;
        this.position = new Position(newX, newY);
    }

    public void reset() {
        this.position = new Position(startX, startY);
    }
}

