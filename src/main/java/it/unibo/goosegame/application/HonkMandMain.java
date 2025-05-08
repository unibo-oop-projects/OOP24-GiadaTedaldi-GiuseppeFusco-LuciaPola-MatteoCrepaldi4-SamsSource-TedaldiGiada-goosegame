package it.unibo.goosegame.application;

import it.unibo.goosegame.controller.HonkMandController;
import it.unibo.goosegame.model.minigames.honkmand.HonkMandModel;
import it.unibo.goosegame.view.HonkMandView;

import javax.swing.*;

/**
 * Entry point dell'applicazione HonkMand (Simon Game).
 * Inizializza MVC e avvia l'interfaccia utente nel thread di Swing.
 */
public class HonkMandMain {
    /**
     * Metodo principale che avvia l'applicazione.
     * @param args argomenti da linea di comando (non usati)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HonkMandModel model = new HonkMandModel();
            HonkMandView view = new HonkMandView();
            new HonkMandController(model, view);
        });
    }
}