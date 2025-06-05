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
import it.unibo.goosegame.view.finalboard.FinalBoardGui;
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

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classed use to represent the gameboard.
 */
public class GameBoardImpl implements GameBoard {
    private static final int CASE_9 = 9;
    private static final int CASE_7 = 7;
    private static final int CASE_6 = 6;
    private static final int CASE_5 = 5;
    private static final int GAME_CELL_NUM = 6;
    private static final int CELLS_NUM = 60;

    private final GameBoardModel model;
    private final GameBoardView view;
    private final List<Cell> gameCells;
    private final List<Player> players;
    private final TurnManager turnManager;
    private final Timer gameTimer;

    /**
     * GameBoard constructor method.
     * @param players the list of the registered players
     */
    public GameBoardImpl(final List<Player> players) {
        this.gameCells = new ArrayList<>();
        this.players = List.copyOf(players);
        this.turnManager = new TurnManagerImpl(players);

        initGameCells();

        for (final Player p : players) {
            p.setSatchel(new CardSatchelController(this)); // Initialize player positions to the first cell
        }

        this.model = new GameBoardModelImpl(turnManager, gameCells);
        this.view = new GameBoardViewImpl(model, gameCells);

        this.gameTimer = new Timer(100, e -> {
            if (model.isOver()) {
                showFinalBoard();
            }
        });

        gameTimer.start();
        view.show();
    }

    /**
     * Method used to show the final board and end the game
     */
    private void showFinalBoard() {
        gameTimer.stop();
        view.disposeFrame();
        new FinalBoardGui(this);
    }

    /**
     * Method used to initialise the game cells.
     * It creates a list of {@link Cell} objects and adds the players to the first cell.
     */
    private void initGameCells() {
        for (int i = 0; i < CELLS_NUM; i++) {
            if (i % GAME_CELL_NUM == 0 && i != 0) {
                gameCells.add(new CellImpl(getMinigame(i)));
            } else {
               gameCells.add(new CellImpl());
            }
        }

        for (final Player player : players) {
            gameCells.getFirst().addPlayer(player);
        }
    }

    /**
     * Utility function to get the minigame based on the index.
     *
     * @param index the index of the cell
     * @return the corresponding minigame menu
     */
    private MinigameMenu getMinigame(final int index) {
        return switch (index / GAME_CELL_NUM) {
            case 1 -> new MemoryMenu();
            case 2 -> new PuzzleMenu();
            case 3 -> new HonkMandMenu();
            case 4 -> new HangmanMenu();
            case CASE_5 -> new ClickTheColorMenu();
            case CASE_6 -> new HerdingHoundMenu();
            case CASE_7 -> new TrisMenu();
            case 8 -> new RockPaperScissorsMenu();
            case CASE_9 -> new ThreeCupsGameMenu();
            case 10 -> new SnakeMenu();
            default -> null;
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(final int steps, final boolean isForward) {
        model.move(turnManager.getCurrentPlayer(), steps, isForward);
    }
}
