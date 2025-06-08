package it.unibo.goosegame.view.minigames.snake.api;

import java.awt.event.KeyListener;

/**
 * Interface for the Snake game view.
 */
public interface SnakeView {
    /**
     *
     * This method is responsible for updating the view when the game state changes.
     * It repaints the panel to reflect the current state of the game.
     *
     * @param win indicates whether the player has won or lost the game
     */
    void showOverMessage(boolean win);

    /**
     * Repaints the game panel to reflect the current state of the game.
     */
    void repaint();

    /**
     * Adds a KeyListener to the view.
     * This allows the view to respond to key events, such as arrow key presses for controlling the snake.
     *
     * @param l the KeyListener to be added
     */
    void addKeyListener(KeyListener l);
}
