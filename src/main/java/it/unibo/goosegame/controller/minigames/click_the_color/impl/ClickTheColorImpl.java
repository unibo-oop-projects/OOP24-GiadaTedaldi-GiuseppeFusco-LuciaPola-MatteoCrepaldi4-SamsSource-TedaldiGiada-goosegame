package it.unibo.goosegame.controller.minigames.click_the_color.impl;

import it.unibo.goosegame.controller.minigames.click_the_color.api.ClickTheColor;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.model.minigames.click_the_color.api.ClickTheColorModel;
import it.unibo.goosegame.model.minigames.click_the_color.impl.ClickTheColorModelImpl;
import it.unibo.goosegame.view.minigames.click_the_color.api.ClickTheColorView;
import it.unibo.goosegame.view.minigames.click_the_color.impl.ClickTheColorViewImpl;

/**
 * Implementation of {@link ClickTheColor}.
 */
public final class ClickTheColorImpl implements ClickTheColor {
    private final ClickTheColorView view;
    private final ClickTheColorModel model;

    /**
     * Constructor for the click the color minigame.
     */
    public ClickTheColorImpl() {
        this.model = new ClickTheColorModelImpl();
        this.view = new ClickTheColorViewImpl(model);

        view.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {
        return model.getGameState();
    }


}
