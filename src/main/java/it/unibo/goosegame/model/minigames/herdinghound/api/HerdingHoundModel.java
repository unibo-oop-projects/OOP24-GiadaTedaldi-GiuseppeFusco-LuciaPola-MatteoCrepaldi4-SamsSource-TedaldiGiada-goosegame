package it.unibo.goosegame.model.minigames.herdinghound.api;

import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.utilities.Position;
import java.util.List;

/**
 * API interface for the Herding Hound minigame model.
 */
public interface HerdingHoundModel extends MinigamesModel {

    /**
     * Returns the goose instance.
     * @return the goose instance
     */
    Goose getGoose();

    /**
     * Returns the dog instance.
     * @return the dog instance
     */
    Dog getDog();

    /**
     * Returns the box instance.
     * @return the box instance
     */
    Box getBox();

    /**
     * Returns the grid size.
     * @return the grid size
     */
    int getGrid();

    /**
     * Returns the list of shadow positions.
     * @return list of shadow positions
     */
    List<Position> getShadows();

    /**
     * Performs the next automatic move of the goose.
     */
    void nextGooseMove();

    /**
     * Starts the game and resets the timer.
     */
    void startGame();

    /**
     * Returns the remaining time for the game.
     * @return the remaining time in milliseconds
     */
    long getRemainingTime();

    /**
     * Returns the current game state.
     * @return the current game state
     */
    @Override
    GameState getGameState();

    /**
     * Returns the cells visible by the dog, excluding shadows and boxes.
     * @return list of visible positions
     */
    List<Position> getVisible();

    /**
     * Advances the dog's state to the next one (ASLEEP->ALERT->AWAKE->ASLEEP).
     */
    void nextDogState();

    /**
     * Returns the list of box positions.
     * @return list of box positions
     */
    List<Position> getBoxes();
}
