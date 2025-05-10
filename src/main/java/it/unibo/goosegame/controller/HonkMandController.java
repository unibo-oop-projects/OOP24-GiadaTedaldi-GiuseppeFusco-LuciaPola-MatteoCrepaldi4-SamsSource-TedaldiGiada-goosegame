package it.unibo.goosegame.controller;

import it.unibo.goosegame.model.minigames.honkmand.HonkMandModel;
import it.unibo.goosegame.model.minigames.honkmand.HonkMandModel.GameState;
import it.unibo.goosegame.utilities.Colors;
import it.unibo.goosegame.view.HonkMandView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controller del minigioco HonkMand (Simon Game).
 * Gestisce l'interazione tra modello e vista.
 */
public class HonkMandController {
    private HonkMandModel model;
    private HonkMandView view;
    
    /**
     * Costruttore. Collega modello e vista e inizializza i listener.
     * @param model modello logico del gioco
     * @param view vista grafica del gioco
     */
    public HonkMandController(HonkMandModel model, HonkMandView view) {
        this.model = model;
        this.view = view;
        initController();
    }
    
    /**
     * Inizializza la vista e i listener dei pulsanti.
     */
    private void initController() {
        view.updateScore(model.getScore());
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
     * Avvia una nuova partita e mostra la sequenza.
     */
    private void startGame() {
        model.startGame();
        view.setGameActive(true);
        view.updateLevel(model.getLevel());
        view.setButtonsEnabled(false);
        view.showMessage(it.unibo.goosegame.utilities.HonkMandMessages.WATCH_SEQUENCE, false);
        
        // Usa un Timer per riprodurre la sequenza dopo un breve ritardo
        Timer timer = new Timer(it.unibo.goosegame.utilities.HonkMandConstants.SEQUENCE_START_DELAY, e -> {
            ((Timer)e.getSource()).stop(); // Ferma il timer
            playSequence();
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    /**
     * Riproduce la sequenza di colori tramite animazione.
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

        sequenceTimer = new Timer(it.unibo.goosegame.utilities.HonkMandConstants.SEQUENCE_STEP_DELAY,null);
        ActionListener sequenceAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index[0] < sequence.size()) {
                    Colors color = sequence.get(index[0]);
                    view.lightUpButton(color, it.unibo.goosegame.utilities.HonkMandConstants.BUTTON_LIGHT_DURATION);
                    index[0]++;
                } else {
                    // Ferma il timer quando la sequenza è completa
                    sequenceTimer.stop();
                    // Aggiungi un breve ritardo prima di abilitare i pulsanti
                    Timer enableButtonsTimer = new Timer(it.unibo.goosegame.utilities.HonkMandConstants.BUTTON_ENABLE_DELAY, event -> {
                        view.setButtonsEnabled(true);
                        view.showMessage(it.unibo.goosegame.utilities.HonkMandMessages.YOUR_TURN, false);
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
     * Gestisce il click su un pulsante colorato.
     * @param colorId colore selezionato
     */
    private void handleButtonClick(Colors colorId) {
        // Usa la nuova gestione degli stati
        if (model.getGameState() != GameState.PLAYING) return;

        view.lightUpButton(colorId, it.unibo.goosegame.utilities.HonkMandConstants.BUTTON_CLICK_LIGHT_DURATION);
        HonkMandModel.InputResult result = model.checkPlayerInput(colorId);

        switch (result) {
            case CORRECT:
                // Nessuna azione, attendi altro input
                break;
            case NEXT_ROUND:
                view.showMessage(it.unibo.goosegame.utilities.HonkMandMessages.CORRECT, false);
                view.updateScore(model.getScore());
                Timer nextRoundTimer = new Timer(it.unibo.goosegame.utilities.HonkMandConstants.NEXT_ROUND_DELAY, e -> {
                    model.nextRound();
                    view.updateLevel(model.getLevel());
                    view.showMessage(it.unibo.goosegame.utilities.HonkMandMessages.LEVEL_LABEL + model.getLevel(), false);
                    Timer playSequenceTimer = new Timer(it.unibo.goosegame.utilities.HonkMandConstants.SEQUENCE_START_DELAY, e2 -> playSequence());
                    playSequenceTimer.setRepeats(false);
                    playSequenceTimer.start();
                    ((Timer)e.getSource()).stop();
                });
                nextRoundTimer.setRepeats(false);
                nextRoundTimer.start();
                break;
            case GAME_WIN:
                view.showMessage(it.unibo.goosegame.utilities.HonkMandMessages.WIN, false);
                view.setButtonsEnabled(false);
                celebrateVictory();
                Timer winTimer = new Timer(it.unibo.goosegame.utilities.HonkMandConstants.WIN_DIALOG_DELAY, e -> {
                    view.showGameOverPanel(true);
                    ((Timer)e.getSource()).stop();
                });
                winTimer.setRepeats(false);
                winTimer.start();
                break;
            case GAME_OVER:
                view.showMessage(it.unibo.goosegame.utilities.HonkMandMessages.GAME_OVER, true);
                view.setButtonsEnabled(false);
                Timer overTimer = new Timer(it.unibo.goosegame.utilities.HonkMandConstants.NEXT_ROUND_DELAY, e -> {
                    view.showGameOverPanel(false);
                    ((Timer)e.getSource()).stop();
                });
                overTimer.setRepeats(false);
                overTimer.start();
                break;
        }
    }
    
    /**
     * Esegue un'animazione di celebrazione per la vittoria.
     */
    private void celebrateVictory() {
        final Colors[] colors = Colors.values();
        final int total = colors.length;
        final int[] index = {0};

        Timer celebrationTimer = new Timer(it.unibo.goosegame.utilities.HonkMandConstants.CELEBRATION_STEP_DELAY, null);
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
                view.lightUpButton(colors[index[0]], it.unibo.goosegame.utilities.HonkMandConstants.BUTTON_CLICK_LIGHT_DURATION);
                index[0]++;
            }
        });
        celebrationTimer.setRepeats(true);
        celebrationTimer.start();
    }
    
}
