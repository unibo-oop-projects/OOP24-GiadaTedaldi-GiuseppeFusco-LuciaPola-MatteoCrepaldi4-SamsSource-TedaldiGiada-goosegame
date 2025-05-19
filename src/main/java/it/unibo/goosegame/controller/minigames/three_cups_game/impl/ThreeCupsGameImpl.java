package it.unibo.goosegame.controller.minigames.three_cups_game.impl;

import it.unibo.goosegame.controller.minigames.three_cups_game.api.ThreeCupsGame;
import it.unibo.goosegame.model.minigames.three_cups_game.api.ThreeCupsGameModel;
import it.unibo.goosegame.model.minigames.three_cups_game.impl.ThreeCupsGameModelImpl;
import it.unibo.goosegame.view.minigames.three_cups_game.api.ThreeCupsGameView;
import it.unibo.goosegame.view.minigames.three_cups_game.impl.ThreeCupsGameViewImpl;

public class ThreeCupsGameImpl implements ThreeCupsGame {
    private final ThreeCupsGameView view;
    private final ThreeCupsGameModel model;

    public ThreeCupsGameImpl() {
        this.model = new ThreeCupsGameModelImpl();
        this.view = new ThreeCupsGameViewImpl(model);

        this.view.show();
    }
}
