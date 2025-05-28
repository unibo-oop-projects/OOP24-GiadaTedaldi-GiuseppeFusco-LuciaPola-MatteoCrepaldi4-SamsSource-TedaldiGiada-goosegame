package it.unibo.goosegame.controller.minigames.click_the_color.impl;

import it.unibo.goosegame.controller.minigames.click_the_color.api.ClickTheColor;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.model.minigames.click_the_color.api.ClickTheColorModel;
import it.unibo.goosegame.model.minigames.click_the_color.impl.ClickTheColorModelImpl;
import it.unibo.goosegame.view.minigames.click_the_color.api.ClickTheColorView;
import it.unibo.goosegame.view.minigames.click_the_color.impl.ClickTheColorViewImpl;

import javax.swing.*;

/**
 * Implementation of {@link ClickTheColor}.
 */
public class ClickTheColorImpl implements ClickTheColor {
    private final ClickTheColorView view;
    private final ClickTheColorModel model;

    /**
     * Constructor method for the Click The Color controller class.
     */
    public ClickTheColorImpl() {
        this.model = new ClickTheColorModelImpl();
        this.view = new ClickTheColorViewImpl(model);

        view.show();
    }

    public GameState getResult() {
        return model.getGameState();
    }
}
