package it.unibo.goosegame.application;

import it.unibo.goosegame.controller.HerdingHoundController;
import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import it.unibo.goosegame.view.HerdingHoundView;

import javax.swing.JFrame;

public class HerdingHoundMain {
        public static void main(String[] args) {
            int gridSize = 15;
            HerdingHoundModel model = new HerdingHoundModel(gridSize);
            HerdingHoundView view = new HerdingHoundView(model);
            new HerdingHoundController(model, view);
    
            JFrame frame = new JFrame("Herding Hound");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(view);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }
    