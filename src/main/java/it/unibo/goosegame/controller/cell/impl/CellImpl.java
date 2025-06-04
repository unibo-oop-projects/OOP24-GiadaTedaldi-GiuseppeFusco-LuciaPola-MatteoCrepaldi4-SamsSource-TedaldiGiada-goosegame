package it.unibo.goosegame.controller.cell.impl;

import it.unibo.goosegame.controller.cell.api.Cell;
import it.unibo.goosegame.model.cell.api.CellModel;
import it.unibo.goosegame.model.cell.impl.CellModelImpl;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.model.player.api.Player;
import it.unibo.goosegame.view.cell.api.CellView;
import it.unibo.goosegame.view.cell.impl.CellViewImpl;
import it.unibo.goosegame.view.general.api.MinigameMenu;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link Cell}.
 */
public class CellImpl implements Cell {
    private final CellModel model;
    private final CellView view;
    private final Optional<MinigameMenu> minigameMenu;
    private final List<Player> players;

    /**
     * Constructor for non minigame cells.
     */
    public CellImpl() {
        this.model = new CellModelImpl();
        this.view = new CellViewImpl(model);
        this.minigameMenu = Optional.empty();
        this.players = new ArrayList<>();
    }

    /**
     * Constructor for minigame cells.
     *
     * @param minigameMenu menu used to trigger the minigame
     */
    public CellImpl(final MinigameMenu minigameMenu) {
        this.model = new CellModelImpl();
        this.view = new CellViewImpl(model);
        this.players = new ArrayList<>();

        if (minigameMenu == null) {
            this.minigameMenu = Optional.empty();
        } else {
            this.minigameMenu = Optional.of(minigameMenu);
        }
    }

    private void updateCellView() {
        this.view.update(players);
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

        System.out.println(cell.isMinigameCell());

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
    public GameState triggerMinigame() {
        if (this.minigameMenu.isEmpty()) {
            return GameState.NOT_STARTED;
        }

        return GameState.ONGOING;
    }
}
