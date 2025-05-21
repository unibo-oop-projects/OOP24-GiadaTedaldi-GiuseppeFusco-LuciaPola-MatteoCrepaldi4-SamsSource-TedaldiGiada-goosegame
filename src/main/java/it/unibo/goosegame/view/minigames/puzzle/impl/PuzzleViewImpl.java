package it.unibo.goosegame.view.minigames.puzzle.impl;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.util.Map;
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
import javax.swing.Timer;

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
    private static final int INIT_TIME = 150;
    private final transient Logger logger = Logger.getLogger(PuzzleViewImpl.class.getName());
    private final JButton[][] buttons = new JButton[GRID_SIZE][GRID_SIZE];
    private final transient PuzzleController controller;
    private final JLabel timerLabel = new JLabel("Time: 02:30");
    private final JButton startButton = new JButton("Start");
    private Timer gameTimer;
    private int timeLeft;

    /**
     * Constructs a new instance of {@link PuzzleViewImpl}.
     */
    public PuzzleViewImpl(final PuzzleController controller) {
        super();
        this.controller = controller;
        SwingUtilities.invokeLater(this::configUI);
    }

    /**
     * Configurates the window properties.
     */
    private void configUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
            this.startGameTimer();
            startButton.setEnabled(false);
        });
        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                updateView();
            }
            @Override
            public void componentShown(final ComponentEvent e) {
                updateView();
            }
        });
        setVisible(true);
    }

    /**
     * Starts the game timer.
     * Stops the timer and shows a message when time is up.
     */
    private void startGameTimer() {
        this.timeLeft = INIT_TIME;
        timerLabel.setText("Time: 02:30");
        if (this.gameTimer != null && this.gameTimer.isRunning()) {
            this.gameTimer.stop();
        }
        this.gameTimer = new Timer(1000, e -> {
            this.timeLeft--;
            final int min = timeLeft / 60;
            final int sec = timeLeft % 60;
            timerLabel.setText(String.format("Time: %02d:%02d", min, sec));
            if (this.timeLeft <= 0) {
                this.gameTimer.stop();
                JOptionPane.showMessageDialog(this, "Time is over! You lost.");
                this.gameOver();
            }
        });
        this.gameTimer.start();
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
                final int tileVal = grid.get(new Position(i, j));
                buttons[i][j].setIcon(loadAndScale(tileVal, cellWidth, cellHeight));
            }
        }
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
        final String path = "/img/puzzle25_tiles/tile25_" + tileVal + ".png";
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
    public void showWinMessage() {
        JOptionPane.showMessageDialog(this, "Puzzle completed! You won.");
        this.gameOver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopTimer() {
        if (this.gameTimer != null) {
            this.gameTimer.stop();
        }
    }

    /**
     * Ends the game by disabling the buttons and setting the default close operation.
     */
    private void gameOver() {
        startButton.setEnabled(false);
        this.enableButtons(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
