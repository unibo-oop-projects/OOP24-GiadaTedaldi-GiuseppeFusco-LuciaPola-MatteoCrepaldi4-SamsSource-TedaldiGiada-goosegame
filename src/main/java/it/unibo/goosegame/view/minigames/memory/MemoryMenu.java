package it.unibo.goosegame.view.minigames.memory;

import it.unibo.goosegame.controller.minigames.memory.MemoryController;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
//import it.unibo.goosegame.controller.minigames.memory.MemoryController;
import it.unibo.goosegame.view.general.impl.MinigameMenuImpl;

/**
 * The MemoryMenu class represents the menu for the memory game.
 * It extends the MinigameMenuImpl class and provides a specific implementation for the memory game.
 */
public class MemoryMenu extends MinigameMenuImpl {

    private static final long serialVersionUID = 1L;
    private transient MemoryController controller;
    /**
     * Constructor for the MemoryMenu class.
     */
    public MemoryMenu() {
        super(
            "/backgroundMemory.png", 
            "Memory Game", 
            "Welcome to Memory Game\n"
            + "Here's some instruction to play:\n"
            + "-Find the couples beyond the cards\n"
            + "-Find them all and you win\n"
        );
        initialize();
    }

    private void initialize() {
        getStartButton().addActionListener(e -> {
            this.controller = new MemoryController();
            super.setVisible(false);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {
        return controller == null ? GameState.NOT_STARTED : controller.getGameState();
    }
}
