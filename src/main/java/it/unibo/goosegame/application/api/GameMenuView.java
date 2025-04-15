package it.unibo.goosegame.application;

public interface GameMenuView {
    void showInstructions();
    void showMenu();
    String getPlayerName();
    void updatePlayerField();
    void updatePlayerLabel(String text);
}
