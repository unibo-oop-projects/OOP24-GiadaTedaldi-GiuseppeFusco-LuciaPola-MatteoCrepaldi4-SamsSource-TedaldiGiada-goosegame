package it.unibo.goosegame.view.finalboard;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import it.unibo.goosegame.controller.gameboard.api.GameBoard;
import it.unibo.goosegame.model.finalboard.api.FinalBoardLogic;
import it.unibo.goosegame.model.finalboard.impl.FinalBoardLogicImpl;
import it.unibo.goosegame.view.gamemenu.impl.GameMenu;

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
    private static final int BUTTON_WIDTH = 180;
    private static final int BUTTON_HEIGHT = 150;
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
                FinalBoardGui.class.getResource("/img/startendmenu/FinalBoard.png"))
        ).getImage();
       frame.setMinimumSize(new Dimension(FRAME_WIDTH, SCROLL_PANE_HEIGHT + BUTTON_HEIGHT));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(true);
        final JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        imagePanel.setLayout(new BorderLayout());

        final ImageIcon newGameIcon = new ImageIcon(Objects.requireNonNull(
            FinalBoardGui.class.getResource("/img/startendmenu/newgame.png")
        ));
        final JButton newGamButton = createButton(newGameIcon, BUTTON_WIDTH, BUTTON_HEIGHT, frame);
        final JPanel buttonWrapper = new JPanel(new GridBagLayout());
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(newGamButton);
        imagePanel.add(buttonWrapper, BorderLayout.CENTER);
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

    private JButton createButton(final ImageIcon image, final int width, final int height, final JFrame frame) {
        final JButton button = new JButton();
        button.setPreferredSize(new Dimension(width, height));
        final Image scaledImage = image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.addActionListener(e -> {
            frame.dispose();
            final GameMenu newMenu = new GameMenu();
            newMenu.setVisible(true);
        });
        return button;
    }
}
