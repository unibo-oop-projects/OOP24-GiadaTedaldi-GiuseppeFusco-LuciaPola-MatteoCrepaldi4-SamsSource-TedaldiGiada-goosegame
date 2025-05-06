package it.unibo.goosegame.controller;

import it.unibo.goosegame.model.minigames.honkmand.HonkMandModel;
import it.unibo.goosegame.utilities.Colors;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controller del Simon Game che gestisce l'interazione tra modello e vista
 */
public class HonkMandController {
    private HonkMandModel model;
    //private HonkMandView view;
    
public HonkMandController(HonkMandModel model /*, HonkMandView view*/) {
        this.model = model;
        //this.view = view;
        
        //initController();
    }
    
    /*private void initController() {
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
    }*/
    
    /**
     * Avvia una nuova partita
     */
    private void startGame() {
        model.startGame();
        //view.setGameActive(true);
        //view.updateLevel(model.getLevel());
        //view.showMessage("Osserva la sequenza!", false);
        
        // Usa un Timer per riprodurre la sequenza dopo un breve ritardo
        Timer timer = new Timer(1000, e -> playSequence());
        timer.setRepeats(false);
        timer.start();
    }
    
    /**
     * Riproduce la sequenza di colori
     */
    private void playSequence() {
        //view.setButtonsEnabled(false);
        List<Colors> sequence = model.getSequence();
        
        // Usa un Timer per simulare il delay tra le luci dei pulsanti
        Timer sequenceTimer = new Timer(700, new ActionListener() {
            private int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < sequence.size()) {
                    Colors color = sequence.get(index);
                    //view.lightUpButton(color, 500);
                    index++;
                } else {
                    // Dopo aver mostrato tutta la sequenza, abilita i pulsanti per l'interazione
                    //view.setButtonsEnabled(true);
                    //view.showMessage("Il tuo turno!", false);
                }
            }
        });
        sequenceTimer.setRepeats(true);
        sequenceTimer.start();
    }
    
    /**
     * Gestisce il click su un pulsante colorato
     */
    private void handleButtonClick(Colors colorId) {
        if (!model.isPlaying()) return;
        
        //view.lightUpButton(colorId, 300);
        
        HonkMandModel.InputResult result = model.checkPlayerInput(colorId);
        
        switch (result) {
            case CORRECT:
                // Continua il gioco
                break;
            case NEXT_ROUND:
                //view.showMessage("Ottimo!", false);
                //view.updateScore(model.getScore());
                
                // Timer per la prossima ronda
                Timer nextRoundTimer = new Timer(1000, e -> {
                    model.nextRound();
                    //view.updateLevel(model.getLevel());
                    //view.showMessage("Livello " + model.getLevel(), false);
                    
                    Timer playSequenceTimer = new Timer(1000, e2 -> playSequence());
                    playSequenceTimer.setRepeats(false);
                    playSequenceTimer.start();
                });
                nextRoundTimer.setRepeats(false);
                nextRoundTimer.start();
                break;
            case GAME_OVER:
                //view.showMessage("Game Over!", true);
                
                // Timer per la fine del gioco
                Timer gameOverTimer = new Timer(500, e -> {
                    //view.gameOverAnimation();
                    endGame();
                    
                    // Mostra il dialogo di game over
                    SwingUtilities.invokeLater(() -> {
                        //view.showGameOverDialog();
                    });
                });
                gameOverTimer.setRepeats(false);
                gameOverTimer.start();
                break;
            case GAME_WIN:
                //view.showMessage("Hai vinto!", false);
                //view.updateScore(model.getScore());
                
                // Timer per animazione di vittoria
                Timer winTimer = new Timer(1000, e -> {
                    celebrateVictory();
                    endGame();
                    
                    // Mostra il dialogo di vittoria
                    SwingUtilities.invokeLater(() -> {
                        //view.showVictoryDialog();
                    });
                });
                winTimer.setRepeats(false);
                winTimer.start();
                break;
        }
    }
    
    /**
     * Esegue un'animazione di celebrazione per la vittoria
     */
    private void celebrateVictory() {
        Timer celebrationTimer = new Timer(200, new ActionListener() {
            private int count = 0;
            private final Colors[] colors = Colors.values();    
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < 3) {
                    //view.lightUpButton(colors[count % 4], 200);
                    count++;
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        celebrationTimer.setRepeats(true);
        celebrationTimer.start();
    }
    
    /**
     * Termina il gioco
     */
    private void endGame() {
        model.setPlaying(false);
        //view.setGameActive(false);
        //view.updateScore(model.getScore());
    }
}
