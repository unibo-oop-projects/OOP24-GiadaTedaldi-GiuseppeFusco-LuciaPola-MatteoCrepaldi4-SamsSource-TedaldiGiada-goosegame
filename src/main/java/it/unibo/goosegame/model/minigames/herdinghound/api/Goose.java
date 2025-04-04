package it.unibo.goosegame.model.minigames.herdinghound.api;

public interface Goose {

    int getX();
    int getY();
    void move(int dx, int dy);
    void moveDown();
    void moveRight();
    void moveUp();

}
