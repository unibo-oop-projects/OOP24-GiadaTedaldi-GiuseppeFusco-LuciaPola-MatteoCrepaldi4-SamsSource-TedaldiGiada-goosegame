package it.unibo.goosegame.view.minigames.rockpaperscissors;

import javax.swing.SwingUtilities;

import it.unibo.goosegame.controller.minigames.rockpaperscissors.impl.RockPaperScissorsControllerImpl;
import it.unibo.goosegame.model.minigames.rockpaperscissors.impl.RockPaperScissorsModelImpl;
import it.unibo.goosegame.view.general.MinigameMenuAbstract;
import it.unibo.goosegame.view.minigames.rockpaperscissors.impl.RockPaperScissorsViewImpl;

/**
 * The RockPaperScissorsMenu class represents the menu for the game.
 */
public class RockPaperScissorsMenu extends MinigameMenuAbstract {
    private static final long serialVersionUID = 1L;
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
            """,
            createView()
        );
    }
    /**
     * @return Th created view.
     */
    private static RockPaperScissorsViewImpl createView() {
        final RockPaperScissorsViewImpl view = new RockPaperScissorsViewImpl();
        view.initializeView();
        final RockPaperScissorsModelImpl model = new RockPaperScissorsModelImpl();
        new RockPaperScissorsControllerImpl(model, view);
        return view;
    }
    /**
     * @param args Command-line arguments.
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final RockPaperScissorsMenu menu = new RockPaperScissorsMenu();
            menu.initializeView();
        });
    }
}
