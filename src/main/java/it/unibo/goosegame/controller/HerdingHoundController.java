package it.unibo.goosegame.controller;

import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import it.unibo.goosegame.model.minigames.herdinghound.api.Dog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class HerdingHoundController {

    private final HerdingHoundModel model;
    private final Random random = new Random();
    private Timer dogStateTimer;

    public HerdingHoundController(HerdingHoundModel model) {
        this.model = model;
        startDogStateCycle();
    }

    public void nextGooseMove() {
        if (!model.isOver()) {
            model.nextGooseMove();
        }
    }

    private void startDogStateCycle() {
        dogStateTimer = new Timer(0, null);
        dogStateTimer.setRepeats(false);
        dogStateTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.nextDogState();

                int delay;
                Dog.State newState = model.getDog().getState();

                if (newState == Dog.State.ALERT) {
                    delay = 1000;
                } else {
                    delay = 1000 + random.nextInt(3000);
                }

                dogStateTimer.setInitialDelay(delay);
                dogStateTimer.restart();
            }
        });

        dogStateTimer.start();
    }
}