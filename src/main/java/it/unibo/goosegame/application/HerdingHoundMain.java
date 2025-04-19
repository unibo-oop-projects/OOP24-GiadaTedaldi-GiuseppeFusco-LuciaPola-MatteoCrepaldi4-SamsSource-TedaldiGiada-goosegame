package it.unibo.goosegame.application;

import it.unibo.goosegame.controller.HerdingHoundController;
import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import it.unibo.goosegame.view.HerdingHoundView;

import javax.swing.SwingUtilities;

public class HerdingHoundMain{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final int gridSize = 20; // puoi cambiarlo a piacere
            HerdingHoundModel model = new HerdingHoundModel(gridSize);
            HerdingHoundView view = new HerdingHoundView(model);
            HerdingHoundController controller = new HerdingHoundController(model, view);
            controller.startGame();
        });
    }
}
