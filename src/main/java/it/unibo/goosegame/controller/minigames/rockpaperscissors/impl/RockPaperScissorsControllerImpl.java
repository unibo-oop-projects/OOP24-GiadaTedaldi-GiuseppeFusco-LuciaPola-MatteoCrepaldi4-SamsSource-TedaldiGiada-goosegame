package it.unibo.goosegame.controller.minigames.rockpaperscissors.impl;

import java.util.Locale;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.goosegame.controller.minigames.rockpaperscissors.api.RockPaperScissorsController;
import it.unibo.goosegame.model.minigames.rockpaperscissors.api.RockPaperScissorsModel;
import it.unibo.goosegame.view.minigames.rockpaperscissors.api.RockPaperScissorsView;
/** 
 * This class handles the interaction between the Rock-Paper-Scissors
 * game model and the view. It processes user input, updates the view 
 * based on the model's state, and manages the game flow.
*/
public class RockPaperScissorsControllerImpl implements RockPaperScissorsController {
    @SuppressFBWarnings(value = "EI2", justification = "View reference is safe in MVC context and not modified internally.")
    private final transient RockPaperScissorsView view;
    private final transient RockPaperScissorsModel model;

    private final ImageIcon rockImage;
    private final ImageIcon paperImage;
    private final ImageIcon scissorsImage;

    /**
     * @param m the game model
     * @param v the game view
     * @param menu the view of game's menu
     */
    @SuppressFBWarnings(
        value = "EI2",
        justification = "The menu is intentionally shared for interaction between view and controller."
    )
    public RockPaperScissorsControllerImpl(final RockPaperScissorsModel m, final RockPaperScissorsView v) {
        this.model = m;
        this.view = Objects.requireNonNull(v);

        view.addRockListener(e -> playTurn("ROCK"));
        view.addPaperListener(e -> playTurn("PAPER"));
        view.addScissorsListener(e -> playTurn("SCISSORS"));

        rockImage = new ImageIcon(RockPaperScissorsControllerImpl.class.getResource("/rock.png"));
        paperImage = new ImageIcon(RockPaperScissorsControllerImpl.class.getResource("/paper.png"));
        scissorsImage = new ImageIcon(RockPaperScissorsControllerImpl.class.getResource("/scissors.png"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playTurn(final String playerChoice) {
        final String computerChoice = model.playRound(playerChoice);

        switch (computerChoice) {
            case "ROCK" -> view.updateComputerChoice(rockImage);
            case "PAPER" -> view.updateComputerChoice(paperImage);
            case "SCISSORS" -> view.updateComputerChoice(scissorsImage);
            default -> throw new IllegalArgumentException("Inavlid choice: " + computerChoice);
        }

        switch (playerChoice) {
            case "ROCK" -> view.updatePlayerChoice(rockImage);
            case "PAPER" -> view.updatePlayerChoice(paperImage);
            case "SCISSORS" -> view.updatePlayerChoice(scissorsImage);
            default -> throw new IllegalArgumentException("Invalid choice: " + playerChoice);
        }

        view.updatePlayerScore(model.getPlayerScore());
        view.updateComputerScore(model.getComputerScore());
        view.updateResult("");

        final int roundResult = model.determineWinner(playerChoice, computerChoice);
        switch (roundResult) {
            case 1 -> view.updateResult("You win this round!");
            case -1 -> view.updateResult("Computer win this round!");
            default -> view.updateResult("It's a tie!");
        }

        if (model.isOver()) {
            if ("PLAYER".equals(model.getName().toUpperCase(Locale.ROOT))) {
                JOptionPane.showMessageDialog(null, "YUO WIN");
            } else {
                JOptionPane.showMessageDialog(null, "GAME OVER - YOU LOSE");
            }
            view.dispose();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void startGame() {
        model.resetGame();
        view.enableAllButtons();
    }
}
