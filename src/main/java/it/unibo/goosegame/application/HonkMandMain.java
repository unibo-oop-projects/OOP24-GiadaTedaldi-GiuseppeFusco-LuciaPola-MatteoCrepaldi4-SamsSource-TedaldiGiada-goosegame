package it.unibo.goosegame.application;

import it.unibo.goosegame.controller.HonkMandController;
import it.unibo.goosegame.model.minigames.honkmand.HonkMandModel;
import it.unibo.goosegame.view.HonkMandView;

import javax.swing.*;
import java.awt.*;

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
            JFrame frame = new JFrame("HonkMand");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setResizable(true);

            HonkMandView view = new HonkMandView();
            view.setFrameRef(frame);
            frame.add(view, BorderLayout.CENTER);

            frame.setVisible(true);

            HonkMandModel model = new HonkMandModel();
            new HonkMandController(model, view);
        });
    }
}
