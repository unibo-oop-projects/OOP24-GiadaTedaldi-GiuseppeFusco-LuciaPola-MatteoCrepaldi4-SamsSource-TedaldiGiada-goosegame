package it.unibo.goosegame.utilities;

import java.awt.Color;

/**
 * Enum representing the available colors for the game.
 */
public enum Colors {
    /** Green color. */
    GREEN(Color.GREEN),
    /** Red color. */
    RED(Color.RED),
    /** Yellow color. */
    YELLOW(Color.YELLOW),
    /** Blue color. */
    BLUE(Color.BLUE);

    private final Color awtColor;

    /**
     * Constructs a Colors enum value.
     * @param awtColor the corresponding AWT Color
     */
    Colors(final Color awtColor) {
        this.awtColor = awtColor;
    }

    /**
     * Returns the corresponding AWT Color.
     * @return the AWT Color
     */
    public Color getAwtColor() {
        return awtColor;
    }
}
