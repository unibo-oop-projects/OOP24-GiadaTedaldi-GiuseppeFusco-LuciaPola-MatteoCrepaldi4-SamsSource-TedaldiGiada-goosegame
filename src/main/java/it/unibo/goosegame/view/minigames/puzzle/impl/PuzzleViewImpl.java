package it.unibo.goosegame.view.minigames.puzzle.impl;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unibo.goosegame.controller.minigames.puzzle.api.PuzzleController;
import it.unibo.goosegame.utilities.Position;
import it.unibo.goosegame.view.minigames.puzzle.api.PuzzleView;

/**
 * Implementation of the {@link PuzzleView} interface using JavaSwing.
 */
public class PuzzleViewImpl extends JFrame implements PuzzleView {
    private static final long serialVersionUID = 1L;
    private static final int GRID_SIZE = 5;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private final transient Logger logger = Logger.getLogger(PuzzleViewImpl.class.getName());
    private final JButton[][] buttons = new JButton[GRID_SIZE][GRID_SIZE];
    private transient PuzzleController controller;
    private final JLabel timerLabel = new JLabel("Time: 02:30");
    private final JButton startButton = new JButton("Start");

    /**
     * Constructs a new instance of {@link PuzzleViewImpl}.
     * 
     */
    public PuzzleViewImpl() {
        super();
        SwingUtilities.invokeLater(this::configUI);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setController(final PuzzleController controller) {
        this.controller = Objects.requireNonNull(controller);
    }

    /**
     * Configurates the window properties.
     */
    private void configUI() {
        this.setTitle("Puzzle");
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        final JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 0, 0));
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                final JButton button = new JButton();
                button.setEnabled(false);
                button.setBorder(BorderFactory.createEmptyBorder());
                button.setMargin(new Insets(0, 0, 0, 0));
                button.setContentAreaFilled(false);
                buttons[i][j] = button;
                final int r = i;
                final int c = j;
                button.addActionListener(e -> {
                    this.controller.clickHandler(new Position(r, c));
                });
                gridPanel.add(button);
            }
        }
        final JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(timerLabel, BorderLayout.WEST);
        controlPanel.add(startButton, BorderLayout.EAST);
        startButton.addActionListener(e -> {
            this.controller.shufflePuzzle();
            this.enableButtons(true);
            this.updateView();
            startButton.setEnabled(false);
        });
        this.add(gridPanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.SOUTH);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                updateView();
            }
            @Override
            public void componentShown(final ComponentEvent e) {
                updateView();
            }
        });
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updateView() {
        final int cellWidth = buttons[0][0].getWidth();
        final int cellHeight = buttons[0][0].getHeight();
        final Map<Position, Integer> grid = this.controller.getGridData();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j].setIcon(null);
            }
        }
        for (final Map.Entry<Position, Integer> entry : grid.entrySet()) {
            final Position pos = entry.getKey();
            final int tileVal = entry.getValue();
            buttons[pos.x()][pos.y()].setIcon(loadAndScale(tileVal, cellWidth, cellHeight));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateTimerLabel(final String time) {
        SwingUtilities.invokeLater(() -> timerLabel.setText(time));
    }

    /**
     * Loads an image for a specific tile value and scales it to fit the button size.
     * 
     * @param tileVal the value representing the tile
     * @param cellWidth the width of the cell
     * @param cellHeight the height of the cell
     * @return an Icon representing the tile image
     */
    private Icon loadAndScale(final int tileVal, final int cellWidth, final int cellHeight) {
        final String path = "/img/minigames/puzzle/tile25_" + tileVal + ".png";
        final URL imageUrl = PuzzleViewImpl.class.getResource(path);
        if (imageUrl == null) {
            this.logger.warning("Immagine non trovata: " + path);
            return null;
        }
        final ImageIcon origIcon = new ImageIcon(imageUrl);
        final Image scaledImg = origIcon.getImage().getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    /**
     * Enables or disables all the buttons on the grid.
     * 
     * @param enable true to enable the buttons, false to disable them
     */
    private void enableButtons(final boolean enable) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j].setEnabled(enable);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void endGame() {
        startButton.setEnabled(false);
        this.enableButtons(false);
        this.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showResultMessage(final boolean isWin) {
        final String resultMsg = isWin ? "Puzzle completed: YOU WON!" : "Time is Over: YOU LOST!";
        JOptionPane.showMessageDialog(this, resultMsg);
    }

}
