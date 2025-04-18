package it.unibo.goosegame.view.minigames.tris.impl;

import java.awt.*;
import javax.swing.*;

import it.unibo.goosegame.controller.minigames.tris.api.TrisController;
import it.unibo.goosegame.utilities.Position;
import it.unibo.goosegame.view.minigames.tris.api.TrisView;

/**
 * Implementation of the {@link TrisView} interface using JavaSwing.
 */
public class TrisViewImpl extends JFrame implements TrisView{
    private static int GRID_SIZE = 3;
    private JButton[][] buttons = new JButton[3][3];
    private JLabel statusLabel;
    private TrisController controller;

    /**
     * Constructs a new instance of {@link TrisViewImpl}.
     * 
     * @param controller the {@link TrisController} that manages user actions
     */
    public TrisViewImpl(TrisController controller) {
        this.controller = controller;
        this.setTitle("Tris - Tic Tac Toe");
        this.setSize(300,350);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE,GRID_SIZE));
        for(int i=0; i<GRID_SIZE; i++) {
            for(int j=0; j<GRID_SIZE; j++) {
                final int r = i;
                final int c = j;
                buttons[r][c] = new JButton();
                buttons[r][c].setFocusPainted(false);
                buttons[r][c].addActionListener(e -> this.controller.movesMaker(new Position(r, c)));
                gridPanel.add(buttons[r][c]);
            }
        }

        statusLabel = new JLabel("Your Turn!", SwingConstants.CENTER);
        this.add(statusLabel, BorderLayout.NORTH);
        this.add(gridPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    public void updateButton(Position pos, String symbol) {
        JButton button = buttons[pos.x()][pos.y()];
        button.setText(symbol);
        if(symbol.equals("X")) {
            button.setForeground(Color.RED);
        } else if(symbol.equals("O")){
            button.setForeground(Color.BLUE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStatus(String msg) {
        statusLabel.setText(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disableButtons() {
        for(JButton[] row: buttons) {
            for(JButton btn : row) {
                btn.setEnabled(false);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeGame(String result) {
        JOptionPane.showMessageDialog(this, result + "\nThe windows will now close");
        System.exit(0);
    }
}
