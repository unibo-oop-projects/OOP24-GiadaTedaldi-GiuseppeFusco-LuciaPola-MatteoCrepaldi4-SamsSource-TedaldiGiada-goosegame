package it.unibo.goosegame.controller;

import it.unibo.goosegame.model.minigames.honkmand.HonkMandModel;

public class HonkMandController {

    private final HonkMandModel model;

    public HonkMandController(HonkMandModel model) {
        this.model = model;
    }

    public void userPressedColor(int color) {//gli passo il colore cosi che nel model confronta il colore con l'elemento in quello step
        if (!model.getUserTurn() || model.isOver()) {
            return; // Se non è il turno dell'utente o il gioco è finito, non fare nulla
        }

        boolean correct = model.checkInput(color);  // Controlla se il colore è corretto

        if (!correct && model.isGameLost()) {
            // Se l'utente ha sbagliato, il gioco termina
            return;
        }

        if (model.isSequenceComplete()) {

            if (!model.isGameWon()) { // se ancora il gioco non è stato vinto
                model.switchTurn();       // Passa al turno del sistema
                model.addNewColor();      // Aggiungi un nuovo colore alla sequenza
                model.switchTurn();       // Passa di nuovo all'utente
            }
        }
    }

    public boolean isUserTurnComplete() {
        return this.model.getUserTurn();
    }

    public boolean isGameOver() {
        return model.isOver();
    }

    public boolean isGameWon() {
        return model.isGameWon();
    }

    public boolean isGameLost() {
        return model.isGameLost();
    }

    public java.util.List<Integer> getSequence() {
        return model.getSequence();
    }
}


