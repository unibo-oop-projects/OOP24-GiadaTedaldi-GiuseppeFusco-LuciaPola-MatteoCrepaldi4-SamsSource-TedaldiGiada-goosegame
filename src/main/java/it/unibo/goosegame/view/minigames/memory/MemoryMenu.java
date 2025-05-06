package it.unibo.goosegame.view.minigames.memory;

import java.awt.event.ActionListener;

import it.unibo.goosegame.view.general.MinigameMenuAbstract;

public class MemoryMenu extends MinigameMenuAbstract {

    public MemoryMenu(final String imgPath, final String title, final String infoMsg, final ActionListener al) {
        super(imgPath, title, infoMsg, al);
        /*
         --Hypothetical Memory menu:
        ActionListener al = e -> {
            MemoryController.startGame();
        };
        new MemoryMenu("resources/memoryBackground.png", "Memory Game", "Welcome to Memory Game! Here's some instruction to play:\n" + //
                        "-Find the couples beyond the cards\n" + //
                        "-find them all and you win\n", al);
         */
    }

}
