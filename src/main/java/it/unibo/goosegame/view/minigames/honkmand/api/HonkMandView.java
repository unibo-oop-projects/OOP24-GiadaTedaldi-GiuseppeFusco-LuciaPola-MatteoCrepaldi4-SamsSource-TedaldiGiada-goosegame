package it.unibo.goosegame.view.minigames.honkmand.api;

import it.unibo.goosegame.utilities.Colors;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * Interface for the HonkMand minigame view.
 * Provides methods to control the main frame, update the view, handle messages, manage buttons, and listeners.
 */
public interface HonkMandView {
    /**
     * Sets the reference to the main JFrame.
     * @param frame the main JFrame reference
     */
    void setFrameRef(JFrame frame);

    /**
     * Updates the displayed level.
     * @param level the new level to display
     */
    void updateLevel(int level);

    /**
     * Updates the displayed score.
     * @param score the new score to display
     */
    void updateScore(int score);

    /**
     * Shows a central message.
     * @param message the message to display
     * @param isError true if the message is an error, false otherwise
     */
    void showMessage(String message, boolean isError);

    /**
     * Clears the central message.
     */
    void clearMessage();

    /**
     * Shows the end-of-game panel.
     * @param hasWon true if the player has won
     */
    void showGameOverPanel(boolean hasWon);

    /**
     * Enables or disables the colored buttons.
     * @param enabled true to enable the buttons, false to disable
     */
    void setButtonsEnabled(boolean enabled);

    /**
     * Sets the game active or inactive.
     * @param active true if the game is active, false otherwise
     */
    void setGameActive(boolean active);

    /**
     * Adds a listener to the start button.
     * @param listener the ActionListener to add
     */
    void addStartButtonListener(ActionListener listener);

    /**
     * Adds a listener to a colored button.
     * @param colorId the color identifier of the button
     * @param listener the ActionListener to add
     */
    void addColorButtonListener(Colors colorId, ActionListener listener);

    /**
     * Lights up a button for a certain duration (glow effect).
     * @param colorId the color identifier of the button
     * @param duration the duration in milliseconds to light up the button
     */
    void lightUpButton(Colors colorId, int duration);
}
