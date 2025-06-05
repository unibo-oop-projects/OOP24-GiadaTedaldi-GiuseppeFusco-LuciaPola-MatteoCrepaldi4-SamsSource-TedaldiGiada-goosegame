package it.unibo.goosegame.view.finalboard;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import it.unibo.goosegame.controller.gameboard.api.GameBoard;
import it.unibo.goosegame.model.finalboard.api.FinalBoardLogic;
import it.unibo.goosegame.model.finalboard.impl.FinalBoardLogicImpl;

/**
 * FinalBoardGui class represents the graphical user interface for displaying the final board of the game.
 * It shows the players' names and their final positions in a table format.
 */
public class FinalBoardGui {
    private static final int TABLE_ROW_HEIGHT = 30;
    private static final int FRAME_HEIGHT = 400;
    private static final int FRAME_WIDTH = 600;
    private static final int SCROLL_PANE_HEIGHT = 90;
    private static final int SCROLL_PANE_WIDTH = 100;
    private final FinalBoardLogic logic;

    /**
     * Constructor for the FinalBoardGUI class.
     * Initializes the GUI components and displays the final board.
     * 
     * @param gameBoard the game board model containing the players' final positions
     */
    public FinalBoardGui(final GameBoard gameBoard) {
        this.logic = new FinalBoardLogicImpl(gameBoard);
        final JFrame frame = new JFrame("Final Board");

        final Image backgroundImage = new ImageIcon(Objects.requireNonNull(
                FinalBoardGui.class.getResource("/FinalBoard.png"))
        ).getImage();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        final JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        imagePanel.setLayout(new BorderLayout());
        final Object[][] rows = logic.getFinalBoard().entrySet().stream()
                .map(e -> new Object[]{e.getKey(), e.getValue()})
                .toArray(Object[][]::new);

        final String[] columns = {"Name", "Final Position"};
        final DefaultTableModel model = new DefaultTableModel(rows, columns) {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };
        final JTable table = new JTable(model);
        table.setRowHeight(TABLE_ROW_HEIGHT);
        final JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(SCROLL_PANE_WIDTH, SCROLL_PANE_HEIGHT));
        frame.add(imagePanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
