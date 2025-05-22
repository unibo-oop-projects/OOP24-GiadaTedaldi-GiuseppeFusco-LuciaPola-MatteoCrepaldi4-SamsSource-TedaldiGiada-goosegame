package it.unibo.goosegame.view.minigames.click_the_color.api;

import java.awt.Color;

public interface ClickTheColorView {
    Color[] off_colors = { Color.decode("#9f6060"), Color.decode("#9f9f60"), Color.decode("#609f60"), Color.decode("#60609f") };
    Color[] on_colors = { Color.decode("#ff0000"), Color.decode("#ffff00"), Color.decode("#00ff00"), Color.decode("#0000ff")};
    /**
     * Sets the game's view to visible
     */
    void show();
}
