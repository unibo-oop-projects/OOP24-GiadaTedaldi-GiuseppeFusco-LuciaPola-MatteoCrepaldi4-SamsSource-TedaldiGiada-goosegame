package it.unibo.goosegame.controller;

import javax.swing.*;

import it.unibo.goosegame.model.minigames.honkmand.HonkMandModel;
import it.unibo.goosegame.view.HonkMandView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HonkMandController {
    private HonkMandModel model;
    private HonkMandView view;

    public HonkMandController(HonkMandModel model, HonkMandView view) {
        this.model = model;
        this.view = view;

        view.setButtonListener(new ButtonHandler());
        playSequence();  // mostra subito la sequenza iniziale
    }

    private void playSequence() {
        model.setUserTurn(false);
        List<Integer> sequence = model.getSequence();

        Timer timer = new Timer(1000, null);
        final int[] index = {0};

        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (index[0] < sequence.size()) {
                    int color = sequence.get(index[0]);
                    view.flashButton(color);
                    view.playSound(color);
                    index[0]++;
                } else {
                    timer.stop();
                    model.setUserTurn(true);
                }
            }
        });

        timer.setInitialDelay(500);
        timer.start();
    }

    private void handleUserInput(int colorIndex) {
        if (!model.isUserTurn() || model.isOver()) return;

        view.flashButton(colorIndex);
        view.playSound(colorIndex);

        boolean completed = model.checkInput(colorIndex);

        if (model.isGameLost()) {
            view.showMessage("Errore! Hai perso!");
            view.enableButtons(false);
        } else if (model.isGameWon()) {
            view.showMessage("Hai vinto! Complimenti!");
            view.enableButtons(false);
        } else if (completed) {
            model.addNewColor();
            view.showMessage("Ben fatto! Prossimo livello...");
            Timer t = new Timer(1000, e -> playSequence());
            t.setRepeats(false);
            t.start();
        }
    }

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int color = Integer.parseInt(e.getActionCommand());
            handleUserInput(color);
        }
    }
}

