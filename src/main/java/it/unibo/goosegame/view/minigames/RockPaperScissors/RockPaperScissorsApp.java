package it.unibo.goosegame.view.minigames.RockPaperScissors;

import javax.swing.SwingUtilities;

public class RockPaperScissorsApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RockPaperScissorsMenu().setVisible(true));
    }
}
