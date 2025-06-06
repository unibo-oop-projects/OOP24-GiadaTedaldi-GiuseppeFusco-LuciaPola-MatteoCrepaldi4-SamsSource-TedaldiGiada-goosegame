package it.unibo.goosegame.controller.cell.impl;

import it.unibo.goosegame.controller.cell.api.Cell;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.model.player.api.Player;
import it.unibo.goosegame.view.cell.api.CellView;
import it.unibo.goosegame.view.cell.impl.CellViewImpl;
import it.unibo.goosegame.view.general.api.MinigameMenu;
import it.unibo.goosegame.view.minigames.click_the_color.impl.ClickTheColorMenu;
import it.unibo.goosegame.view.minigames.hangman.impl.HangmanMenu;
import it.unibo.goosegame.view.minigames.herdinghound.impl.HerdingHoundMenu;
import it.unibo.goosegame.view.minigames.honkmand.impl.HonkMandMenu;
import it.unibo.goosegame.view.minigames.memory.impl.MemoryMenu;
import it.unibo.goosegame.view.minigames.puzzle.impl.PuzzleMenu;
import it.unibo.goosegame.view.minigames.rockpaperscissors.impl.RockPaperScissorsMenu;
import it.unibo.goosegame.view.minigames.snake.SnakeMenu;
import it.unibo.goosegame.view.minigames.three_cups_game.impl.ThreeCupsGameMenu;
import it.unibo.goosegame.view.minigames.tris.impl.TrisMenu;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Implementation of {@link Cell}.
 */
public class CellImpl implements Cell {
    private final CellView view;
    private Optional<MinigameMenu> minigameMenu;
    private final List<Player> players;
    private Timer timer;
    private GameState gameState;

    private final Map<Class<? extends MinigameMenu>, Supplier<MinigameMenu>> menuMap = Map.of(
            ClickTheColorMenu.class, ClickTheColorMenu::new,
            HangmanMenu.class, HangmanMenu::new,
            HerdingHoundMenu.class, HerdingHoundMenu::new,
            HonkMandMenu.class, HonkMandMenu::new,
            MemoryMenu.class, MemoryMenu::new,
            PuzzleMenu.class, PuzzleMenu::new,
            RockPaperScissorsMenu.class, RockPaperScissorsMenu::new,
            SnakeMenu.class, SnakeMenu::new,
            ThreeCupsGameMenu.class, ThreeCupsGameMenu::new,
            TrisMenu.class, TrisMenu::new
    );

    /**
     * Constructor for non minigame cells.
     */
    public CellImpl() {
        this.view = new CellViewImpl();
        this.minigameMenu = Optional.empty();
        this.players = new ArrayList<>();
    }

    /**
     * Constructor for minigame cells.
     *
     * @param minigameMenu menu used to trigger the minigame
     */
    public CellImpl(final MinigameMenu minigameMenu) {
        this.view = new CellViewImpl();
        this.players = new ArrayList<>();

        if (minigameMenu == null) {
            this.minigameMenu = Optional.empty();
        } else {
            this.minigameMenu = Optional.of(minigameMenu);
        }
    }

    /**
     * Utility method to refresh the cell view.
     */
    private void updateCellView() {
        this.view.update(players);
    }

    /**
     * Utility method to refresh the minigame menu.
     * 
     * @param menu the old minigame menu
     * @return the new minigame menu
     */
    private Optional<MinigameMenu> refresh(final MinigameMenu menu) {
        final Supplier<MinigameMenu> factory = menuMap.get(menu.getClass());
        return factory == null ? Optional.empty() : Optional.of(factory.get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getCellPanel() {
        return this.view.getCellPanel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMinigameCell() {
        return this.minigameMenu.isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPlayer(final Player player) {
        this.players.add(player);
        SwingUtilities.invokeLater(this::updateCellView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void movePlayer(final Cell cell, final Player player) {
        this.players.remove(player);
        cell.addPlayer(player);

        SwingUtilities.invokeLater(this::updateCellView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsPlayer(final Player p) {
        return this.players.contains(p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void triggerMinigame() {
        if (this.minigameMenu.isEmpty()) {
            return;
        }

        final MinigameMenu menu = this.minigameMenu.get();
        gameState = GameState.NOT_STARTED;

        this.timer = new Timer(100, e -> {
            if (gameState == GameState.WON || gameState == GameState.LOST || gameState == GameState.TIE) {
                stop();
            } else {
                gameState = menu.getGameState();
            }
        });

        menu.initializeView();
        timer.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState checkGameState() {
        if (this.minigameMenu.isEmpty()) {
            return GameState.NOT_STARTED;
        }

        if (gameState == GameState.WON || gameState == GameState.LOST || gameState == GameState.TIE) {
            minigameMenu.get().dispose();
            this.minigameMenu = refresh(minigameMenu.get());
        }

        return gameState;
    }

    private void stop() {
        this.timer.stop();
    }
}
