
package it.unibo.goosegame.model.minigames.herdinghound.impl;
import java.util.Random;

import it.unibo.goosegame.model.minigames.herdinghound.api.Boxgen;
import it.unibo.goosegame.utilities.Pair;

import java.util.ArrayList;
import java.util.List;

public class BoxImpl implements Boxgen {

    private List<Pair<Integer,Integer>> allBoxes = new ArrayList<>();
    private List<Pair<Integer,Integer>> shadows = new ArrayList<>();
    private int gridSize;
    private int boxDistance;
    private Random random;
    private DogLogicImpl dog;

    public BoxImpl(int gridSize, DogLogicImpl dog){
        this.gridSize=gridSize;
        this.boxDistance=2;
        this.random = new Random();
        this.dog = dog;
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
        if (random.nextDouble() < 0.6) {
            Pair<Integer, Integer> box = new Pair<>(x, y);
            allBoxes.add(box);
            generateShadows(box);
        }
    }

    for (int x = boxDistance + 1, y = gridSize - boxDistance; x < gridSize - boxDistance; x++) {
        if (random.nextDouble() < 0.6) {
            Pair<Integer, Integer> box = new Pair<>(x, y);
            allBoxes.add(box);
            generateShadows(box);
        }
    }

    for (int x = gridSize - boxDistance, y = gridSize - boxDistance - 1; y >= boxDistance; y--) {
        if (random.nextDouble() < 0.6) {
            Pair<Integer, Integer> box = new Pair<>(x, y);
            allBoxes.add(box);
            generateShadows(box);
        }
    }
}

private void generateShadows(Pair<Integer, Integer> box) {
    if(this.dog.getCoord().getX() > box.getX()){
        shadows.add(new Pair<Integer,Integer>(box.getX()-1, box.getY()));
    }else if(this.dog.getCoord().getY() < box.getY()){
        shadows.add(new Pair<Integer,Integer>(box.getX(), box.getY()+1));
    }else if(this.dog.getCoord().getX() < box.getX()){
        shadows.add(new Pair<Integer,Integer>(box.getX()+1, box.getY()));
    }
    }

    public List<Pair<Integer,Integer>> getShadows(){
        return new ArrayList<>(shadows);
    }
}



    
