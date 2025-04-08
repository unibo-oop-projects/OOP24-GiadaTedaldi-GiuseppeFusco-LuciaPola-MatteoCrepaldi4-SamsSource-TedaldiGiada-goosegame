
package it.unibo.goosegame.model.minigames.herdinghound.impl;
import java.util.Random;

import it.unibo.goosegame.model.minigames.herdinghound.api.Boxgen;
import it.unibo.goosegame.utilities.Pair;

import java.util.ArrayList;
import java.util.List;

public class BoxImpl implements Boxgen {

    private List<Pair<Integer,Integer>> eastBox = new ArrayList<>();
    private List<Pair<Integer,Integer>> southBox = new ArrayList<>();
    private List<Pair<Integer,Integer>> westBox = new ArrayList<>();
    private List<Pair<Integer,Integer>> shadows = new ArrayList<>();
    private int gridSize;
    private int boxDistance;
    private Random random;

    public BoxImpl(int gridSize){
        this.gridSize=gridSize;
        this.boxDistance=2;
        this.random = new Random();
    }

    @Override
    public List<Pair> getBoxes() {
        List<Pair> allBoxes = new ArrayList<>();
        allBoxes.clear();
        allBoxes.addAll(westBox);
        allBoxes.addAll(southBox);
        allBoxes.addAll(eastBox);
        return allBoxes;
    }

    @Override
public void generateBoxes() {
    westBox.clear();
    southBox.clear();
    eastBox.clear();
    shadows.clear();

    for (int x = boxDistance, y = boxDistance; y < gridSize - boxDistance; y++) {
        if (random.nextDouble() < 0.6) {
            Pair<Integer, Integer> box = new Pair<>(x, y);
            westBox.add(box);
            generateShadows(box);
        }
    }

    for (int x = boxDistance + 1, y = gridSize - boxDistance; x < gridSize - boxDistance; x++) {
        if (random.nextDouble() < 0.6) {
            Pair<Integer, Integer> box = new Pair<>(x, y);
            southBox.add(box);
            generateShadows(box);
        }
    }

    for (int x = gridSize - boxDistance, y = gridSize - boxDistance - 1; y >= boxDistance; y--) {
        if (random.nextDouble() < 0.6) {
            Pair<Integer, Integer> box = new Pair<>(x, y);
            eastBox.add(box);
            generateShadows(box);
        }
    }
}

private void generateShadows(Pair<Integer, Integer> box) {
    int x = box.getX();
    int y = box.getY();
    int center = gridSize / 2;

    if (x == boxDistance) {
        if (y >= center - 2 && y <= center + 2) {
            addShadowLine(x - 1, y, -1, 0);
        } else if (y < center - 2) {
            addShadowLine(x - 1, y - 1, -1, -1);
        } else {
            addShadowLine(x - 1, y + 1, -1, 1);
        }
    }
    else if (y == gridSize - boxDistance) {
        if (x >= center - 2 && x <= center + 2) {
            addShadowLine(x, y + 1, 0, 1);
        } else if (x < center - 2) {
            addShadowLine(x - 1, y + 1, -1, 1);
        } else {
            addShadowLine(x + 1, y + 1, 1, 1);
        }
    }
    else if (x == gridSize - boxDistance) {
        if (y >= center - 2 && y <= center + 2) {
            addShadowLine(x + 1, y, 1, 0);
        } else if (y < center - 2) {
            addShadowLine(x + 1, y - 1, 1, -1);
        } else {
            addShadowLine(x + 1, y + 1, 1, 1);
        }
    }
}

private void addShadowLine(int startX, int startY, int dx, int dy) {
    int currX = startX;
    int currY = startY;
    while (currX >= 0 && currX < gridSize && currY >= 0 && currY < gridSize) {
        shadows.add(new Pair<>(currX, currY));
        currX += dx;
        currY += dy;
    }
}

    public List<Pair<Integer,Integer>> getShadows(){
        return new ArrayList<>(shadows);
    }
}



    
