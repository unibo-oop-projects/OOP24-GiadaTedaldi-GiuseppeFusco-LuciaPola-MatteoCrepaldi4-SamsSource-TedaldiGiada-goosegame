package it.unibo.goosegame.controller.minigames.click_the_color.impl;

import it.unibo.goosegame.controller.minigames.click_the_color.api.ClickTheColor;
import it.unibo.goosegame.model.minigames.click_the_color.api.ClickTheColorModel;
import it.unibo.goosegame.model.minigames.click_the_color.impl.ClickTheColorModelImpl;
import it.unibo.goosegame.view.minigames.click_the_color.api.ClickTheColorView;
import it.unibo.goosegame.view.minigames.click_the_color.impl.ClickTheColorViewImpl;

public class ClickTheColorImpl implements ClickTheColor {
    private final ClickTheColorModel model;
    private final ClickTheColorView view;

    public ClickTheColorImpl() {
        this.model = new ClickTheColorModelImpl();
        this.view = new ClickTheColorViewImpl(model);

        this.view.show();
    }
}
