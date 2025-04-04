package it.unibo.goosegame.model.minigames.herdinghound;
import java.awt.*;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import it.unibo.goosegame.model.minigames.herdinghound.api.Goose;
import it.unibo.goosegame.model.minigames.herdinghound.impl.DogLogicImpl;
import it.unibo.goosegame.model.minigames.herdinghound.impl.GooseLogicImpl;
import it.unibo.goosegame.model.minigames.herdinghound.api.Dog;

public class HerdingHoundModel {
    private final int gridSize;
    private final Goose goose;
    private final Dog dog;
    private final List<Point> boxes;
    private final Set<Point> shadows;

    public HerdingHoundModel(int gridSize){
        this.gridSize = gridSize;
        int center = gridSize/2;
        this.dog = new DogLogicImpl(center, center, 3);
        this.goose = new GooseLogicImpl(0, 0);
        this.boxes = new ArrayList<>();
        this.shadows = new HashSet<>();
    }
    private boolean inBounds(int x, int y){
        return false;

    }
    public void gooseMove(int dx, int dy){
        int newX = goose.getX() +dx ;
        int newY = goose.getY() +dy;
        if(inBounds(newX, newY)){
            goose.move(dx, dy);
        }

    }

    private void boxesGeneration(){
        for()
    }
    private List<Point> celleAdiacenti(Point center){
        List<Point> nearby = new ArrayList<>();
        for(int dx = -1; dx <=1; dx++){
            for (int dy = -1;)
        }
    }
}
