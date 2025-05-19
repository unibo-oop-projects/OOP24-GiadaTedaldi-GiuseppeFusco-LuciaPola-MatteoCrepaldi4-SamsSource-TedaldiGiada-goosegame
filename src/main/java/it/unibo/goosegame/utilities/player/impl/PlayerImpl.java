package it.unibo.goosegame.utilities.player.impl;

import it.unibo.goosegame.utilities.Position;
import it.unibo.goosegame.utilities.player.api.Player;

import java.awt.*;

public class PlayerImpl implements Player {
    private static int CELLS_NUM = 63;

    private String name;        // Name of the player, could be hardcoded or dynamic
    private Color color;        // Color of the player's in game icon
    private int position;       // Real time position of the player
    private Position realPosition;   // Position (in pixel) needed to place the graphical component on the board
    private boolean hasWon;     // Set to true if the player has won (reached cell number 63)


    public PlayerImpl(String name, Color color) {
        this.name = name;
        this.color = color;
        this.position = 0;
        this.hasWon = false;
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public void move(int steps, boolean isForward) {
        if (isForward) {
            if (position + steps <= CELLS_NUM) {
                position += steps;
            } else {
                position = CELLS_NUM - (steps - (CELLS_NUM - position));
            }
        }
        else {
            if (position - steps < 0) {
                position = 0;   // Making sure the player can't go out of bounds
            }
            else {
                position -= steps;
            }
        }

        if (position == CELLS_NUM) {
            this.hasWon = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void goTo(int cellIndex) {
        this.position = cellIndex;
    }

    // === GETTERS ===
    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasWon() {
        return hasWon;
    }
}
