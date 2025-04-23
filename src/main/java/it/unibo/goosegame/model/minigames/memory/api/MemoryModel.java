package it.unibo.goosegame.model.minigames.memory.api;

import java.util.Optional;

import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.utilities.Position;


public interface MemoryModel extends MinigamesModel {

    void hit(Position p);

    Optional<Integer> found(Position p);

    Optional<Integer> temporary(Position p);
}
