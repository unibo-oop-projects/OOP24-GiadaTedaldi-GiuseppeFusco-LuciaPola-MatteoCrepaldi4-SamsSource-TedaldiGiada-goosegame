package it.unibo.goosegame.controller;

import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import it.unibo.goosegame.view.HerdingHoundView;
import it.unibo.goosegame.model.minigames.herdinghound.api.Dog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class HerdingHoundController {

    private final HerdingHoundModel model;
    private final HerdingHoundView view;
    private Timer gameTimer;

    public HerdingHoundController(HerdingHoundModel model, HerdingHoundView view) {
        this.model = model;
        this.view = view;
        this.view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                handleKeyPressed(e);
            }
        });
        this.view.setFocusable(true);
        startGameTimer();  
    }

    private void handleKeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            model.nextGooseMove();
            view.updateView();
            if (model.isOver()) {
                view.showGameOver(model.getGameState());
            }
        }
    }

    private void startGameTimer() {
        gameTimer = new Timer(1000, e -> updateGame());
        gameTimer.start();
    }

    private void updateGame() {
        model.nextGooseMove();
        model.nextDogState();
        view.updateView();
        if (model.isOver()) {
            gameTimer.stop();
            view.showGameOver(model.getGameState());
        }
    }

    public void startGame() {
        view.initialize();
        model.resetGame();
        view.updateView();
    }

    public void resetGame() {
        model.resetGame();
        view.updateView();
    }

    public void nextGooseMove() {
        if (!model.isOver()) {
            model.nextGooseMove();
        }
    }
}