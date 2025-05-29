package it.unibo.goosegame.view.gameboard.impl;

import it.unibo.goosegame.controller.cell.api.Cell;
import it.unibo.goosegame.model.gameboard.api.GameBoardModel;
import it.unibo.goosegame.view.gameboard.api.GameBoardView;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.WindowConstants;
import java.awt.GridLayout;
import java.awt.BorderLayout;

/**
 * Implementation of {@link GameBoardView}.
 */
public final class GameBoardViewImpl implements GameBoardView {
    private static final int BOARD_DIMENSION = 16;
    private static final int FRAME_SIZE = 600;

    private final GameBoardModel model;
    private final JFrame frame;
    private final List<Cell> boardCells;

    /**
     * Constructor for the gameboard view component.
     *
     * @param model model component of the game
     * @param gameCells list of all the cells of the board
     */
    public GameBoardViewImpl(final GameBoardModel model, final List<Cell> gameCells) {
        this.frame = new JFrame();
        this.model = model;
        this.boardCells = gameCells;

        initView();
    }

    /**
     * Utility function used to initialise the view components.
     */
    private void initView() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(FRAME_SIZE, FRAME_SIZE);

        final JPanel gameboardPanel = new JPanel(new GridLayout(BOARD_DIMENSION, BOARD_DIMENSION));
        final JPanel buttonsPanel = new JPanel();

        initGameboard(gameboardPanel);

        buttonsPanel.add(new JButton("Bottone di prova"));

        frame.add(gameboardPanel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
    }

    /**
     * Utility function used to initialise the gameboard panel.
     *
     * @param container container panel for the gameboard
     */
    private void initGameboard(final JPanel container) {
        int counter = 0;

        for (int i = 0; i < BOARD_DIMENSION; i++) {
            for (int j = 0; j < BOARD_DIMENSION; j++) {
                if (i == 0 || i == BOARD_DIMENSION - 1 || j == 0 || j == BOARD_DIMENSION - 1) {
                    container.add(boardCells.get(counter).getCellPanel());
                    counter++;
                } else {
                    container.add(new JPanel());
                }
            }
        }
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }
}
