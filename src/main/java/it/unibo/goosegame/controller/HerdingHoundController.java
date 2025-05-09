package it.unibo.goosegame.controller;

import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import it.unibo.goosegame.view.HerdingHoundView;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.model.minigames.herdinghound.impl.DogImpl;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Controller per il minigioco Herding Hound.
 * Gestisce l'interazione tra utente, modello e vista.
 */
public class HerdingHoundController {
    private final HerdingHoundModel model;
    private final HerdingHoundView view;
    private Timer dogStateTimer;
    private Timer gameTimer;
    private final Random rnd = new Random();
    private boolean spacePressed = false;

    public HerdingHoundController(HerdingHoundModel model, HerdingHoundView view) {
        this.model = model;
        this.view = view;

        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !spacePressed && !model.isOver()) {
                    spacePressed = true;
                    model.nextGooseMove();
                    view.updateView();
                    if (model.isOver()) {
                        endGame();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    spacePressed = false;
                }
            }
        });
        view.setFocusable(true);
        view.requestFocusInWindow();

        scheduleNextDogState(model.getDog().getState());

        gameTimer = new Timer(1000, e -> {
            view.updateView();
            if (model.getGameState() != GameState.ONGOING) {
                endGame();
            }
        });
        gameTimer.setInitialDelay(0);
        gameTimer.start();
    }

    private void scheduleNextDogState(DogImpl.State currentState) {
        if (model.isOver()) return;
        int delay = (currentState == DogImpl.State.ALERT)
                    ? 1_000
                    : 2_000 + rnd.nextInt(3) * 1_000;
        if (dogStateTimer != null) dogStateTimer.stop();
        dogStateTimer = new Timer(delay, e -> {
            model.nextDogState();
            view.updateView();
            if (model.isOver()) {
                endGame();
            } else {
                scheduleNextDogState(model.getDog().getState());
            }
        });
        dogStateTimer.setRepeats(false);
        dogStateTimer.start();
    }

    private void endGame() {
        if (gameTimer  != null) gameTimer.stop();
        if (dogStateTimer != null) dogStateTimer.stop();
        view.showGameOver();
        view.setFocusable(false);
    }
}
