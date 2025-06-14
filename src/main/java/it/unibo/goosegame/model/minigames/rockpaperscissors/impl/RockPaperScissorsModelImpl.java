package it.unibo.goosegame.model.minigames.rockpaperscissors.impl;

import java.util.Random;

import it.unibo.goosegame.model.minigames.rockpaperscissors.api.RockPaperScissorsModel;

/**
 * The RockPaperScissorsModel class manages the logic of the Rock-Paper-Scissors game.
 * It keeps track of the player and computer scores, determines the winner of each round,
 * checks if the game is over, and allows resetting the state for a new game.
 */
public class RockPaperScissorsModelImpl implements RockPaperScissorsModel {
    private static final int MAX_WIN = 3;

    private int playerScore;
    private int computerScore;
    private boolean over;
    private String winner = "";

    private final Random random = new Random();
    private final String[] choices = {"ROCK", "PAPER", "SCISSORS"};

    /**
    * {@inheritDoc}
    */
    @Override
    public String getRandomComputerChoice() {
        return choices[random.nextInt(3)];
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public String playRound(final String playerChoice) {
        final String computerChoice = getRandomComputerChoice();
        final int result = determineWinner(playerChoice, computerChoice);
        if (result == 1) {
            this.playerScore++;
        }
        if (result == -1) {
            this.computerScore++;
        }
        if (playerScore == MAX_WIN || computerScore == MAX_WIN) {
            this.over = true;
            this.winner = (playerScore == MAX_WIN) ? "PLAYER" : "COMPUTER";
        }
        return computerChoice;
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public int determineWinner(final String player, final String computer) {
        if (player.equals(computer)) {
            return 0;
        }
        if (("ROCK".equals(player) && "SCISSORS".equals(computer)) 
            || ("SCISSORS".equals(player) && "PAPER".equals(computer)) 
            || ("PAPER".equals(player) && "ROCK".equals(computer))) {
                return 1;
        }
        return -1;
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public void resetGame() {
        this.playerScore = 0;
        this.computerScore = 0;
        this.over = false;
        this.winner = "";
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public GameState getGameState() {
        if (!over) {
            throw new IllegalStateException("Game is not over yet.");
        }
        return "PLAYER".equals(this.winner) ? GameState.WON : GameState.LOST;
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public String getName() {
        return this.winner;
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public boolean isOver() {
        return this.over;
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public int getPlayerScore() {
        return this.playerScore;
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public int getComputerScore() {
        return this.computerScore;
    }
}
