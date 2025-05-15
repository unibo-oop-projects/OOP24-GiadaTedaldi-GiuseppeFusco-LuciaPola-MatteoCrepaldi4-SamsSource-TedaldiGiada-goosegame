package it.unibo.goosegame.utilities;

<<<<<<< HEAD
import java.util.Objects;
=======
import java.io.Serializable;
import java.util.Objects;

/**
 * A class representing a position in a 2D grid.
 * This class is used to store the coordinates of a position
 * and provides methods to access the coordinates, check equality,
 * generate a hash code, and convert the position to a string.
 */
public class Position implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int x;
    private final int y;

    /**
     * Constructor to create a Position object.
     * @param x
     * @param y
     */
    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of the position.
     * @return the x-coordinate
     */
    public int x() {
        return this.x;
    }

    /**
     * Returns the y-coordinate of the position.
     * @return the y-coordinate
     */
    public int y() {
        return this.y;
    }

    /**
     * Checks if this position is equal to another object.
     * Two positions are considered equal if their x and y coordinates are equal.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
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
     * Generates a hash code for this position.
     * The hash code is based on the x and y coordinates.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Returns a string representation of the position.
     * The format is "Position (x, y)".
     *
     * @return the string representation of the position
     */
    @Override
    public String toString() {
        return "Position (" + x + ", " + y + ")";
    }
>>>>>>> Fusco

public class Position {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Position)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Position other = (Position) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }

    @Override
    public String toString() {
        return "Position (" + x + ", " + y + ")";
    }

}