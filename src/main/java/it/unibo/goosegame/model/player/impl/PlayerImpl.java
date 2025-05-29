package it.unibo.goosegame.model.player.impl;

import it.unibo.goosegame.model.player.api.Player;

import java.awt.Color;
import java.util.List;

/**
 * Implementation of {@link Player}.
 */
public final class PlayerImpl implements Player {
    private static final int CELLS_NUM = 63;

    /**
     * Array containing all the possible player colors.
     */
    private static final Color[] COLORS = { Color.red, Color.yellow, Color.green, Color.blue, Color.cyan };

    private final String name;            // Name of the player, could be hardcoded or dynamic
    private final Color color;            // Color of the player's in game icon
    private int position;           // Real time position of the player

    /**
     * Constructor for the {@link PlayerImpl} class.
     *
     * @param name name of the player
     * @param colorIndex color index of player's icon
     */
    public PlayerImpl(final String name, final int colorIndex) {
        this.name = name;
        this.color = COLORS[colorIndex];
        this.position = 0;
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    public void move(final int steps, final boolean isForward) {
        if (isForward) {
            if (position + steps <= CELLS_NUM) {
                position += steps;
            } else {
                position = CELLS_NUM - (steps - (CELLS_NUM - position));
            }
        } else {
            if (position - steps < 0) {
                position = 0;   // Making sure the player can't go out of bounds
            } else {
                position -= steps;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void goTo(final int cellIndex) {
        this.position = cellIndex;
    }

    // === GETTERS ===
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public List<Color> getColorsList() {
        return List.of(COLORS);
    }
}
