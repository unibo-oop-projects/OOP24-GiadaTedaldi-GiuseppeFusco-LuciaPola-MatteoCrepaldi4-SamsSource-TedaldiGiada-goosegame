package it.unibo.goosegame.view.general.api;

import javax.swing.JButton;
import javax.swing.JPanel;

public interface MinigameMenuAbstractInterface {
    /**
     * Initializes and sets up the components of the minigames's menu view.
     */
    void initializeView();
    /**
     * @return the start button
     */
    JButton getStartButton();
    /**
     * @return the start button
     */
    JPanel getCardPanel();
}