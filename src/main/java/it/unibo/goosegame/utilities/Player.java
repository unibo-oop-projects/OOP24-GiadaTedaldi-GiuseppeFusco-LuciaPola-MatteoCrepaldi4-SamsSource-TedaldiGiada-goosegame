package it.unibo.goosegame.utilities;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.goosegame.model.cardsatchel.api.CardSatchelModel;
import it.unibo.goosegame.model.cardsatchel.impl.CardSatchelModelImpl;

/**
 * Represents a player in the game.
 */
public final class Player {
    private final String name;
    private final CardSatchelModel satchel;

    /**
     * Constructs a new player with the given name.
     * @param name the name of the player
     */
    @SuppressFBWarnings(
    value = "EI_EXPOSE_REP",
    justification = "The satchel is intentionally exposed as its management" 
    + "is handled in a controlled manner elsewhere."
)
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
    @SuppressFBWarnings(
    value = "EI_EXPOSE_REP",
    justification = "The satchel is intentionally exposed because its lifecycle" 
    + "and access are tightly controlled elsewhere in the application."
)
    public CardSatchelModel getSatchel() {
        return this.satchel;
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
