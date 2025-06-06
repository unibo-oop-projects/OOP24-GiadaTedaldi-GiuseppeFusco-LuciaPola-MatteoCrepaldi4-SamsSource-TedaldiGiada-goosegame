package it.unibo.goosegame.controller.minigames.rockpaperscissors.impl;

import java.util.Locale;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.goosegame.controller.minigames.rockpaperscissors.api.RockPaperScissorsController;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
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
     */
    @SuppressFBWarnings(
        value = "EI2",
        justification = "The menu is intentionally shared for interaction between view and controller."
    )
    public RockPaperScissorsControllerImpl(final RockPaperScissorsModel m, final RockPaperScissorsView v) {
        this.model = m;
        this.view = Objects.requireNonNull(v);

        this.view.addRockListener(e -> playTurn("ROCK"));
        this.view.addPaperListener(e -> playTurn("PAPER"));
        this.view.addScissorsListener(e -> playTurn("SCISSORS"));

        this.rockImage = new ImageIcon(RockPaperScissorsControllerImpl.class.getResource("/img/minigames/rpc/rock.png"));
        this.paperImage = new ImageIcon(RockPaperScissorsControllerImpl.class.getResource("/img/minigames/rpc/paper.png"));
        this.scissorsImage = new ImageIcon(RockPaperScissorsControllerImpl.class.getResource("/img/minigames/rpc/scissors.png"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playTurn(final String playerChoice) {
        final String computerChoice = this.model.playRound(playerChoice);

        switch (computerChoice) {
            case "ROCK" -> this.view.updateComputerChoice(this.rockImage);
            case "PAPER" -> this.view.updateComputerChoice(this.paperImage);
            case "SCISSORS" -> this.view.updateComputerChoice(this.scissorsImage);
            default -> throw new IllegalArgumentException("Inavlid choice: " + computerChoice);
        }

        switch (playerChoice) {
            case "ROCK" -> this.view.updatePlayerChoice(this.rockImage);
            case "PAPER" -> this.view.updatePlayerChoice(this.paperImage);
            case "SCISSORS" -> this.view.updatePlayerChoice(this.scissorsImage);
            default -> throw new IllegalArgumentException("Invalid choice: " + playerChoice);
        }

        this.view.updatePlayerScore(this.model.getPlayerScore());
        this.view.updateComputerScore(this.model.getComputerScore());
        this.view.updateResult("");

        final int roundResult = this.model.determineWinner(playerChoice, computerChoice);
        switch (roundResult) {
            case 1 -> this.view.updateResult("You win this round!");
            case -1 -> this.view.updateResult("Computer win this round!");
            default -> this.view.updateResult("It's a tie!");
        }

        if (model.isOver()) {
            if ("PLAYER".equals(this.model.getName().toUpperCase(Locale.ROOT))) {
                JOptionPane.showMessageDialog(null, "YUO WIN");
            } else {
                JOptionPane.showMessageDialog(null, "GAME OVER - YOU LOSE");
            }
            this.view.dispose();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void startGame() {
        this.model.resetGame();
        this.view.enableAllButtons();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {
        if (!this.model.isOver()) {
            return GameState.ONGOING;
        }
        return this.model.getGameState();
    }
}
