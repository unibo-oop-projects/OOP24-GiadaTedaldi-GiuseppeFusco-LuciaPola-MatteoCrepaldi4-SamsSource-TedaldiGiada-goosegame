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
        super("/img/HerdingHound_menu.png", "Herding Hound", 
        """ 
        In Herding Hound, you play as a goose
        trying to escape by moving along the border of a grid.
        At the start, there's a 3-second countdown to get ready, followed by 1 minute to complete your escape.
        Press the spacebar to move the goose one step at a time along the edge of the grid in a anticlockwise direction.
        The dog, sleeping at the center, can wake up at any moment.
        Before it does, it turns yellow for 2 seconds to warn you.
        Once awake, it looks toward the goose’s direction.
        If the dog sees you while awake — and you’re not hidden behind a box — YOU LOSE.
        Use the boxes and their shadows to stay out of sight.
        You also lose if the timer runs out before reaching the final corner.
        WIN by reaching the end without being seen and within 1 minute. 
        Good luck, and stay hidden!
        """);
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
