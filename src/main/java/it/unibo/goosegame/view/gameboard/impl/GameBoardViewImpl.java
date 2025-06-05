package it.unibo.goosegame.view.gameboard.impl;

import it.unibo.goosegame.controller.cell.api.Cell;
import it.unibo.goosegame.model.gameboard.api.GameBoardModel;
import it.unibo.goosegame.view.gameboard.api.GameBoardView;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
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
    private final JLabel currentPlayerLabel;
    private final JButton nextTurnButton;
    private final JButton diceButton;
    private final JButton openSatchelButton;
    private final List<Cell> boardCells;

    /**
     * Constructor for the gameboard view component.
     *
     * @param model model component of the game
     * @param gameCells list of all the cells of the board
     */
    public GameBoardViewImpl(final GameBoardModel model, final List<Cell> gameCells) {
        final ImageIcon satchelIcon = new ImageIcon("src/main/resources/img/satchel.png");
        this.frame = new JFrame();
        this.model = model;
        this.currentPlayerLabel = new JLabel();
        this.nextTurnButton = new JButton("Next turn");
        this.diceButton = new JButton("Throw dices");
        this.openSatchelButton = new JButton(satchelIcon);
        this.boardCells = List.copyOf(gameCells);

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
        final JPanel infoPanel = new JPanel(new BorderLayout());

        initGameboard(gameboardPanel);

        diceButton.addActionListener(e -> {
            clickedDiceButton();
        });

        nextTurnButton.addActionListener(e -> {
            clickedNextTurnButton();
        });
        nextTurnButton.setEnabled(false);

        openSatchelButton.addActionListener(e -> {
            model.getCurrentPlayer().getSatchel().showSatchel();
        });

        currentPlayerLabel.setText("Current player: " + model.getCurrentPlayer().getName());

        buttonsPanel.add(nextTurnButton);
        buttonsPanel.add(diceButton);
        buttonsPanel.add(openSatchelButton);

        infoPanel.add(buttonsPanel, BorderLayout.CENTER);
        infoPanel.add(currentPlayerLabel, BorderLayout.EAST);

        frame.add(gameboardPanel, BorderLayout.CENTER);
        frame.add(infoPanel, BorderLayout.SOUTH);
    }

    /**
     * Method called when the dice button is clicked.
     */
    private void clickedDiceButton() {
        model.throwDices();
        diceButton.setEnabled(false);
        nextTurnButton.setEnabled(true);
    }

    /**
     * Method called when the next turn button is clicked.
     * It updates the current player label and enables the dice button.
     */
    private void clickedNextTurnButton() {
        model.nextTurn();
        currentPlayerLabel.setText("Current player: " + model.getCurrentPlayer().getName());
        nextTurnButton.setEnabled(false);
        diceButton.setEnabled(true);
    }

    /**
     * Utility function used to initialise the gameboard panel.
     *
     * @param container container panel for the gameboard
     */
    private void initGameboard(final JPanel container) {

        for (int i = 0; i < BOARD_DIMENSION; i++) {
            for (int j = 0; j < BOARD_DIMENSION; j++) {
                if (i == 0 || i == BOARD_DIMENSION - 1 || j == 0 || j == BOARD_DIMENSION - 1) {
                    container.add(boardCells.get(gridToLinear(i, j)).getCellPanel());
                } else {
                    container.add(new JPanel());
                }
            }
        }
    }

    private int gridToLinear(final int row, final int col) {
        if (row == 0) {
            return col; // Lato alto
        }

        if (col == BOARD_DIMENSION - 1) {
            return BOARD_DIMENSION - 1 + row; // Lato destro
        }

        if (row == BOARD_DIMENSION - 1) {
            return 2 * BOARD_DIMENSION - 2 + BOARD_DIMENSION - 1 - col; // Lato basso
        }

        return 3 * BOARD_DIMENSION - 3 + BOARD_DIMENSION - 1 - row; // Lato sinistro
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }

    @Override
    public void disposeFrame() {
        this.frame.dispose();
    }
}
