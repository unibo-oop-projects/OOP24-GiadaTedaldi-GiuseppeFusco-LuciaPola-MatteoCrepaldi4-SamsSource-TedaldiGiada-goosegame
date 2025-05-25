package it.unibo.goosegame.view.minigames.memory;

//import it.unibo.goosegame.controller.minigames.memory.MemoryController;
import it.unibo.goosegame.view.general.impl.MinigameMenuImpl;

/**
 * The MemoryMenu class represents the menu for the memory game.
 * It extends the MinigameMenuImpl class and provides a specific implementation for the memory game.
 */
public class MemoryMenu extends MinigameMenuImpl {
    private static final long serialVersionUID = 1L;
    /**
     * Constructor for the MemoryMenu class.
     */
    public MemoryMenu() {
        super(
            "/nomeImmagine.png", 
            "Memory", 
            "informazioni di gioco da inserire",
            null
        );
        //getStartButton().addActionListener(e -> new MemoryController());

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
