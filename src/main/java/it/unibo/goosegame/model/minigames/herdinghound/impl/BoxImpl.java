
package it.unibo.goosegame.model.minigames.herdinghound.impl;
import java.util.Random;
import java.util.Set;

import it.unibo.goosegame.model.minigames.herdinghound.api.Box;
import it.unibo.goosegame.utilities.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BoxImpl implements Box {

    private static final double BOX_PROBABILITY = 0.6;

    private List<Pair<Integer,Integer>> allBoxes = new ArrayList<>();
    private Set<Pair<Integer,Integer>> shadows = new HashSet<>();
    private int gridSize;
    private int boxDistance;
    private Random random;
    private Pair<Integer,Integer> pointLux;

    public BoxImpl(int gridSize, DogImpl dog){
        this.gridSize=gridSize;
        this.boxDistance=gridSize/5;
        this.random = new Random();
        this.pointLux = new Pair<Integer,Integer>( dog.getCoord().getX(), dog.getCoord().getY());
    }

    @Override
    public List<Pair<Integer,Integer>> getBoxes() {
        return allBoxes;
    }

    @Override
public void generateBoxes() {
    allBoxes.clear();
    shadows.clear();

    for (int x = boxDistance, y = boxDistance; y < gridSize - boxDistance; y++) {
        if (random.nextDouble() < BOX_PROBABILITY) {
            Pair<Integer, Integer> box = new Pair<>(x, y);
            allBoxes.add(box);
            generateShadows(box);
        }
    }

    for (int x = boxDistance + 1, y = gridSize - boxDistance; x < gridSize - boxDistance; x++) {
        if (random.nextDouble() < BOX_PROBABILITY) {
            Pair<Integer, Integer> box = new Pair<>(x, y);
            allBoxes.add(box);
            generateShadows(box);
        }
    }

    for (int x = gridSize - boxDistance, y = gridSize - boxDistance - 1; y >= boxDistance; y--) {
        if (random.nextDouble() < BOX_PROBABILITY) {
            Pair<Integer, Integer> box = new Pair<>(x, y);
            allBoxes.add(box);
            generateShadows(box);
        }
    }

    for (int x = boxDistance + 1; x < gridSize - boxDistance; x++) {
        int y = boxDistance;
        if (random.nextDouble() < BOX_PROBABILITY) {
            Pair<Integer, Integer> box = new Pair<>(x, y);
            allBoxes.add(box);
            generateShadows(box);
        }
    }
}

private void generateShadows(Pair<Integer, Integer> box) {
    int lightX = pointLux.getX();
    int lightY = pointLux.getY();

    int dx = box.getX() - lightX;
    int dy = box.getY() - lightY;

    int stepX = (dx == 0) ? 0 : dx / Math.abs(dx);
    int stepY = (dy == 0) ? 0 : dy / Math.abs(dy);

    double distance = Math.sqrt(dx * dx + dy * dy);
    
    int shadowWidth = (int) (Math.max(gridSize / 10, gridSize / distance));

    int shadowLength = gridSize;

    int shadowX = box.getX() + stepX;
    int shadowY = box.getY() + stepY;

    while (shadowX >= 0 && shadowX < gridSize && shadowY >= 0 && shadowY < gridSize && shadowLength > 0) {

        for (int i = -shadowWidth; i <= shadowWidth; i++) {
            int newShadowX = shadowX + i * stepX;
            int newShadowY = shadowY + i * stepY;

            if (newShadowX >= 0 && newShadowX < gridSize && newShadowY >= 0 && newShadowY < gridSize) {
                Pair<Integer, Integer> shadowCell = new Pair<>(newShadowX, newShadowY);
                
                if (!shadows.contains(shadowCell)) {
                    shadows.add(shadowCell);
                }
            }
        }
        
        shadowX += stepX;
        shadowY += stepY;
        shadowLength--;
    }
}



    public List<Pair<Integer,Integer>> getShadows(){
        return new ArrayList<>(shadows);
    }
}



    
