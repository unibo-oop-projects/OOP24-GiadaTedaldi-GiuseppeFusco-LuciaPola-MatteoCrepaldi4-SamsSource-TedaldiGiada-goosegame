package it.unibo.goosegame.view;

import it.unibo.goosegame.model.minigames.herdinghound.impl.DogImpl;
import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import it.unibo.goosegame.utilities.Position;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * View per il minigioco Herding Hound.
 * Si occupa solo della presentazione grafica.
 */
public class HerdingHoundView extends JPanel {
    // ... altre costanti e variabili ...
    private static final int BLINK_DELAY = 200;
    private static final int BLINK_TOTAL = 6; // 3 lampeggi (on+off)
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

    private final HerdingHoundModel model;

    // Lampeggiamento zone rosse
    private boolean blinking = false;
    private boolean blinkOn = true;
    private int blinkCount = 0;
    private Timer blinkTimer;
    // Lampeggiamento oca
    private boolean blinkGoose = false;
    private boolean blinkGooseOn = true;

    // Countdown iniziale
    private boolean countdownActive = false;
    private int countdownValue = 3;
    private boolean showGoText = false;
    private Timer countdownTimer;
    private Runnable countdownFinishCallback;

    public HerdingHoundView(final HerdingHoundModel model) {
        this.model = Objects.requireNonNull(model, "Model non può essere null");
        setPreferredSize(new Dimension(DEFAULT_SIZE, DEFAULT_SIZE));
        setBackground(BACKGROUND_COLOR);
    }

    /** Avvia il countdown iniziale, poi chiama onFinish.run() */
    public void startCountdown(Runnable onFinish) {
        countdownValue = 3;
        showGoText = false;
        countdownActive = true;
        countdownFinishCallback = onFinish;
        repaint(); // Mostra subito il 3
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
        countdownTimer.setInitialDelay(1000); // Aspetta 1 secondo prima del primo tick
        countdownTimer.start();
    }

    public boolean isCountdownActive() {
        return countdownActive;
    }

    /**
     * Avvia il lampeggio di fine partita. Se ha vinto, lampeggia l'oca.
     */
    public void startBlinking(JFrame frame, boolean hasWon) {
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

        // Sfondo verde prato
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, w, h);

        // Griglia
        g.setColor(GRID_COLOR);
        for (int i = 0; i <= gridSize; i++) {
            g.drawLine(xOffset, yOffset + i * cellSize, xOffset + gridWidth, yOffset + i * cellSize);
            g.drawLine(xOffset + i * cellSize, yOffset, xOffset + i * cellSize, yOffset + gridHeight);
        }

        // Zone potenzialmente visibili dal cane (verde chiaro)
        g.setColor(VISIBLE_AREA_COLOR);
        for (Position pos : model.getDog().getVisibleArea()) {
            drawCell(g, pos, cellSize, xOffset, yOffset);
        }

        // Zone effettivamente visibili dal cane quando è sveglio (rosso trasparente)
        if (model.getDog().getState() == DogImpl.State.AWAKE && (!blinking || blinkOn)) {
            g.setColor(DOG_VISIBLE_COLOR);
            for (Position pos : model.getVisible()) {
                drawCell(g, pos, cellSize, xOffset, yOffset);
            }
        }

        // Ombre
        g.setColor(DOG_SHADOW_COLOR);
        for (Position shadow : model.getShadows()) {
            drawCell(g, shadow, cellSize, xOffset, yOffset);
        }

        // Scatole
        g.setColor(BOX_COLOR);
        for (Position box : model.getBoxes()) {
            drawCell(g, box, cellSize, xOffset, yOffset);
        }

        // Cane
        final Position dogPos = model.getDog().getCoord();
        g.setColor(switch (model.getDog().getState()) {
            case AWAKE -> DOG_AWAKE_COLOR;
            case ALERT -> DOG_ALERT_COLOR;
            default -> DOG_DEFAULT_COLOR;
        });
        drawCell(g, dogPos, cellSize, xOffset, yOffset);

        // Simbolo sopra il cane
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

        // Oca (lampeggia solo se blinkGoose attivo)
        if (!blinkGoose || blinkGooseOn) {
            g.setColor(Color.WHITE);
            drawCell(g, model.getGoose().getCoord(), cellSize, xOffset, yOffset);
        }

        // Countdown centrale
        if (countdownActive) {
            g.setColor(Color.BLACK);
            String text = showGoText ? "CORRI CORRI!" : (countdownValue > 0 ? String.valueOf(countdownValue) : "");
            Font font = new Font("Arial", Font.BOLD, showGoText ? 60 : 80);
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            int cx = w / 2 - textWidth / 2;
            int cy = h / 2 + textHeight / 4;
            g.drawString(text, cx, cy);
        }
    }

    private void drawCell(final Graphics g, final Position coord,
                          final int size, final int xOffset, final int yOffset) {
        final int x = xOffset + coord.y() * size;
        final int y = yOffset + coord.x() * size;
        g.fillRect(x, y, size, size);
    }

    /** Aggiorna la view. */
    public void updateView() {
        repaint();
    }

    /** Mostra un pannello di fine gioco con messaggio e pulsante Chiudi. */
    public void showGameOverPanel(JFrame frame, boolean hasWon) {
        JPanel endPanel = new JPanel(new GridBagLayout());
        String message = hasWon ? "Hai Vinto!" : "Hai Perso!";
        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 32));
        JButton closeButton = new JButton("Chiudi");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 20));
        closeButton.addActionListener(e -> frame.dispose());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(10,10,10,10);
        endPanel.add(label, gbc);
        gbc.gridy = 1;
        endPanel.add(closeButton, gbc);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(endPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
}
