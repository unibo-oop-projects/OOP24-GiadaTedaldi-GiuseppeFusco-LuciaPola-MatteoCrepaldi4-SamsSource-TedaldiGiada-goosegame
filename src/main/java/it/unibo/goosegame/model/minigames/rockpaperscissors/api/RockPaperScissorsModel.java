package it.unibo.goosegame.model.minigames.rockpaperscissors.api;
import it.unibo.goosegame.model.general.MinigamesModel;
/**
 * Interface for the RockPaperScissors game model.
 */
public interface RockPaperScissorsModel extends MinigamesModel {
    /**
     * @return computer's choice.
     */
    String getRandomComputerChoice();
    /**
     * @param playerChoice the player's choice (Rock, Paper, Scissors).
     * @return the computer's choice.
     */
    String playRound(String playerChoice);
    /**
     * @param player the player's choice.
     * @param computer the computer's choice.
     * @return 1 if the player win, -1 if the computer win, 0 in case of a draw.
     */
    int determineWinner(String player, String computer);
    /**
     * @return the player's score.
     */
    int getPlayerScore();
    /**
     * @return the computer's score.
     */
    int getComputerScore();
}
