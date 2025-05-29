package it.unibo.goosegame.view.minigames.tris;

import it.unibo.goosegame.controller.minigames.tris.api.TrisController;
import it.unibo.goosegame.controller.minigames.tris.impl.TrisControllerImpl;
import it.unibo.goosegame.model.minigames.tris.api.TrisModel;
import it.unibo.goosegame.model.minigames.tris.impl.TrisModelImpl;
import it.unibo.goosegame.view.general.impl.MinigameMenuImpl;
import it.unibo.goosegame.view.minigames.tris.api.TrisView;
import it.unibo.goosegame.view.minigames.tris.impl.TrisViewImpl;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;

/**
 * Menu for the Tris(Tic-Tac-Toe) minigame.
 */
public class TrisMenu extends MinigameMenuImpl {
    private static final long serialVersionUID = 1L;
    private transient TrisController controller;
    private transient TrisModel model;
    private transient TrisView view;

    /**
     * Constructor for the TrisMenu class.
     */
    public TrisMenu() {
        super("/backgroundTris.png",
            "Tris",
            """ 
            The Trsi(Tic-Tac-Toe) game is played on a 3x3 grid.
            In this mode, you play against the computer in a best-of-three match: 
            the first to win two rounds wins the game.

            To play, click on an empty square to place your symbol (X). 
            The computer will respond with its symbol (O).
            The goal is to line up three of your symbols in a row — horizontally, vertically, or diagonally
            before your opponent does.

            A round ends when:
            --> You align three symbols (You Win!)
            --> The computer aligns three symbols (You Lose!)
            --> All squares are filled without a winner (It's a Draw)

            Remember:
            --> The computer plays strategically: it blocks your moves and looks for opportunities to win.
            --> Think ahead to anticipate the computer's moves.
            --> Every move counts — plan carefully!
            """
        );
        this.initialize();
    }

    /**
     *  Inizializes view and starts the game.
     */
    private void initialize() {
       getStartButton().addActionListener(e -> {
            this.model = new TrisModelImpl();
            this.view = new TrisViewImpl();
            this.controller = new TrisControllerImpl(this.model, this.view);
            this.controller.startGame();
            super.setVisible(false);
       });
    }

    /**
     * @return the game result.
     */
    public GameState getGameState() {
        return this.controller == null ? GameState.NOT_STARTED : this.controller.getGameState();
    }
}
