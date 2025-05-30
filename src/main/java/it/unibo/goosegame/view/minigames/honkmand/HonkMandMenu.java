package it.unibo.goosegame.view.minigames.honkmand;

import it.unibo.goosegame.controller.minigames.honkmand.HonkMandController;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.view.general.impl.MinigameMenuImpl;

/**
 * This class represents the menu for the HonkMand game.
 */
public class HonkMandMenu extends MinigameMenuImpl {

    private static final long serialVersionUID = 1L;
    private transient HonkMandController controller;

    /**
     * Constructor for the HonkMandMenu class.
     */
    public HonkMandMenu() {
        super("/img/HonkMand_menu.png", "HonkMand", "");
        initialize();
    }

    private void initialize() {
        getStartButton().addActionListener(e -> {
            this.controller = new HonkMandController();
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
    final HonkMandMenu menu = new HonkMandMenu();
    menu.initializeView();
    }); */
