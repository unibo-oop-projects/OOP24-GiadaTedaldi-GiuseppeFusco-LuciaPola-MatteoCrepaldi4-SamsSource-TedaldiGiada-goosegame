package it.unibo.goosegame.view.cell.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.goosegame.model.player.api.Player;
import it.unibo.goosegame.view.cell.api.CellView;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import java.awt.Color;
import java.util.List;

/**
 * Implementation of {@link CellView}.
 */
public class CellViewImpl implements CellView {
    private final JPanel cellLabel;

    /**
     * Constructor for the cell graphical element.
     *
     */
    public CellViewImpl() {
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
     *
     * The Panel needs to be exposed, since it's needed from the main board
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
    @Override
    public JPanel getCellPanel() {
        return cellLabel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final List<Player> players) {
        cellLabel.removeAll();

        for (final Player player : players) {
            final JButton playerIcon = new JButton();
            playerIcon.setBackground(player.getColor());
            playerIcon.addActionListener(e -> {
                JOptionPane.showMessageDialog(cellLabel, player.getName());
            });
            cellLabel.add(playerIcon);
        }

        cellLabel.revalidate();
        cellLabel.repaint();
    }
}
