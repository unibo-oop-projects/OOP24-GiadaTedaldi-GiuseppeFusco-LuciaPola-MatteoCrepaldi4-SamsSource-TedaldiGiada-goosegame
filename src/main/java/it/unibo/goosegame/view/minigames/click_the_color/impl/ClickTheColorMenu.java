package it.unibo.goosegame.view.minigames.click_the_color.impl;

import it.unibo.goosegame.controller.minigames.click_the_color.impl.ClickTheColorImpl;
import it.unibo.goosegame.view.general.impl.MinigameMenuImpl;

public class ClickTheColorMenu extends MinigameMenuImpl {
    public ClickTheColorMenu() {
        super(
            "/backgroundCTC.png",
            "Click the color",
            "Click the Color is a fast-paced game where you must click the correct colored " +
                    "button before time runs out. Match the color shown to earn points—miss or wait too long, " +
                    "and you will lose points",
            null
        );

        init();
    }

    private void init() {
        getStartButton().addActionListener(e -> {
            new ClickTheColorImpl();
        });
    }
}
