package it.unibo.goosegame.model.minigames.RockPaperScissors;

import java.util.Random;

import it.unibo.goosegame.model.general.MinigamesModel;

public class RockPaperScissorsModel implements MinigamesModel {
    private int playerScore = 0;
    private int computerScore = 0;
    private boolean over = false;
    private final String[] choices = {"Rock", "Paper", "Scissors"};
    private String winner = "";
    private final Random random = new Random();

    public String getRandomComputerChoice() {
        return choices[random.nextInt(3)];
    }

    public String playRound(String playerChoice) {
        String computerChoice = getRandomComputerChoice();
        int result = determineWinner(playerChoice, computerChoice);

        switch (result) {
            case 1 -> {
                playerScore++;
            }
            case -1 -> {
                computerScore++;
            }
        }

        if(playerScore == 3 || computerScore == 3) {
            over = true;
            winner = (playerScore == 3) ? "PLAYER" : "COMPUTER";
        }
        return computerChoice;
    }

    
    public int determineWinner(String player, String computer) {
        if(player.equals(computer)) {
            return 0;
        }
        if((player.equals("Rock") && computer.equals("Scissors")) ||
            (player.equals("Scissors") && computer.equals("Paper")) ||
            (player.equals("Paper") && computer.equals("Rock"))) {
                return 1;
        }
        return -1;
    }

    @Override
    public void resetGame() {
        playerScore = 0;
        computerScore = 0;
        over = false;
        winner = "";
    }

    @Override
    public int getResult() {
       return (winner.equals("PLAYER")) ? playerScore : computerScore;
    }

    @Override
    public String getName() {
        return winner;
    }

    @Override
    public boolean isOver() {
        return over;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getComputerScore() {
        return computerScore;
    }
}
