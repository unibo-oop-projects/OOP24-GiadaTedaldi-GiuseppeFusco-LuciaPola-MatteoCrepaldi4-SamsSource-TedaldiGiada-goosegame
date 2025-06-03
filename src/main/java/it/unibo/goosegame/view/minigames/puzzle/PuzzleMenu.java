package it.unibo.goosegame.view.minigames.puzzle;

import it.unibo.goosegame.controller.minigames.puzzle.api.PuzzleController;
import it.unibo.goosegame.controller.minigames.puzzle.impl.PuzzleControllerImpl;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.model.minigames.puzzle.api.PuzzleModel;
import it.unibo.goosegame.model.minigames.puzzle.impl.PuzzleModelImpl;
import it.unibo.goosegame.view.general.impl.MinigameMenuImpl;
import it.unibo.goosegame.view.minigames.puzzle.api.PuzzleView;
import it.unibo.goosegame.view.minigames.puzzle.impl.PuzzleViewImpl;

/**
 * Menu for the Puzzle minigame.
 */
public class PuzzleMenu extends MinigameMenuImpl {
    private static final long serialVersionUID = 1L;
    private transient PuzzleController controller;
    private transient PuzzleModel model;
    private transient PuzzleView view;

    /**
     * Constructor for the PuzzleMenu class.
     */
    public PuzzleMenu() {
        super("/backgroundPuzzle.png",
            "Puzzle",
            """ 
            The Puzzle game consists of a 5x5 grid of tiles displaying an image, 
            which gets shuffled when you press the Start button (located at the bottom right).
            The goal is to rearrange the image within 2 minutes and 30 seconds (the time is shown at the bottom left).

            To play, select two tiles: they will be swapped.
            Keep going until the image is correctly reassembled.

            The game ends when:
            --> You successfully complete the puzzle (You Win!)
            --> Time runs out before you finish (You Lose!)

            Remember:
            --> Take time to memorize the image before it gets shuffled.
            --> Think ahead to minimize your moves.
            --> The clock is ticking — stay focused!
            """
        );
        this.initialize();
    }

    /**
     * Inizializes view and starts the Puzzle game.
     */
    private void initialize() {
       getStartButton().addActionListener(e -> {
            this.model = new PuzzleModelImpl();
            this.view = new PuzzleViewImpl();
            this.controller = new PuzzleControllerImpl(this.model, this.view);
            this.controller.startGame();
            super.dispose();
       });
    }

    @Override
    public GameState getGameState() {
        return this.controller == null ? GameState.NOT_STARTED : this.controller.getGameState();
    }
}
