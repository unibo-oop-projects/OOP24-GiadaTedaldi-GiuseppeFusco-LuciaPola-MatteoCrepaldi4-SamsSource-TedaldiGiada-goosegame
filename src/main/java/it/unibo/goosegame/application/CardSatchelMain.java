package it.unibo.goosegame.application;

import javax.swing.SwingUtilities;

/**
 * Main class to test the CardSatchel GUI.
 */
public final class CardSatchelMain {

    private CardSatchelMain() {
        throw new AssertionError();
    }

    /**
     * Launches the CardSatchel test GUI.
     * @param args the command line arguments (not used)
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            new it.unibo.goosegame.view.cardsatchel.impl.CardSatchelFrame().setVisible(true);
        });
    }
}
