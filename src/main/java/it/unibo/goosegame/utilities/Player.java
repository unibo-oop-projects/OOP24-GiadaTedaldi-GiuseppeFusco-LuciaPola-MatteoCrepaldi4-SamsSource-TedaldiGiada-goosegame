package it.unibo.goosegame.utilities;

import it.unibo.goosegame.model.CardSatchelModel;

public class Player {
    private final String name;
    private final CardSatchelModel satchel;

    public Player(String name) {
        this.name = name;
        this.satchel = new CardSatchelModel();
    }

    public String getName() {
        return name;
    }

    public CardSatchelModel getSatchel() {
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
