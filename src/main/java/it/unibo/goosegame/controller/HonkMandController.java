package it.unibo.goosegame.controller;

import it.unibo.goosegame.model.minigames.honkmand.HonkMandModel;
import it.unibo.goosegame.model.general.MinigamesModel;
import java.util.List;

public class HonkMandController {
    private HonkMandModel model;
    private boolean userTurnComplete = false;

    public HonkMandController(HonkMandModel model) {
        this.model = model;
    }

    // Metodo per iniziare il gioco
    public void startGame() {
        model.resetGame();  // Resetta il gioco
        model.addNewColor(); // Aggiungi il primo colore
    }

    // Metodo per gestire il passaggio al turno successivo
    public void nextTurn() {
        model.switchTurn();
        if (model.getUserTurn()) {
            // Aggiungi un nuovo colore quando l'utente inizia il turno
            model.addNewColor();
        }
    }

    // Metodo per gestire l'input dell'utente
    public void userPressedColor(int colorIndex) {
        if (!model.getUserTurn() || model.isOver()) {
            return;  // Se non è il turno dell'utente o il gioco è finito, non fare nulla
        }

        // Controlla l'input dell'utente
        boolean correctInput = model.checkInput(colorIndex);

        // Se la sequenza è corretta, l'utente continua; se completa la sequenza, la aggiorniamo
        if (correctInput) {
            if (model.isSequenceComplete()) {
                // Se la sequenza è completata con successo, aggiorna i round vinti
                model.incrementSuccessfulRounds();
                userTurnComplete = true;
            }
        } else {
            // Se l'utente sbaglia, il gioco finisce
            userTurnComplete = true;
        }
    }

    // Metodo per ottenere la sequenza attuale
    public List<Integer> getSequence() {
        return model.getSequence();
    }

    // Metodo per ottenere lo stato del turno dell'utente
    public boolean isUserTurn() {
        return model.getUserTurn();
    }

    // Metodo per ottenere se il gioco è finito
    public boolean isGameOver() {
        return model.isOver();
    }

    // Metodo per ottenere il numero di round vinti
    public int getSuccessfulRounds() {
        return model.getSuccessfulRounds();
    }

    // Metodo per ottenere lo stato di vittoria
    public boolean isGameWon() {
        return model.isGameWon();
    }

    // Metodo per ottenere lo stato di sconfitta
    public boolean isGameLost() {
        return model.isGameLost();
    }

    // Metodo per ottenere il passo corrente
    public int getCurrentStep() {
        return model.getCurrentStep();
    }

    // Metodo per gestire il passaggio tra i turni
    public void switchTurn() {
        model.switchTurn();
        userTurnComplete = false;  // Reset dello stato del turno
    }

    // Metodo per sapere se il turno dell'utente è completo
    public boolean isUserTurnComplete() {
        return userTurnComplete;
    }
}

