package it.unibo.goosegame.view.minigames.tris.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import it.unibo.goosegame.controller.minigames.tris.api.TrisController;
import it.unibo.goosegame.utilities.Position;
import it.unibo.goosegame.view.minigames.tris.api.TrisView;

/**
 * Implementation of the {@link TrisView} interface using JavaSwing.
 */
public class TrisViewImpl extends JFrame implements TrisView {
    private static final long serialVersionUID = 1L;
    private static final int GRID_SIZE = 3;
    private static final int WIDTH = 300;
    private static final int HEIGHT = 350;
    private final JButton[][] buttons = new JButton[GRID_SIZE][GRID_SIZE];
    private JLabel statusLabel;
    private transient TrisController controller;

    /**
     * Constructs a new instance of {@link TrisViewImpl}.
     * 
     */
    public TrisViewImpl() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setController(final TrisController controller) {
        this.controller = controller;
        SwingUtilities.invokeLater(this::configUI);
    }

    /**
     * Configurates the window properties.
     */
    private void configUI() {
        this.setTitle("Tris - Tic Tac Toe");
        this.setSize(WIDTH, HEIGHT);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        final JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                final int r = i;
                final int c = j;
                buttons[r][c] = new JButton();
                buttons[r][c].setFocusPainted(false);
                buttons[r][c].addActionListener(e -> {
                    if (this.controller != null) {
                        this.controller.movesMaker(new Position(r, c));
                    }
                });
                gridPanel.add(buttons[r][c]);
            }
        }
        statusLabel = new JLabel("Your Turn!", SwingConstants.CENTER);
        this.add(statusLabel, BorderLayout.NORTH);
        this.add(gridPanel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateButton(final Position pos, final String symbol) {
        final JButton button = buttons[pos.x()][pos.y()];
        button.setText(symbol);
        if ("X".equals(symbol)) {
            button.setForeground(Color.RED);
        } else if ("O".equals(symbol)) {
            button.setForeground(Color.BLUE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStatus(final String msg) {
        statusLabel.setText(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disableButtons() {
        for (final JButton[] row: buttons) {
            for (final JButton button : row) {
                button.setEnabled(false);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endGame(final String result) {
        JOptionPane.showMessageDialog(this, result);
        this.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }
}
