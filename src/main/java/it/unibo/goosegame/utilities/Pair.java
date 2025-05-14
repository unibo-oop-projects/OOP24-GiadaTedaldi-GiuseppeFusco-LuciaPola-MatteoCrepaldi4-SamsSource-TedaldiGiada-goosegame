package it.unibo.goosegame.utilities;

/**
 * Immutable generic pair of two elements.
 * @param <X> the type of the first element
 * @param <Y> the type of the second element
 */
public final class Pair<X, Y> {

    private final X x;
    private final Y y;

    /**
     * Constructs a Pair with the given elements.
     * @param x the first element
     * @param y the second element
     */
    public Pair(final X x, final Y y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the first element of the pair.
     * @return the first element
     */
    public X getX() {
        return this.x;
    }

    /**
     * Returns the second element of the pair.
     * @return the second element
     */
    public Y getY() {
        return this.y;
    }

    /**
     * Checks if this pair is equal to another object.
     * @param obj the object to compare
     * @return true if the pairs are equal, false otherwise
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
     * Returns the hash code for this pair.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        return result;
    }

    /**
     * Returns a string representation of the pair.
     * @return string representation
     */
    @Override
    public String toString() {
        return "Pair [x=" + x + ", y=" + y + "]";
    }
}
