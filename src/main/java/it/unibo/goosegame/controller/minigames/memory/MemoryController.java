package it.unibo.goosegame.controller.minigames.memory;

import it.unibo.goosegame.model.minigames.memory.api.MemoryModel;
import it.unibo.goosegame.model.minigames.memory.impl.MemoryModelImpl;
import it.unibo.goosegame.view.minigames.memory.api.MemoryView;
import it.unibo.goosegame.view.minigames.memory.impl.MemoryViewImpl;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;

/**
 * Controller for the Memory mini-game.
 * This class initializes the model and view for the game.
 */
public class MemoryController {
    private final MemoryModel model;
    private final MemoryView view;

    /**
     * Constructor for MemoryController.
     * Initializes the model and view for the Memory mini-game.
     * Sets up the game by resetting the model and displaying the view.
     */
    public MemoryController() {
        this.model = new MemoryModelImpl();
        this.view = new MemoryViewImpl(model);
        this.view.show();
    }

    /**
     * Returns the current game state.
     * 
     * @return the current game state
     */
    public GameState getGameState() {
        return this.model.getGameState();
    }
}
