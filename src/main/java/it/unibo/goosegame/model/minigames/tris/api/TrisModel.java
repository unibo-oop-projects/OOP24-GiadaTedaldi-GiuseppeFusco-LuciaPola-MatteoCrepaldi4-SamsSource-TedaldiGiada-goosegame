package it.unibo.goosegame.model.minigames.tris.api;

import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.utilities.Position;

public interface TrisModel extends MinigamesModel{

    enum Player {
        HUMAN, PC;
    }

    void makePcMove();

    boolean makeHumanMove(Position position);

    boolean isFull();

    boolean isHuman(Position position);

    boolean isPc(Position position);

    String getStatus();

    boolean checkWin();

}
