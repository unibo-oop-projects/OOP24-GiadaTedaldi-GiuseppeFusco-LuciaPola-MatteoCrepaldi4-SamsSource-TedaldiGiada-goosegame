package it.unibo.goosegame.view.gameboard.api;

/**
 * Class storing all the view information for {@link it.unibo.goosegame.controller.gameboard.api.GameBoard}.
 */
public interface GameBoardView {

    /**
     * Used to make the frame of the application visible.
     */
    void show();

    /**
     * Used to signal the view to dispose the graphical interface.
     */
    void disposeFrame();
}
