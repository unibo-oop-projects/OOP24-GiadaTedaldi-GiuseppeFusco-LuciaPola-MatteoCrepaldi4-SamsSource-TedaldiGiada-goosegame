package it.unibo.goosegame.application;

import it.unibo.goosegame.controller.HerdingHoundController;
import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import it.unibo.goosegame.view.HerdingHoundView;

import java.awt.*;

import javax.swing.JFrame;

public class HerdingHoundMain {
        public static void main(String[] args) {
            int gridSize = 21;
            HerdingHoundModel model = new HerdingHoundModel(gridSize);
            HerdingHoundView view = new HerdingHoundView(model);
            new HerdingHoundController(model, view);
    
            JFrame frame = new JFrame("Herding Hound");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(view, BorderLayout.CENTER);

            frame.setSize(600, 600); 
            frame.setLocationRelativeTo(null);
            frame.setResizable(true);
            frame.setVisible(true);

            view.requestFocusInWindow();
        }
    }
    