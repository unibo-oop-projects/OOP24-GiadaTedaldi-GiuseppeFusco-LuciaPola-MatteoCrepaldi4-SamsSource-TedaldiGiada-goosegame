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
    private static final int BORDER_MARGIN_DIVISOR = 2;
    private static final int SHADOW_WIDTH_MIN_DIVISOR = 10;
    private static final int SHADOW_MAX_LENGTH = 1000;

    private final Set<Pair<Integer, Integer>> allBoxes = new HashSet<>();
    private final Set<Pair<Integer, Integer>> shadows = new HashSet<>();
    private final int gridSize;
    private final int boxDistance;
    private final Random random;
    private final Pair<Integer, Integer> pointLux;

    public BoxImpl(int gridSize, DogImpl dog) {
        this.gridSize = gridSize;
        this.boxDistance = BORDER_MARGIN_DIVISOR;
        this.random = new Random();
        this.pointLux = new Pair<>(dog.getCoord().getX(), dog.getCoord().getY());
    }

    @Override
    public List<Pair<Integer, Integer>> getBoxes() {
        return new ArrayList<>(allBoxes);
    }

    @Override
    public void generateBoxes() {
        allBoxes.clear();
        shadows.clear();

        for (int y = boxDistance; y < gridSize - boxDistance; y++) {
            tryAddBox(new Pair<>(boxDistance, y));
        }

        for (int x = boxDistance + 1; x < gridSize - boxDistance; x++) {
            tryAddBox(new Pair<>(x, gridSize - boxDistance - 1));
        }

        for (int y = gridSize - boxDistance - 1; y >= boxDistance; y--) {
            tryAddBox(new Pair<>(gridSize - boxDistance - 1 , y));
        }

        for (int x = boxDistance + 1; x < gridSize - boxDistance; x++) {
            tryAddBox(new Pair<>(x, boxDistance));
        }
    }

    private void tryAddBox(Pair<Integer, Integer> box) {
        if (random.nextDouble() < BOX_PROBABILITY) {
            allBoxes.add(box);
            generateShadows(box);
        }
    }

    private void generateShadows(Pair<Integer, Integer> box) {
        int lightX = pointLux.getX();
        int lightY = pointLux.getY();

        int dx = box.getX() - lightX;
        int dy = box.getY() - lightY;   

        int stepX = Integer.compare(dx, 0);
        int stepY = Integer.compare(dy, 0);

        double distance = Math.sqrt(dx * dx + dy * dy);
        int shadowWidth = (int) Math.max(gridSize / SHADOW_WIDTH_MIN_DIVISOR, gridSize / distance);
        int shadowLength = Math.min(gridSize, SHADOW_MAX_LENGTH);

        int shadowX = box.getX() + stepX;
        int shadowY = box.getY() + stepY;

        while (isInBounds(shadowX, shadowY) && shadowLength-- > 0) {
            for (int i = -shadowWidth; i <= shadowWidth; i++) {
                int newShadowX = shadowX + i * stepY;
                int newShadowY = shadowY + i * stepX;

                if (isInBounds(newShadowX, newShadowY)) {
                    shadows.add(new Pair<>(newShadowX, newShadowY));
                }
            }
            shadowX += stepX;
            shadowY += stepY;
        }
    }

    private boolean isInBounds(int x, int y) {
        return !((x > boxDistance && x < gridSize - boxDistance) && (y > boxDistance && y < gridSize-boxDistance)) &&
         (x >= 0 && x < gridSize && y >= 0 && y < gridSize);
    }

    public List<Pair<Integer, Integer>> getShadows() {
        return new ArrayList<>(shadows);
    }

}



    
