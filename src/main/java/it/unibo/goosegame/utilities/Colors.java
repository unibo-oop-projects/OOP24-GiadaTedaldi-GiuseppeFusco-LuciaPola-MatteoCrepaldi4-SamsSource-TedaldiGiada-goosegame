package it.unibo.goosegame.utilities;

import java.awt.Color;

public enum Colors {
    GREEN(Color.GREEN),
    RED(Color.RED),
    YELLOW(Color.YELLOW),
    BLUE(Color.BLUE);

    private final Color awtColor;

    Colors(Color awtColor) {
        this.awtColor = awtColor;
    }

    public Color getAwtColor() {
        return awtColor;
    }
}
