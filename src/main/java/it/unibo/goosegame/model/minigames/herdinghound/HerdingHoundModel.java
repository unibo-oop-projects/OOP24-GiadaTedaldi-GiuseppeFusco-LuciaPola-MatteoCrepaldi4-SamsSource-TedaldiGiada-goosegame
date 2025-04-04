package it.unibo.goosegame.model.minigames.herdinghound;
import java.awt.*;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

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
    private final Random random = new Random();
    private boolean win = false;

    public HerdingHoundModel(int gridSize){
        this.gridSize = gridSize;
        int center = gridSize/2;
        this.dog = new DogLogicImpl(center, center, 3);
        this.goose = new GooseLogicImpl(0, 0, gridSize);
        this.boxes = new ArrayList<>();
        this.shadows = new HashSet<>();
        boxesGeneration();
        refreshShadows();
    }

    public void gooseMove(int dx, int dy){
        if(!win){
        int x = goose.getX();
        int y = goose.getY();
        if(x==0 && y < gridSize -1){
            goose.move(0, 1);
        }else if (y == gridSize - 1 && x < gridSize-1){
            goose.move(1,0);
        }else if (x == gridSize -1 && y > 0){
            goose.move(0,-1);
        }
        refreshShadows();
        if(goose.getX() == gridSize -1 && goose.getY() == 0){
            win = true;
        }
    }
        }
    
    private void boxesGeneration(){
        for (int i = 1; i < gridSize -1; i++){
            if(Math.random() < 0.3){
                boxes.add(new Point(i,1));
            }
            if(Math.random() < 0.3){
                boxes.add(new Point(i, gridSize -2));
            }
            if(Math.random() < 0.3){
                boxes.add(new Point(1,i));
            }
            if(Math.random() < 0.3){
                boxes.add(new Point(gridSize-2, i));
            }
        }
    }

    private void refreshShadows(){
        shadows.clear();
        Point origin = new Point(dog.getX(),dog.getY());
        Point dir = directionToDelta(dog.getDirection());
        boolean trovatoOstacolo = false;
        Point scatolaPos=null;
        for(int d=1; d < gridSize && !trovatoOstacolo; d++){
            int x = origin.x + dir.x * d;
            int y = origin.y + dir.y * d;
            if(inBounds(x,y)){
                Point current = new Point(x,y);
                if(boxes.contains(current)){
                    trovatoOstacolo=true;
                    scatolaPos=current;
                }
            }
        }
        if(trovatoOstacolo && scatolaPos != null){
            boolean ombraTrovata = false;
            for(int i = 1; i < gridSize && !ombraTrovata; i++){
                int ox = scatolaPos.x + dir.x*i;
                int oy = scatolaPos.y + dir.y*i;
                if (inBounds(ox, oy)){
                    if(ox == 0 || oy == 0 || ox == gridSize -1 || oy == gridSize -1){
                        shadows.add(new Point(ox,oy));
                        ombraTrovata=true;
                    }
                }
            }
        }
    }

        private boolean inBounds(int x, int y){
            return x >= 0 && y >= 0 && x < gridSize-1 && y < gridSize-1;
        }
        private Point directionToDelta(int dir){
            return switch(dir){
                case 1 -> new Point(1,0);
                case 2 -> new Point(0,1);
                case 3 -> new Point(-1,0);
                default -> new Point(0,0);
            };
        }

        public Goose getGoose(){
            return goose;
        }
        public Dog getDog(){
            return dog;
        }
        public List<Point> getBoxes(){
            return boxes;
        }
        public Set<Point> getShadows(){
            return shadows;
        }
        public int getGridSize(){
            return gridSize;}
            public boolean isPartitaVinta(){
                return win;
            }
        }
