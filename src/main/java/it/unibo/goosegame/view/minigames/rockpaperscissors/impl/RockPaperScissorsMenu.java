package it.unibo.goosegame.view.minigames.rockpaperscissors.impl;

import it.unibo.goosegame.controller.minigames.rockpaperscissors.api.RockPaperScissorsController;
import it.unibo.goosegame.controller.minigames.rockpaperscissors.impl.RockPaperScissorsControllerImpl;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.model.minigames.rockpaperscissors.api.RockPaperScissorsModel;
import it.unibo.goosegame.model.minigames.rockpaperscissors.impl.RockPaperScissorsModelImpl;
import it.unibo.goosegame.view.general.impl.MinigameMenuImpl;
import it.unibo.goosegame.view.minigames.rockpaperscissors.api.RockPaperScissorsView;

/**
 * The RockPaperScissorsMenu class represents the menu for the game.
 */
public class RockPaperScissorsMenu extends MinigameMenuImpl {
    private static final long serialVersionUID = 1L;
    private transient RockPaperScissorsController controller;
    private transient RockPaperScissorsModel model;
    private transient RockPaperScissorsView view;
    /**
     * Constructor for the RockPaperScissorsMenu class.
     */
    public RockPaperScissorsMenu() {
        super("/img/minigames/background/RPS.png",
            "RockPaperScissors",
            """ 
            The game involves choosing one of the possible moves (rock, paper, or scissors) 
            and comparing it with the opponent's choice to determine the winner of the round. 
            The game is won by the first player to reach three points.

            Remember:
                --> Rock beats Scissors
                --> Paper beats Rock
                --> Scissors beats Paper
            """
        );
        this.initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {
        return this.controller == null ? GameState.NOT_STARTED : controller.getGameState();
    }

    /**
     *  Inizialize view.
     */
    private void initialize() {
        this.model = new RockPaperScissorsModelImpl();
        this.getStartButton().addActionListener(e -> {
            this.view = new RockPaperScissorsViewImpl();
            this.controller = new RockPaperScissorsControllerImpl(this.model, this.view);
            this.view.initializeView();
            this.controller.startGame();
            super.setVisible(false);
      });
    }
}
