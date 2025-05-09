package it.unibo.goosegame.utilities;

import java.util.Objects;

/**
 * Represents a position in a 2D coordinate system with integer values.
 */
public class Position {

    private final int x;
    private final int y;

    /**
     * Constructs a new Position with the specified x and y coordinates. 
     * 
     * @param x the horizontal coordinate
     * @param y the vertical coordinate
     */
    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x coordinate
     */
    public int x() {
        return this.x;
    }

    /**
     * @return the y coordinate
     */
    public int y() {
        return this.y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Position)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        return this.x == other.x && this.y == other.y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Position (" + x + ", " + y + ")";
    }
}
