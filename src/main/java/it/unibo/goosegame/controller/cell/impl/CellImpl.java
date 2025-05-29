package it.unibo.goosegame.controller.cell.impl;

import it.unibo.goosegame.controller.cell.api.Cell;
import it.unibo.goosegame.model.cell.api.CellModel;
import it.unibo.goosegame.model.cell.impl.CellModelImpl;
import it.unibo.goosegame.view.cell.api.CellView;
import it.unibo.goosegame.view.cell.impl.CellViewImpl;
import it.unibo.goosegame.view.general.api.MinigameMenu;

import javax.swing.JPanel;
import java.util.Optional;

/**
 * Implementation of {@link Cell}.
 */
public class CellImpl implements Cell {
    private final CellModel model;
    private final CellView view;
    private final Optional<MinigameMenu> minigameMenu;

    /**
     * Constructor for non minigame cells.
     */
    public CellImpl() {
        this.model = new CellModelImpl();
        this.view = new CellViewImpl(model);
        this.minigameMenu = Optional.empty();
    }

    /**
     * Constructor for minigame cells.
     *
     * @param minigameMenu menu used to trigger the minigame
     */
    public CellImpl(final MinigameMenu minigameMenu) {
        this.model = new CellModelImpl();
        this.view = new CellViewImpl(model);

        this.minigameMenu = Optional.of(minigameMenu);
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
}
