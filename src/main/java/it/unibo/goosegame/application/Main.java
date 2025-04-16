package it.unibo.goosegame.application;

import it.unibo.goosegame.controller.minigames.tris.TrisController;
import it.unibo.goosegame.model.minigames.tris.impl.TrisModelImpl;

public class Main {
    public static void main(String[] args) {
        new TrisController(new TrisModelImpl());
    }
}
