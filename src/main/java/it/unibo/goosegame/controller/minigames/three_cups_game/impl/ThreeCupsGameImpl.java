package it.unibo.goosegame.controller.minigames.three_cups_game.impl;

import it.unibo.goosegame.controller.minigames.three_cups_game.api.ThreeCupsGame;
import it.unibo.goosegame.model.minigames.three_cups_game.api.ThreeCupsGameModel;
import it.unibo.goosegame.model.minigames.three_cups_game.impl.ThreeCupsGameModelImpl;
import it.unibo.goosegame.view.minigames.three_cups_game.api.ThreeCupsGameView;
import it.unibo.goosegame.view.minigames.three_cups_game.impl.ThreeCupsGameViewImpl;

/**
 * Implementation of {@link ThreeCupsGame}.
 */
public class ThreeCupsGameImpl implements ThreeCupsGame {
    /**
     * Construct for the Three Cups Game minigame.
     */
    public ThreeCupsGameImpl() {
        final ThreeCupsGameModel model = new ThreeCupsGameModelImpl();
        final ThreeCupsGameView view = new ThreeCupsGameViewImpl(model);

        view.show();
    }
}
