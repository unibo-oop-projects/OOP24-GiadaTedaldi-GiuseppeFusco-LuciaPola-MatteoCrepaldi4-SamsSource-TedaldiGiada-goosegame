package it.unibo.goosegame.model.minigames.three_cups_game.api;

import it.unibo.goosegame.model.general.MinigamesModel;

/**
 * Model class for the Three Cups Game minigame.
 */
public interface ThreeCupsGameModel extends MinigamesModel {

    /**
     * Registers the cup choice event.
     *
     * @param choice Index of the chosen cup
     * @return A value representing if the user guess was correct or not
     */
    boolean makeChoice(int choice);

    /**
     * Gets updated information about the current game.
     *
     * @return a string containing the current game information
     */
    String getStatus();
}
