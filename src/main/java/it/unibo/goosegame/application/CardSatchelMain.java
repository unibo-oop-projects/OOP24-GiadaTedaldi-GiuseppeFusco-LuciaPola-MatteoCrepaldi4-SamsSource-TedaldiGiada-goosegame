package it.unibo.goosegame.application;

import it.unibo.goosegame.utilities.Player;

public final class CardSatchelMain {

    private CardSatchelMain() {
        throw new AssertionError();
    }

    public static void main(final String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Crea un player di test
            Player player = new Player("TestPlayer");
            // Mostra il satchel tramite il controller
            player.getSatchel().showSatchel();
        });
    }
}