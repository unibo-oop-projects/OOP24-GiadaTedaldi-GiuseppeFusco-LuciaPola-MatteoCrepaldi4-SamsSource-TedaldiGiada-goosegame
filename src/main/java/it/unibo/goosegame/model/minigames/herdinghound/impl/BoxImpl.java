package it.unibo.goosegame.model.minigames.herdinghound.impl;

import java.util.Random;
import java.util.Set;
import it.unibo.goosegame.model.minigames.herdinghound.api.Box;
import it.unibo.goosegame.utilities.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BoxImpl implements Box {

    private static final double BOX_PROBABILITY = 0.6;
    private static final int BORDER_MARGIN_DIVISOR = 2;
    private static final int SHADOW_WIDTH_MIN_DIVISOR = 10;
    private static final int SHADOW_MAX_LENGTH = 1000;

    private final Set<Position> allBoxes = new HashSet<>();
    private final Set<Position> shadows = new HashSet<>();
    private final int gridSize;
    private final int boxDistance;
    private final Random random;
    private final Position pointLux;

    public BoxImpl(int gridSize, DogImpl dog) {
        this.gridSize = gridSize;
        this.boxDistance = BORDER_MARGIN_DIVISOR;
        this.random = new Random();
        this.pointLux = new Position(dog.getCoord().x(), dog.getCoord().y());
    }

    @Override
    public List<Position> getBoxes() {
        return new ArrayList<>(allBoxes);
    }

    @Override
    public void generateBoxes() {
        allBoxes.clear();
        shadows.clear();

        for (int y = boxDistance; y < gridSize - boxDistance; y++) {
            tryAddBox(new Position(boxDistance, y));
        }

        for (int x = boxDistance + 1; x < gridSize - boxDistance; x++) {
            tryAddBox(new Position(x, gridSize - boxDistance - 1));
        }

        for (int y = gridSize - boxDistance - 1; y >= boxDistance; y--) {
            tryAddBox(new Position(gridSize - boxDistance - 1 , y));
        }

        for (int x = boxDistance + 1; x < gridSize - boxDistance; x++) {
            tryAddBox(new Position(x, boxDistance));
        }
    }

    private void tryAddBox(Position box) {
        if (random.nextDouble() < BOX_PROBABILITY) {
            allBoxes.add(box);
            generateShadows(box);
        }
    }

    private void generateShadows(Position box) {
        int lightX = pointLux.x();
        int lightY = pointLux.y();

        int dx = box.x() - lightX;
        int dy = box.y() - lightY;   

        int stepX = Integer.compare(dx, 0);
        int stepY = Integer.compare(dy, 0);

        double distance = Math.sqrt(dx * dx + dy * dy);
        int shadowWidth = (int) Math.max(gridSize / SHADOW_WIDTH_MIN_DIVISOR, gridSize / distance);
        int shadowLength = Math.min(gridSize, SHADOW_MAX_LENGTH);

        int shadowX = box.x() + stepX;
        int shadowY = box.y() + stepY;

        while (isInBounds(shadowX, shadowY) && shadowLength-- > 0) {
            for (int i = -shadowWidth; i <= shadowWidth; i++) {
                int newShadowX = shadowX + i * stepY;
                int newShadowY = shadowY + i * stepX;

                if (isInBounds(newShadowX, newShadowY)) {
                    shadows.add(new Position(newShadowX, newShadowY));
                }
            }
            shadowX += stepX;
            shadowY += stepY;
        }
    }

    private boolean isInBounds(int x, int y) {
    boolean isOnBorder = (x <= boxDistance || x >= gridSize - boxDistance - 1) || 
    (y <= boxDistance || y >= gridSize - boxDistance - 1);
    return isOnBorder && x >= 0 && x < gridSize && y >= 0 && y < gridSize;
    }

    public List<Position> getShadows() {
        return new ArrayList<>(shadows);
    }

}



    
