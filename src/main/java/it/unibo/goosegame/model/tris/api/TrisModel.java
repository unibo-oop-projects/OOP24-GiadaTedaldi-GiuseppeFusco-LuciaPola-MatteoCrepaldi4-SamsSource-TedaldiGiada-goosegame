package it.unibo.goosegame.model.tris.api;

import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.utilities.Position;

public interface TrisModel extends MinigamesModel{

    enum Player {
        HUMAN, PC;
    }

    boolean makeMove(Position position);

    boolean isFull();

    boolean isHuman(Position position);

    boolean isPc(Position position);

    boolean checkWin();

}
