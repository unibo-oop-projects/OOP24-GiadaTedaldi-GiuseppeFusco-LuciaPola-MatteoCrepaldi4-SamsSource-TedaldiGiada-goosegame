package it.unibo.goosegame.application;

import it.unibo.goosegame.controller.HonkMandController;
import it.unibo.goosegame.model.minigames.honkmand.HonkMandModel;
import it.unibo.goosegame.view.HonkMandView;

import javax.swing.*;

/**
 * Classe principale che avvia l'applicazione Simon Game
 */
public class HonkMandMain {
    public static void main(String[] args) {
        // Assicura che l'interfaccia utente venga creata nel thread di Swing
        SwingUtilities.invokeLater(() -> {
            // Crea il modello
            HonkMandModel model = new HonkMandModel();
            
            // Crea la vista
            HonkMandView view = new HonkMandView();
            
            // Crea il controller e collega modello e vista
            HonkMandController controller = new HonkMandController(model, view);
        });
    }
}