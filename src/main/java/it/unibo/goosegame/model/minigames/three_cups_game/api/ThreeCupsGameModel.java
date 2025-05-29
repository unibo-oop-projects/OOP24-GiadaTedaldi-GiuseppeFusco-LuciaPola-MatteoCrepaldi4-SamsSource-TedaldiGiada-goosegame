package it.unibo.goosegame.model.minigames.three_cups_game.api;

import it.unibo.goosegame.model.general.MinigamesModel;

import java.util.Optional;

/**
 * Model class for the Three Cups Game minigame.
 */
public interface ThreeCupsGameModel extends MinigamesModel {
    /**
     * Method used by the view to update the model.
     *
     * @return the index of the right button, -1 if the round is still in progress
     */
    int update();

    /**
     * Method used by the view to signal the model a cup has been clicked.
     *
     * @param index the index of the clicked cup
     */
    void clicked(int index);

    /**
     * Getter method for the number of rounds already played.
     *
     * @return the number of rounds already played
     */
    int getRoundNumber();

    /**
     * Getter method for the number of won rounds.
     *
     * @return the number of won rounds
     */
    int getVictories();

    /**
     * Getter method for the right cup to click.
     *
     * @return returns wehter the last choice was right or not. Empty if the round is still ongoing.
     */
    Optional<Boolean> getLastChoice();

    /**
     * Getter for the number of rounds.
     * @return the number of rounds
     */
    int getMaxRounds();

    /**
     * Getter for the wait status of the game.
     * @return true if the game is waiting for the next round, otherwise false
     */
    boolean isWaiting();
}
