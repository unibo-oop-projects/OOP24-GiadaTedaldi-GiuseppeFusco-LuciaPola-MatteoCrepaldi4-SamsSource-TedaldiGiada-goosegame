package it.unibo.goosegame.view.cell.api;

import javax.swing.JPanel;

/**
 * View for {@link it.unibo.goosegame.controller.cell.api.Cell} elements.
 */
public interface CellView {
    /**
     * Utility function to get the cell panel.
     *
     * @return cell {@link JPanel} already formatted
     */
    JPanel getCellPanel();
}
