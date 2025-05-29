package it.unibo.goosegame.view.cell.impl;

import it.unibo.goosegame.model.cell.api.CellModel;
import it.unibo.goosegame.view.cell.api.CellView;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import java.awt.Color;

/**
 * Implementation of {@link CellView}.
 */
public class CellViewImpl implements CellView {
    private final CellModel model;
    private final JPanel cellLabel;

    /**
     * Constructor for the cell graphical element.
     *
     * @param model model element of the cell
     */
    public CellViewImpl(final CellModel model) {
        this.model = model;
        this.cellLabel = new JPanel(new GridLayout(2, 2));


        cellInit();
    }

    /**
     * Utility function to change the panel look.
     */
    private void cellInit() {
        cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getCellPanel() {
        return cellLabel;
    }
}
