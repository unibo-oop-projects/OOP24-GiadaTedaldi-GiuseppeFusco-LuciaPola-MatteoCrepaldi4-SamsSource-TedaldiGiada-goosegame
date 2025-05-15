package it.unibo.goosegame.utilities;

/**
 * A generic class representing a pair of objects.
 * This class is used to store two related objects of potentially different types.
 *
 * @param <X> the type of the first object
 * @param <Y> the type of the second object
 */
public class Pair<X, Y> {

    private final X x;
    private final Y y;

    /**
     * Constructor to create a Pair object.
     *
     * @param x the first object
     * @param y the second object
     */
    public Pair(final X x, final Y y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the first object of the pair.
     *
     * @return the first object
     */
    public X getX() {
        return this.x;
    }

    /**
     * Returns the second object of the pair.
     *
     * @return the second object
     */
    public Y getY() {
        return this.y;
    }

    /**
     * Checks if this pair is equal to another object.
     * Two pairs are considered equal if both their first and second objects are equal.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair other = (Pair) obj;
        if (this.x == null) {
            if (other.x != null) {
                return false;
            }
        } else if (!x.equals(other.x)) {
            return false;
        }
        if (y == null) {
            if (other.y != null) {
                return false;
            }
        } else if (!y.equals(other.y)) {
            return false;
        }
        return true;
    }

    /**
     * Generates a hash code for this pair.
     * The hash code is computed based on the hash codes of both objects in the pair.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((x == null) ? 0 : y.hashCode());
        return result;
    }

    /**
     * Returns a string representation of this pair.
     */
    @Override
    public String toString() {
        return "Pair [x=" + x + ", y=" + y + "]";
    }

}
