package it.unibo.goosegame.controller;

import it.unibo.goosegame.model.minigames.honkmand.HonkMandModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Controller del Simon Game che gestisce l'interazione tra modello e vista
 */
public class HonkMandController {
    private HonkMandModel model;
    private HonlMandView view;
    private ExecutorService executor;
    
    public HonkMandController(HonkMandModel model, HonkMandView view) {
        this.model = model;
        this.view = view;
        this.executor = Executors.newSingleThreadExecutor();
        
        initController();
    }
    
    private void initController() {
        // Inizializza la vista
        view.updateScore(model.getScore(), model.getHighScore());
        
        // Aggiungi listener al pulsante di avvio
        view.addStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        
        // Aggiungi listener ai pulsanti colorati
        view.addColorButtonListener(SimonModel.GREEN, e -> handleButtonClick(SimonModel.GREEN));
        view.addColorButtonListener(SimonModel.RED, e -> handleButtonClick(SimonModel.RED));
        view.addColorButtonListener(SimonModel.YELLOW, e -> handleButtonClick(SimonModel.YELLOW));
        view.addColorButtonListener(SimonModel.BLUE, e -> handleButtonClick(SimonModel.BLUE));
        
        // Aggiungi listener alla checkbox della modalità strict
        view.addStrictModeListener(e -> model.setStrictMode(view.isStrictModeSelected()));
    }
    
    /**
     * Avvia una nuova partita
     */
    private void startGame() {
        model.startGame();
        view.setGameActive(true);
        view.updateLevel(model.getLevel());
        view.showMessage("Osserva la sequenza!", false);
        
        // Usa un thread separato per non bloccare l'interfaccia
        SwingUtilities.invokeLater(() -> {
            Timer timer = new Timer(1000, e -> playSequence());
            timer.setRepeats(false);
            timer.start();
        });
    }
    
    /**
     * Riproduce la sequenza di colori
     */
    private void playSequence() {
        executor.execute(() -> {
            view.setButtonsEnabled(false);
            List<String> sequence = model.getSequence();
            
            for (String color : sequence) {
                try {
                    SwingUtilities.invokeAndWait(() -> view.lightUpButton(color, 500));
                    Thread.sleep(700);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            SwingUtilities.invokeLater(() -> {
                view.setButtonsEnabled(true);
                view.showMessage("Il tuo turno!", false);
            });
        });
    }
    
    /**
     * Gestisce il click su un pulsante colorato
     */
    private void handleButtonClick(String colorId) {
        if (!model.isPlaying()) return;
        
        view.lightUpButton(colorId, 300);
        
        SimonModel.InputResult result = model.checkPlayerInput(colorId);
        
        switch (result) {
            case CORRECT:
                // Continua il gioco
                break;
            case NEXT_ROUND:
                view.showMessage("Ottimo!", false);
                view.updateScore(model.getScore(), model.getHighScore());
                
                Timer nextRoundTimer = new Timer(1000, e -> {
                    model.nextRound();
                    view.updateLevel(model.getLevel());
                    view.showMessage("Livello " + model.getLevel(), false);
                    
                    Timer playSequenceTimer = new Timer(1000, e2 -> playSequence());
                    playSequenceTimer.setRepeats(false);
                    playSequenceTimer.start();
                });
                nextRoundTimer.setRepeats(false);
                nextRoundTimer.start();
                break;
            case RETRY:
                view.showMessage("Errore! Riprova", true);
                
                Timer retryTimer = new Timer(1000, e -> playSequence());
                retryTimer.setRepeats(false);
                retryTimer.start();
                break;
            case GAME_OVER:
                view.showMessage("Game Over!", true);
                
                Timer gameOverTimer = new Timer(500, e -> {
                    view.gameOverAnimation();
                    endGame();
                    
                    // Mostra il dialogo di game over
                    SwingUtilities.invokeLater(() -> {
                        view.showGameOverDialog();
                    });
                });
                gameOverTimer.setRepeats(false);
                gameOverTimer.start();
                break;
            case GAME_WIN:
                view.showMessage("Hai vinto!", false);
                view.updateScore(model.getScore(), model.getHighScore());
                
                Timer winTimer = new Timer(1000, e -> {
                    // Animazione di vittoria
                    celebrateVictory();
                    endGame();
                    
                    // Mostra il dialogo di vittoria
                    SwingUtilities.invokeLater(() -> {
                        view.showVictoryDialog();
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
        executor.execute(() -> {
            try {
                // Lampeggia tutti i pulsanti in sequenza
                for (int i = 0; i < 3; i++) {
                    SwingUtilities.invokeAndWait(() -> view.lightUpButton(SimonModel.GREEN, 200));
                    Thread.sleep(200);
                    
                    SwingUtilities.invokeAndWait(() -> view.lightUpButton(SimonModel.RED, 200));
                    Thread.sleep(200);
                    
                    SwingUtilities.invokeAndWait(() -> view.lightUpButton(SimonModel.YELLOW, 200));
                    Thread.sleep(200);
                    
                    SwingUtilities.invokeAndWait(() -> view.lightUpButton(SimonModel.BLUE, 200));
                    Thread.sleep(200);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    /**
     * Termina il gioco
     */
    private void endGame() {
        model.setPlaying(false);
        view.setGameActive(false);
        view.updateScore(model.getScore(), model.getHighScore());
    }
    
    /**
     * Chiude le risorse
     */
    public void shutdown() {
        executor.shutdown();
    }
}