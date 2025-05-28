package it.unibo.goosegame.view.minigames.three_cups_game.impl;

import it.unibo.goosegame.controller.minigames.three_cups_game.impl.ThreeCupsGameImpl;
import it.unibo.goosegame.view.general.impl.MinigameMenuImpl;

public class ThreeCupsGameMenu extends MinigameMenuImpl{
    public ThreeCupsGameMenu() {
        super(
            "/backgroundTCG.png",
            "Three Cups Game",
            "Three Cups Game is a fun and simple minigame where three cups are " +
                    "placed on a table. One of them hides a reward underneath. Your goal is " +
                    "to guess which cup it’s under—choose correctly to earn points!",
            null
        );

        init();
    }

    private void init() {
        getStartButton().addActionListener(e -> {
           new ThreeCupsGameImpl();
        });
    }
}
