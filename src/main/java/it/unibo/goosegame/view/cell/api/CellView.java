package it.unibo.goosegame.view.cell.api;

import it.unibo.goosegame.model.player.api.Player;

import javax.swing.JPanel;
import java.util.List;

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

    /**
     * Updates the cell view with the current players.
     *
     * @param players list of players to update the view with
     */
    void update(List<Player> players);
}
