package it.unibo.goosegame.model.player.impl;

import it.unibo.goosegame.controller.cardsatchel.CardSatchelController;
import it.unibo.goosegame.model.player.api.Player;

import java.awt.Color;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Implementation of {@link Player}.
 */
public final class PlayerImpl implements Player {
    /**
     * Array containing all the possible player colors.
     */
    private static final Color[] COLORS = { Color.red, Color.yellow, Color.green, Color.blue, Color.cyan };

    private final String name;            // Name of the player, could be hardcoded or dynamic
    private final Color color;            // Color of the player's in game icon
    private int position;                 // Real time position of the player
    private CardSatchelController satchel;

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
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPosition() {
        return position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIndex(final int index) {
        this.position = index;
    }

    /**
     * Returns the player's satchel.
     * The returned object is mutable and modifications will affect the player directly.
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
    @Override
    public CardSatchelController getSatchel() {
        return this.satchel;
    }

    @Override
    public void setSatchel(CardSatchelController satchel) {
        this.satchel = satchel;
    }
}
