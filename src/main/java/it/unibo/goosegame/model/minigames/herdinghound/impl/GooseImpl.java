package it.unibo.goosegame.model.minigames.herdinghound.impl;

import it.unibo.goosegame.model.minigames.herdinghound.api.Goose;
import it.unibo.goosegame.utilities.Position;

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

