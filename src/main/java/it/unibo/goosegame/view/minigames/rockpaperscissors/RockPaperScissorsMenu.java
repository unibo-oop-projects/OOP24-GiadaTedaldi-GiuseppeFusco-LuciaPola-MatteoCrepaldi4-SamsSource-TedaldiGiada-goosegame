package it.unibo.goosegame.view.minigames.rockpaperscissors;

import javax.swing.SwingUtilities;

import it.unibo.goosegame.controller.minigames.rockpaperscissors.api.RockPaperScissorsController;
import it.unibo.goosegame.controller.minigames.rockpaperscissors.impl.RockPaperScissorsControllerImpl;
import it.unibo.goosegame.model.minigames.rockpaperscissors.api.RockPaperScissorsModel;
import it.unibo.goosegame.model.minigames.rockpaperscissors.impl.RockPaperScissorsModelImpl;
import it.unibo.goosegame.view.general.impl.MinigameMenuImpl;
import it.unibo.goosegame.view.minigames.rockpaperscissors.api.RockPaperScissorsView;
import it.unibo.goosegame.view.minigames.rockpaperscissors.impl.RockPaperScissorsViewImpl;

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
        super("/RPS.png",
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
        initialize();
    }

    /**
     *  Inizialize view.
     */
    private void initialize() {
      model = new RockPaperScissorsModelImpl();
      getStartButton().addActionListener(e -> {
            view = new RockPaperScissorsViewImpl();
            controller = new RockPaperScissorsControllerImpl(model, view, this);
            view.initializeView();
            controller.startGame();
            dispose();
      });
    }
     public static void main(final String[] args) {
    SwingUtilities.invokeLater(() -> {
      final RockPaperScissorsMenu menu = new RockPaperScissorsMenu();
      menu.initializeView();
    });
  }
}
