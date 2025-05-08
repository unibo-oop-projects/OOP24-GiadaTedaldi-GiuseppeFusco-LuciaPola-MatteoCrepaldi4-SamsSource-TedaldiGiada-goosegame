package it.unibo.goosegame.controller;

import it.unibo.goosegame.model.minigames.honkmand.HonkMandModel;
import it.unibo.goosegame.utilities.Colors;
import it.unibo.goosegame.view.HonkMandView;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controller del Simon Game che gestisce l'interazione tra modello e vista
 */
public class HonkMandController {
    private HonkMandModel model;
    private HonkMandView view;
    
public HonkMandController(HonkMandModel model, HonkMandView view) {
        this.model = model;
        this.view = view;
        
        initController();
    }
    
    private void initController() {
        // Inizializza la vista
        view.updateScore(model.getScore());
        
        // Aggiungi listener al pulsante di avvio
        view.addStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        
        // Aggiungi listener ai pulsanti colorati
        view.addColorButtonListener(Colors.GREEN, e -> handleButtonClick(Colors.GREEN));
        view.addColorButtonListener(Colors.RED, e -> handleButtonClick(Colors.RED));
        view.addColorButtonListener(Colors.YELLOW, e -> handleButtonClick(Colors.YELLOW));
        view.addColorButtonListener(Colors.BLUE, e -> handleButtonClick(Colors.BLUE));
    }
    
    /**
     * Avvia una nuova partita
     */
    private void startGame() {
        model.startGame();
        view.setGameActive(true);
        view.updateLevel(model.getLevel());
        view.setButtonsEnabled(false);
        view.showMessage("Osserva la sequenza!", false);
        
        // Usa un Timer per riprodurre la sequenza dopo un breve ritardo
        Timer timer = new Timer(1000, e -> {
            ((Timer)e.getSource()).stop(); // Ferma il timer
            playSequence();
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    /**
     * Riproduce la sequenza di colori
     */
    private Timer sequenceTimer;

    private void playSequence() {
        view.setButtonsEnabled(false);
        List<Colors> sequence = model.getSequence();
        
        // Usa un Timer per simulare il delay tra le luci dei pulsanti
        if (sequenceTimer != null && sequenceTimer.isRunning()){
            sequenceTimer.stop();
        }

        final int[] index = {0};

        sequenceTimer = new Timer(700,null);
        ActionListener sequenceAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index[0] < sequence.size()) {
                    Colors color = sequence.get(index[0]);
                    view.lightUpButton(color, 500);
                    index[0]++;
                } else {
                    // Ferma il timer quando la sequenza è completa
                sequenceTimer.stop();
                
                // Aggiungi un breve ritardo prima di abilitare i pulsanti
                Timer enableButtonsTimer = new Timer(500, event -> {
                    view.setButtonsEnabled(true);
                    view.showMessage("Il tuo turno!", false);
                    ((Timer)event.getSource()).stop();
                });
                enableButtonsTimer.setRepeats(false);
                enableButtonsTimer.start();
            }
        }
    };

        sequenceTimer.addActionListener(sequenceAction);
        sequenceTimer.setRepeats(true);
        sequenceTimer.start();
    }
    
    /**
     * Gestisce il click su un pulsante colorato
     */
    private void handleButtonClick(Colors colorId) {
        if (!model.isPlaying()) return;

        view.lightUpButton(colorId, 300);
        HonkMandModel.InputResult result = model.checkPlayerInput(colorId);

        switch (result) {
            case CORRECT:
                // Nessuna azione, attendi altro input
                break;
            case NEXT_ROUND:
                view.showMessage("Ottimo!", false);
                view.updateScore(model.getScore());
                Timer nextRoundTimer = new Timer(1000, e -> {
                    model.nextRound();
                    view.updateLevel(model.getLevel());
                    view.showMessage("Livello " + model.getLevel(), false);
                    Timer playSequenceTimer = new Timer(1000, e2 -> playSequence());
                    playSequenceTimer.setRepeats(false);
                    playSequenceTimer.start();
                    ((Timer)e.getSource()).stop();
                });
                nextRoundTimer.setRepeats(false);
                nextRoundTimer.start();
                break;
            case GAME_WIN:
                view.showMessage("Hai vinto!", false);
                view.setButtonsEnabled(false);
                celebrateVictory();
                Timer winTimer = new Timer(1000, e -> {
                    view.showVictoryDialog();
                    ((Timer)e.getSource()).stop();
                });
                winTimer.setRepeats(false);
                winTimer.start();
                break;
            case GAME_OVER:
                view.showMessage("Game Over!", true);
                view.setButtonsEnabled(false);
                Timer overTimer = new Timer(1000, e -> {
                    view.showGameOverDialog();
                    ((Timer)e.getSource()).stop();
                });
                overTimer.setRepeats(false);
                overTimer.start();
                break;
        }
    }
    
    /**
     * Esegue un'animazione di celebrazione per la vittoria
     */
    private void celebrateVictory() {
        final Colors[] colors = Colors.values();
        final int total = colors.length;
        final int[] index = {0};

        Timer celebrationTimer = new Timer(400, null);
        celebrationTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Spegni il pulsante precedente (se non è il primo giro)
                if (index[0] > 0) {
                    Colors prevColor = colors[index[0] - 1];
                    // Ripristina il colore originale
                    view.lightUpButton(prevColor, 0);
                }
                // Se abbiamo finito la sequenza, ferma il timer
                if (index[0] >= total) {
                    ((Timer) e.getSource()).stop();
                    return;
                }
                // Illumina il pulsante corrente
                view.lightUpButton(colors[index[0]], 300);
                index[0]++;
            }
        });
        celebrationTimer.setRepeats(true);
        celebrationTimer.start();
    }
    
}
