package it.unibo.goosegame.model.minigames.RockPaperScissors.impl;

import javax.swing.SwingUtilities;

public class RockPaperScissorsApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RockPaperScissorsMenu().setVisible(true));
    }
}