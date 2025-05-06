package it.unibo.goosegame.controller.minigames.memory;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.goosegame.model.minigames.memory.api.MemoryModel;
import it.unibo.goosegame.model.minigames.memory.impl.MemoryModelImpl;
import it.unibo.goosegame.view.minigames.memory.api.MemoryView;
import it.unibo.goosegame.view.minigames.memory.impl.MemoryViewImpl;

/**
 * Controller for the Memory mini-game.
 * This class initializes the model and view for the game.
 */
public class MemoryController {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
    private final MemoryModel model;
    private final MemoryView view;

    /**
     * Constructor for MemoryController.
     * Initializes the model and view for the Memory mini-game.
     * Sets up the game by resetting the model and displaying the view.
     */
    public MemoryController() {
        this.model = new MemoryModelImpl();
        this.model.resetGame();
        this.view = new MemoryViewImpl(model);
        this.view.show();
    }

    /**
     * Starts the Memory game.
     * This method creates a new instance of MemoryController,
     * @return a new MemoryController instance.
     */
    @SuppressFBWarnings("ISC")
    public static MemoryController startGame() {
        return new MemoryController();
    }
}

