package it.unibo.goosegame.controller.minigames.click_the_color.impl;

import it.unibo.goosegame.controller.minigames.click_the_color.api.ClickTheColor;
import it.unibo.goosegame.model.minigames.click_the_color.api.ClickTheColorModel;
import it.unibo.goosegame.model.minigames.click_the_color.impl.ClickTheColorModelImpl;
import it.unibo.goosegame.view.minigames.click_the_color.api.ClickTheColorView;
import it.unibo.goosegame.view.minigames.click_the_color.impl.ClickTheColorViewImpl;

/**
 * Implementation of {@link ClickTheColor}.
 */
public class ClickTheColorImpl implements ClickTheColor {
    /**
     * Constructor method for the Click The Color controller class.
     */
    public ClickTheColorImpl() {
        final ClickTheColorModel model = new ClickTheColorModelImpl();
        final ClickTheColorView view = new ClickTheColorViewImpl(model);

        view.show();
    }
}
