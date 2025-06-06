package it.unibo.goosegame.controller.minigames.three_cups_game.impl;

import it.unibo.goosegame.controller.minigames.three_cups_game.api.ThreeCupsGame;
import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.model.minigames.three_cups_game.api.ThreeCupsGameModel;
import it.unibo.goosegame.model.minigames.three_cups_game.impl.ThreeCupsGameModelImpl;
import it.unibo.goosegame.view.minigames.three_cups_game.api.ThreeCupsGameView;
import it.unibo.goosegame.view.minigames.three_cups_game.impl.ThreeCupsGameViewImpl;

/**
 * Implementation of {@link ThreeCupsGame}.
 */
public class ThreeCupsGameImpl implements ThreeCupsGame {
    private final ThreeCupsGameModel model;
    private final ThreeCupsGameView view;

    /**
     * Constructor for the Three Cups Game minigame.
     */
    public ThreeCupsGameImpl() {
        this.model = new ThreeCupsGameModelImpl();
        this.view = new ThreeCupsGameViewImpl(model);

        view.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MinigamesModel.GameState getGameState() {
        return model.getGameState();
    }
}
