package it.unibo.goosegame.view.minigames.herdinghound.api;

import javax.swing.JFrame;

/**
 * Interface for the Herding Hound minigame view.
 * Provides methods to control the countdown, blinking animation, view updates, and game over panel.
 */
public interface HerdingHoundView {
    /**
     * Starts the initial countdown, then executes the callback when finished.
     * @param onFinish the callback to execute at the end of the countdown
     */
    void startCountdown(Runnable onFinish);

    /**
     * Returns true if the countdown is active.
     * @return true if the countdown is active, false otherwise
     */
    boolean isCountdownActive();

    /**
     * Starts the end-of-game blinking animation.
     * @param frame the JFrame on which to show the final panel
     * @param hasWon true if the player has won
     */
    void startBlinking(JFrame frame, boolean hasWon);

    /**
     * Updates the view (calls repaint).
     */
    void updateView();

    /**
     * Shows the end-of-game panel.
     * @param frame the JFrame on which to show the panel
     * @param hasWon true if the player has won
     */
    void showGameOverPanel(JFrame frame, boolean hasWon);
}
