package it.unibo.goosegame.view;

import it.unibo.goosegame.model.minigames.herdinghound.impl.DogImpl;
import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import it.unibo.goosegame.utilities.Position;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Objects;

/**
 * View for the Herding Hound minigame.
 * Handles only the graphical presentation.
 */
public final class HerdingHoundView extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int BLINK_DELAY = 200;
    private static final int BLINK_TOTAL = 6; // 3 blinks (on+off)
    private static final int DEFAULT_SIZE = 600;
    private static final Color BACKGROUND_COLOR = new Color(0x7EC850);
    private static final Color GRID_COLOR = new Color(0x3E3E3E);
    private static final Color VISIBLE_AREA_COLOR = new Color(0xB6FFB6);
    private static final Color DOG_VISIBLE_COLOR = new Color(255, 0, 0, 80);
    private static final Color DOG_SHADOW_COLOR = new Color(0, 0, 0, 60);
    private static final Color BOX_COLOR = new Color(0x8B5A2B);
    private static final Color DOG_AWAKE_COLOR = Color.RED;
    private static final Color DOG_ALERT_COLOR = Color.YELLOW;
    private static final Color DOG_DEFAULT_COLOR = Color.WHITE;

    private final transient HerdingHoundModel model;

    // Blinking of red zones
    private boolean blinking;
    private boolean blinkOn = true;
    private int blinkCount;
    private Timer blinkTimer;
    // Goose blinking
    private boolean blinkGoose;
    private boolean blinkGooseOn = true;

    // Initial countdown
    private boolean countdownActive;
    private int countdownValue = 3;
    private boolean showGoText;
    private Timer countdownTimer;
    private transient Runnable countdownFinishCallback;

    /**
     * Constructs a HerdingHoundView.
     * @param model the game model (must not be null)
     */
    public HerdingHoundView(final HerdingHoundModel model) {
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        setPreferredSize(new Dimension(DEFAULT_SIZE, DEFAULT_SIZE));
        setBackground(BACKGROUND_COLOR);
    }

    /**
     * Starts the initial countdown, then calls onFinish.run().
     * @param onFinish the callback to execute when the countdown finishes
     */
    public void startCountdown(final Runnable onFinish) {
        countdownValue = 3;
        showGoText = false;
        countdownActive = true;
        countdownFinishCallback = onFinish;
        repaint(); // Show 3 immediately
        countdownTimer = new Timer(1000, e -> {
            if (countdownValue > 1) {
                countdownValue--;
            } else if (!showGoText) {
                showGoText = true;
                countdownValue = 0;
            } else {
                countdownTimer.stop();
                countdownActive = false;
                showGoText = false;
                repaint();
                if (countdownFinishCallback != null) {
                    countdownFinishCallback.run();
                }
                return;
            }
            repaint();
        });
        countdownTimer.setInitialDelay(1000); // Wait 1 second before the first tick
        countdownTimer.start();
    }

    /**
     * Returns whether the countdown is active.
     * @return true if the countdown is active, false otherwise
     */
    public boolean isCountdownActive() {
        return countdownActive;
    }

    /**
     * Starts the end-of-game blinking animation. If the player has won, the goose blinks.
     * @param frame the JFrame to show the end panel on
     * @param hasWon true if the player has won, false otherwise
     */
    public void startBlinking(final JFrame frame, final boolean hasWon) {
        blinking = true;
        blinkOn = true;
        blinkCount = 0;
        blinkGoose = hasWon;
        blinkGooseOn = true;
        blinkTimer = new Timer(BLINK_DELAY, e -> {
            blinkOn = !blinkOn;
            if (blinkGoose) {
                blinkGooseOn = !blinkGooseOn;
            }
            blinkCount++;
            repaint();
            if (blinkCount >= BLINK_TOTAL) {
                blinkTimer.stop();
                blinking = false;
                blinkGoose = false;
                showGameOverPanel(frame, hasWon);
            }
        });
        blinkTimer.setInitialDelay(0);
        blinkTimer.start();
    }

    /**
     * Paints the game grid, elements, and effects.
     * @param g the Graphics context
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final int gridSize = model.getGrid();
        final int w = getWidth();
        final int h = getHeight();
        final int cellSize = Math.min(w, h) / gridSize;
        final int gridWidth = cellSize * gridSize;
        final int gridHeight = cellSize * gridSize;
        final int xOffset = (w - gridWidth) / 2;
        final int yOffset = (h - gridHeight) / 2;

        // Green grass background
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, w, h);

        // Grid
        g.setColor(GRID_COLOR);
        for (int i = 0; i <= gridSize; i++) {
            g.drawLine(xOffset, yOffset + i * cellSize, xOffset + gridWidth, yOffset + i * cellSize);
            g.drawLine(xOffset + i * cellSize, yOffset, xOffset + i * cellSize, yOffset + gridHeight);
        }

        // Zones potentially visible by the dog (light green)
        g.setColor(VISIBLE_AREA_COLOR);
        for (final Position pos : model.getDog().getVisibleArea()) {
            drawCell(g, pos, cellSize, xOffset, yOffset);
        }

        // Zones actually visible by the dog when awake (transparent red)
        if (model.getDog().getState() == DogImpl.State.AWAKE && (!blinking || blinkOn)) {
            g.setColor(DOG_VISIBLE_COLOR);
            for (final Position pos : model.getVisible()) {
                drawCell(g, pos, cellSize, xOffset, yOffset);
            }
        }

        // Shadows
        g.setColor(DOG_SHADOW_COLOR);
        for (final Position shadow : model.getShadows()) {
            drawCell(g, shadow, cellSize, xOffset, yOffset);
        }

        // Boxes
        g.setColor(BOX_COLOR);
        for (final Position box : model.getBoxes()) {
            drawCell(g, box, cellSize, xOffset, yOffset);
        }

        // Dog
        final Position dogPos = model.getDog().getCoord();
        g.setColor(switch (model.getDog().getState()) {
            case AWAKE -> DOG_AWAKE_COLOR;
            case ALERT -> DOG_ALERT_COLOR;
            default -> DOG_DEFAULT_COLOR;
        });
        drawCell(g, dogPos, cellSize, xOffset, yOffset);

        // Symbol above the dog
        if (model.getDog().getState() != DogImpl.State.ASLEEP) {
            final String symbol = model.getDog().getState() == DogImpl.State.AWAKE ? "!" : "?";
            g.setColor(Color.BLACK);
            final Font font = new Font("Arial", Font.BOLD, cellSize / 2);
            g.setFont(font);
            final FontMetrics fm = g.getFontMetrics();
            final int textWidth = fm.stringWidth(symbol);
            final int textHeight = fm.getHeight();
            final int centerX = xOffset + dogPos.y() * cellSize + (cellSize - textWidth) / 2;
            final int centerY = yOffset + dogPos.x() * cellSize - textHeight / 4;
            g.drawString(symbol, centerX, centerY);
        }

        // Goose (blinks only if blinkGoose is active)
        if (!blinkGoose || blinkGooseOn) {
            g.setColor(Color.WHITE);
            drawCell(g, model.getGoose().getCoord(), cellSize, xOffset, yOffset);
        }

        // Central countdown
        if (countdownActive) {
            g.setColor(Color.BLACK);
            final String text = showGoText ? "RUN RUN!" : countdownValue > 0 ? String.valueOf(countdownValue) : "";
            final Font font = new Font("Arial", Font.BOLD, showGoText ? 60 : 80);
            g.setFont(font);
            final FontMetrics fm = g.getFontMetrics();
            final int textWidth = fm.stringWidth(text);
            final int textHeight = fm.getHeight();
            final int cx = w / 2 - textWidth / 2;
            final int cy = h / 2 + textHeight / 4;
            g.drawString(text, cx, cy);
        }
    }

    private void drawCell(final Graphics g, final Position coord,
                          final int size, final int xOffset, final int yOffset) {
        final int x = xOffset + coord.y() * size;
        final int y = yOffset + coord.x() * size;
        g.fillRect(x, y, size, size);
    }

    /**
     * Updates the view (repaints the panel).
     */
    public void updateView() {
        repaint();
    }

    /**
     * Shows an end-of-game panel with a message and close button.
     * @param frame the JFrame to show the panel on
     * @param hasWon true if the player has won, false otherwise
     */
    public void showGameOverPanel(final JFrame frame, final boolean hasWon) {
        final String message = hasWon ? "You Won!" : "You Lost!";
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new GameEndPanel(message, frame::dispose, "HerdingHound", hasWon), BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
}
