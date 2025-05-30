package it.unibo.goosegame.view.minigames.herdinghound;

import it.unibo.goosegame.controller.minigames.herdinghound.HerdingHoundController;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.view.general.impl.MinigameMenuImpl;

/**
 * This class represents the menu for the Herding Hound minigame.
 */
public class HerdingHoundMenu extends MinigameMenuImpl {
    private static final long serialVersionUID = 1L;
    private transient HerdingHoundController controller;

    /**
     * Constructor for the HerdingHound class.
     */
    public HerdingHoundMenu() {
        super("/img/HerdingHound_menu.png", "Herding Hound", "");
        initialize();
    }

    private void initialize() {
        getStartButton().addActionListener(e -> {
            this.controller = new HerdingHoundController();
            controller.startGame();
            super.setVisible(false);
        });
    }

    /**
     * Returns the state of the game.
     * @return GameState
     */
    public GameState getGameState() {
        return controller == null ? GameState.NOT_STARTED : controller.getGameState();
    }
}

    /*
    public static void main(final String[] args) {
    SwingUtilities.invokeLater(() -> {
    final HerdingHoundMenu menu = new HerdingHoundMenu();
    menu.initializeView();
    }); */
