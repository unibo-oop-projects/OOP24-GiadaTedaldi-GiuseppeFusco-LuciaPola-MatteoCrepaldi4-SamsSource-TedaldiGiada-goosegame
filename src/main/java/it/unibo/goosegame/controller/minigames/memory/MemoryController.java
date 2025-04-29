package it.unibo.goosegame.controller.minigames.memory;

import it.unibo.goosegame.model.minigames.memory.api.MemoryModel;
import it.unibo.goosegame.model.minigames.memory.impl.MemoryModelImpl;
import it.unibo.goosegame.view.minigames.memory.api.MemoryView;
import it.unibo.goosegame.view.minigames.memory.impl.MemoryViewImpl;

public class MemoryController {
    private MemoryModel model;
    private MemoryView view;
    
    public MemoryController() {
        this.model = new MemoryModelImpl();
        this.view = new MemoryViewImpl(model);
        this.view.show();
    }

    public static MemoryController startGame() {
        return new MemoryController();
    }
}

