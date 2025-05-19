package it.unibo.goosegame.utilities;

import it.unibo.goosegame.model.cardsatchel.impl.CardSatchelModelImpl;

/**
 * Represents a player in the game.
 */
public final class Player {
    private final String name;
    private final CardSatchelModelImpl satchel;

    /**
     * Constructs a new player with the given name.
     * @param name the name of the player
     */
    public Player(final String name) {
        this.name = name;
        this.satchel = new CardSatchelModelImpl();
    }

    /**
     * Returns the name of the player.
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the player's card satchel.
     * @return the player's satchel
     */
    public CardSatchelModelImpl getSatchel() {
        return satchel;
    }

    /**
     * Checks if this player is equal to another object.
     * @param obj the object to compare
     * @return true if the other object is a Player with the same name, false otherwise
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Player player = (Player) obj;
        return name.equals(player.name);
    }

    /**
     * Returns the hash code for this player.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
