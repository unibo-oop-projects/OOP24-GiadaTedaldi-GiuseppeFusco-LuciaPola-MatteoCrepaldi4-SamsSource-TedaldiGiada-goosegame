package it.unibo.goosegame.model.minigames.herdinghound.impl;

import java.util.List;
import java.util.stream.Collectors;

import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.utilities.Pair;

public class HerdingHoundModel implements MinigamesModel{

    private final GooseImpl goose;
    private final DogImpl dog;
    private final BoxImpl box;
    private final int gridSize;

    public HerdingHoundModel(int gridSize){
        this.gridSize = gridSize;
        this.goose = new GooseImpl();
        this.dog = new DogImpl(this.gridSize);
        this.box = new BoxImpl(gridSize, dog);
        box.generateBoxes();
    }

    public void nextGooseMove() {
        Pair<Integer, Integer> pos = goose.getCoord();
        int x = pos.getX();
        int y = pos.getY();

        if (x == 0 && y < gridSize - 1) {
            goose.move(0, 1);
        } else if (y == gridSize - 1 && x < gridSize - 1) {
            goose.move(1, 0);
        } else if (x == gridSize - 1 && y > 0) {
            goose.move(0, -1);
        }else if (y == 0 && x > 0) {
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
        return goose.getCoord().equals(new Pair<>(gridSize - 1, 0));
    }

    private boolean hasLost() {
        if (dog.getState() != DogImpl.State.AWAKE) {
            return false;
        }
        Pair<Integer, Integer> goosePos = goose.getCoord();
        List<Pair<Integer, Integer>> visible = getVisible();
        return visible.contains(goosePos);
    }

    public List<Pair<Integer, Integer>> getVisible() {
        List<Pair<Integer, Integer>> visible = dog.getVisibleArea();
        List<Pair<Integer, Integer>> shadows = box.getShadows();

        return visible.stream()
            .filter(pos -> !shadows.contains(pos))
            .collect(Collectors.toList());
    }
    
}
