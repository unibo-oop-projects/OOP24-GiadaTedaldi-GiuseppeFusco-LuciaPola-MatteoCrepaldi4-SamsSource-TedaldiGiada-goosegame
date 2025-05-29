package it.unibo.goosegame.utilities;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.goosegame.controller.cardsatchel.CardSatchelController;

/**
 * Represents a player in the game.
 */
public final class Player {
    private final String name;
    private final CardSatchelController satchel;
    private int caselle;

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
        this.satchel = new CardSatchelController(this);
        this.caselle = 0;
    }

    /**
     * Returns the name of the player.
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    public void move(final int steps, boolean foreward){
        if (foreward){
            this.caselle += steps;
        }else{
            this.caselle -= steps;
        }
    }

    public int getCaselle() {
        return this.caselle;
    }

    /**
     * Returns the player's card satchel.
     * @return the player's satchel
     */
    public CardSatchelController getSatchel() {
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
