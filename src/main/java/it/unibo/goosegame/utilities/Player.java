package it.unibo.goosegame.utilities;

import it.unibo.goosegame.model.cardsatchel.impl.CardSatchelModelImpl;

public class Player {
    private final String name;
    private final CardSatchelModelImpl satchel;

    public Player(String name) {
        this.name = name;
        this.satchel = new CardSatchelModelImpl();
    }

    public String getName() {
        return name;
    }

    public CardSatchelModelImpl getSatchel() {
        return satchel;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
