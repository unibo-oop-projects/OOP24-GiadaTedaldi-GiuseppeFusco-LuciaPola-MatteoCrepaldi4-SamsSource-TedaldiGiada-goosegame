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
        Pair<Integer, Integer> pos = goose.getCoord();
        int x = pos.getX();
        int y = pos.getY();

        if (x == START_X && y < gridSize - 1) {
            goose.move(0, 1);
        } else if (y == gridSize - 1 && x < gridSize - 1) {
            goose.move(1, 0);
        } else if (x == gridSize - 1 && y > 0) {
            goose.move(0, -1);
        } else if (y == START_Y && x > START_X) {
            goose.move(-1, 0);
        }

        dog.refreshDirection(goose);
    }

    public GooseImpl getGoose() {
        return goose;
    }

    public DogImpl getDog() {
        return dog;
    }

    public BoxImpl getBox() {
        return box;
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
        Pair<Integer, Integer> winPosition = new Pair<>(gridSize - 1, START_Y);
        return goose.getCoord().equals(winPosition);
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
            .collect(Collectors.toList());
    }

    public void nextDogState() {
        dog.refreshState();
    }
    
}
