package it.unibo.goosegame.model.minigames.click_the_color.api;

import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.utilities.Pair;

import java.util.Map;

public interface ClickTheColorModel extends MinigamesModel {
    /**
     * Updates the game logic
     *
     * @return the index of the button to activate, -1 if no button is active
     */
    public int update();

    public void clicked(int index);

    public int getScore();
}
