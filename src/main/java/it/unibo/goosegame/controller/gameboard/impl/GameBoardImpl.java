package it.unibo.goosegame.controller.gameboard.impl;

import it.unibo.goosegame.controller.cardsatchel.CardSatchelController;
import it.unibo.goosegame.controller.cell.api.Cell;
import it.unibo.goosegame.controller.cell.impl.CellImpl;
import it.unibo.goosegame.controller.gameboard.api.GameBoard;
import it.unibo.goosegame.model.gameboard.api.GameBoardModel;
import it.unibo.goosegame.model.gameboard.impl.GameBoardModelImpl;
import it.unibo.goosegame.model.player.api.Player;
import it.unibo.goosegame.model.turnmanager.api.TurnManager;
import it.unibo.goosegame.model.turnmanager.impl.TurnManagerImpl;
import it.unibo.goosegame.view.gameboard.api.GameBoardView;
import it.unibo.goosegame.view.gameboard.impl.GameBoardViewImpl;
import it.unibo.goosegame.view.general.api.MinigameMenu;
import it.unibo.goosegame.view.minigames.click_the_color.impl.ClickTheColorMenu;
import it.unibo.goosegame.view.minigames.hangman.HangmanMenu;
import it.unibo.goosegame.view.minigames.herdinghound.HerdingHoundMenu;
import it.unibo.goosegame.view.minigames.honkmand.HonkMandMenu;
import it.unibo.goosegame.view.minigames.memory.MemoryMenu;
import it.unibo.goosegame.view.minigames.puzzle.PuzzleMenu;
import it.unibo.goosegame.view.minigames.rockpaperscissors.RockPaperScissorsMenu;
import it.unibo.goosegame.view.minigames.snake.SnakeMenu;
import it.unibo.goosegame.view.minigames.three_cups_game.impl.ThreeCupsGameMenu;
import it.unibo.goosegame.view.minigames.tris.TrisMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Classed use to represent the gameboard.
 */
public class GameBoardImpl implements GameBoard {
    private static final int CELLS_NUM = 60;

    private final GameBoardModel model;
    private final List<Cell> gameCells;
    private final List<Player> players;
    private final TurnManager turnManager;

    /**
     * GameBoard constructor method.
     * @param players the list of the registered players
     */
    public GameBoardImpl(final List<Player> players) {
        this.gameCells = new ArrayList<>();
        this.players = players;
        this.turnManager = new TurnManagerImpl(players);

        initGameCells();

        for(Player p : players) {
            p.setSatchel(new CardSatchelController(this)); // Initialize player positions to the first cell
        }

        this.model = new GameBoardModelImpl(turnManager, gameCells);
        GameBoardView view = new GameBoardViewImpl(model, gameCells);

        view.show();
    }

    /**
     * Method used to initialise the game cells.
     * It creates a list of {@link Cell} objects and adds the players to the first cell.
     */
    private void initGameCells() {
        for (int i = 0; i < CELLS_NUM; i++) {
            if (i % 6 == 0 && i != 0) {
                gameCells.add(new CellImpl(getMinigame(i)));
            } else {
               gameCells.add(new CellImpl());
            }
        }

        for (final Player player : players) {
            gameCells.getFirst().addPlayer(player);
        }
    }

    MinigameMenu getMinigame(int index) {
        return switch (index / 6) {
            case 1 -> new MemoryMenu();
            case 2 -> new PuzzleMenu();
            case 3 -> new HonkMandMenu();
            case 4 -> new HangmanMenu();
            case 5 -> new ClickTheColorMenu();
            case 6 -> new HerdingHoundMenu();
            case 7 -> new TrisMenu();
            case 8 -> new RockPaperScissorsMenu();
            case 9 -> new ThreeCupsGameMenu();
            case 10 -> new SnakeMenu();
            default -> null;
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(final int steps, final boolean isForward) {
        model.move(turnManager.getCurrentPlayer(), steps, isForward);
    }
}
