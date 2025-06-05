package it.unibo.goosegame.view.minigames.click_the_color.impl;

import it.unibo.goosegame.controller.minigames.click_the_color.impl.ClickTheColorImpl;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.view.general.impl.MinigameMenuImpl;

import java.io.Serial;

/**
 * Extension of {@link MinigameMenuImpl} for the click the color minigame.
 */
public class ClickTheColorMenu extends MinigameMenuImpl {
    @Serial
    private static final long serialVersionUID = 1L;
    private transient ClickTheColorImpl controller;

    /**
     * Constructor for the click the color menu.
     */
    public ClickTheColorMenu() {
        super(
            "/img/minigames/background/backgroundCTC.png",
            "Click the color",
            "Click the Color is a fast-paced game where you must click the correct colored "
                    + "button before time runs out. Match the color shown to earn points—miss or wait too long, "
                    + "and you will lose points"
        );

        init();
    }

    /**
     * Utility function used to initialise the menu.
     */
    private void init() {
        getStartButton().addActionListener(e -> {
            this.controller = new ClickTheColorImpl(this);
            this.setVisible(false);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {
        return controller == null ? GameState.NOT_STARTED : controller.getGameState();
    }
}
