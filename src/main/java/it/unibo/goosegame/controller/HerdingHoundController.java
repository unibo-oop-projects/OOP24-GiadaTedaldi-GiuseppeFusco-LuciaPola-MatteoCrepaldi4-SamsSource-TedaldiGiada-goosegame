package it.unibo.goosegame.controller;

import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import it.unibo.goosegame.view.HerdingHoundView;
import it.unibo.goosegame.model.minigames.herdinghound.impl.DogImpl;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class HerdingHoundController {
    private final HerdingHoundModel model;
    private final HerdingHoundView view;
    private Timer dogStateTimer;
    private final Random rnd = new Random();

    public HerdingHoundController(HerdingHoundModel model, HerdingHoundView view) {
        this.model = model;
        this.view = view;

        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !model.isOver()) {
                    model.nextGooseMove();
                    view.updateView();
                    if (model.isOver()) {
                        view.showGameOver();
                    }
                }
            }
        });
        view.setFocusable(true);
        view.requestFocusInWindow();

        scheduleNextDogState(model.getDog().getState());
    }

    private void scheduleNextDogState(DogImpl.State currentState) {
        int delay;
        if (currentState == DogImpl.State.ALERT) {
            delay = 1000;
        } else {
            delay = 2000 + rnd.nextInt(3) * 1000;
        }

        if (dogStateTimer != null) dogStateTimer.stop();
        dogStateTimer = new Timer(delay, e -> {
            model.nextDogState();
            view.updateView();
            if (model.isOver()) {
                view.showGameOver();
            } else {
                scheduleNextDogState(model.getDog().getState());
            }
        });
        dogStateTimer.setRepeats(false);
        dogStateTimer.start();
    }
}
