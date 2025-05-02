package it.unibo.goosegame.model.general;

public interface MinigamesModel {

    public enum GameState{
        ONGOING,
        WON,
        LOST
    }

    void resetGame();

    boolean isOver();
    }
