package it.unibo.goosegame.application.api;

public interface GameMenuView {
    void showInstructions();
    void showMenu();
    String getPlayerName();
    void updatePlayerField();
    void updatePlayerLabel(String text);
}
