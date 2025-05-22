package it.unibo.goosegame.view.minigames.memory;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

import it.unibo.goosegame.view.general.impl.MinigameMenuAbstract;

/**
 * The MemoryMenu class represents the menu for the memory game.
 * It extends the MinigameMenuAbstract class and provides a specific implementation for the memory game.
 */
public class MemoryMenu extends MinigameMenuAbstract {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param imgPath the path of the image background
     * @param title the title of the frame
     * @param infoMsg the message to show in the instructions
     * @param gamePanel the panel representing the game.
     * @param al the action listener for the start button
     */
    public MemoryMenu(final String imgPath, final String title, final String infoMsg, final JPanel gamePanel, final ActionListener al) {
        super(imgPath, title, infoMsg, gamePanel, al);
        /*
         --Hypothetical Memory menu:
        ActionListener al = e -> {
            MemoryController.startGame();
        };
        new MemoryMenu("resources/memoryBackground.png", "Memory Game", "Welcome to Memory Game\n" + 
                        Here's some instruction to play:\n" + //
                        "-Find the couples beyond the cards\n" + //
                        "-find them all and you win\n", al);
         */
    }

}
