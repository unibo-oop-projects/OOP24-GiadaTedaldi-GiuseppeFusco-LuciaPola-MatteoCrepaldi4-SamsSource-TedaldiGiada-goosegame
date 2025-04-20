package it.unibo.goosegame.model.minigames.herdinghound.impl;

import java.util.List;
import java.util.stream.Collectors;


import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.utilities.Pair;

public class HerdingHoundModel implements MinigamesModel {

    private static final int START_X = 0;
    private static final int START_Y = 0;

    private final GooseImpl goose;
    private final DogImpl dog;
    private final BoxImpl box;
    private final int gridSize;

    public HerdingHoundModel(int gridSize) {
        this.gridSize = gridSize;
        this.goose = new GooseImpl(START_X, START_Y);
        this.dog = new DogImpl(gridSize);
        this.box = new BoxImpl(gridSize, dog);
        this.box.generateBoxes();
    }

    public void nextGooseMove() {
        Pair<Integer,Integer> pos = goose.getCoord();
        int x = pos.getX(), y = pos.getY();
    
        if (y == START_Y && x < gridSize - 1) {
            // sulla colonna 0, vai giù
            goose.move(1, 0);
        }
        else if (x == gridSize - 1 && y < gridSize - 1) {
            // in fondo, vai a destra
            goose.move(0, 1);
        }
        else if (y == gridSize - 1 && x > START_X) {
            // sull'ultima colonna, risali
            goose.move(-1, 0);
        }
    
        // aggiorna la direzione del cane a ogni mossa
        dog.refreshDirection(goose);
    }

    public GooseImpl getGoose() {
        return this.goose;
    }

    public DogImpl getDog() {
        return this.dog;
    }

    public BoxImpl getBox() {
        return this.box;
    }

    public int getGrid(){
        return this.gridSize;
    }

    public List<Pair<Integer, Integer>> getShadows(){
        return this.box.getShadows();
    }

    @Override
    public void resetGame() {
        goose.reset();
        dog.reset();
        box.generateBoxes();
        dog.refreshDirection(goose);
    }

    @Override
    public GameState getGameState() {
        if (hasWon()) {
            return GameState.WON;
        } else if (hasLost()) {
            return GameState.LOST;
        } else {
            return GameState.ONGOING;
        }
    }

    public boolean isOver() {
        return hasWon() || hasLost();
    }

    private boolean hasWon() {
        Pair<Integer,Integer> winPos = new Pair<>(START_X, gridSize - 1);
        return goose.getCoord().equals(winPos);
    }

    private boolean hasLost() {
        if (dog.getState() != DogImpl.State.AWAKE) {
            return false;
        }

        return getVisible().contains(goose.getCoord());
    }

    public List<Pair<Integer, Integer>> getVisible() {
        return dog.getVisibleArea().stream()
            .filter(pos -> !box.getShadows().contains(pos))
            .filter(pos -> !box.getBoxes().contains(pos))
            .collect(Collectors.toList());
    }

    public void nextDogState() {
        dog.refreshState();
        dog.refreshDirection(goose);
    }
    
    public List<Pair<Integer, Integer>> getBoxes() {
        return box.getBoxes();
    }
}
