package it.unibo.goosegame.model.minigames.three_cups_game.api;

import it.unibo.goosegame.model.general.MinigamesModel;

public interface ThreeCupsGameModel extends MinigamesModel {

    /**
     * Resets all the information needed to start a new round
     */
    void startNextRound();

    /**
     * Registers the cup choice event
     *
     * @param choice Index of the chosen cup
     * @return A value representing if the user guess was correct or not
     */
    boolean makeChoice(int choice);


    String getStatus();
}
