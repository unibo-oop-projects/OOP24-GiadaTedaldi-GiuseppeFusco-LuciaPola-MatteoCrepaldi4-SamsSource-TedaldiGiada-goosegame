package it.unibo.goosegame.view;

import it.unibo.goosegame.utilities.Colors;
import it.unibo.goosegame.utilities.HonkMandMessages;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Vista moderna e ridimensionabile per il minigioco HonkMand (Simon Game).
 * Pulsanti rotondi con glow, layout flessibile e UI accattivante.
 */
public class HonkMandView extends JFrame {
    private final Map<Colors, RoundButton> buttons;
    private final JButton startButton;
    private final JLabel levelLabel;
    private final JLabel scoreLabel;
    private final JLabel messageLabel;
    private JDialog victoryDialog;
    private JDialog gameOverDialog;

    public HonkMandView() {
        setTitle(HonkMandMessages.TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 500));
        setPreferredSize(new Dimension(600, 700));
        setLocationRelativeTo(null);
        setResizable(true);

        // Font moderno
        Font mainFont = new Font("Segoe UI", Font.BOLD, 22);
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 18);

        // Messaggio centrale
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(mainFont);
        messageLabel.setPreferredSize(new Dimension(400, 40));

        // Pannello info
        JPanel infoPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        infoPanel.setOpaque(false);
        levelLabel = new JLabel(HonkMandMessages.LEVEL_LABEL + "0");
        levelLabel.setFont(labelFont);
        scoreLabel = new JLabel(HonkMandMessages.SCORE_LABEL + "0");
        scoreLabel.setFont(labelFont);
        infoPanel.add(levelLabel);
        infoPanel.add(scoreLabel);

        // Pulsante start/restart
        startButton = new JButton(HonkMandMessages.START_BUTTON);
        startButton.setFont(labelFont);
        startButton.setFocusPainted(false);
        startButton.setPreferredSize(new Dimension(180, 40));

        // Pannello pulsanti colorati (GridBagLayout per adattabilità)
        JPanel colorPanel = new JPanel(new GridBagLayout());
        colorPanel.setOpaque(false);
        buttons = new HashMap<>();
        buttons.put(Colors.GREEN, new RoundButton(Color.GREEN));
        buttons.put(Colors.RED, new RoundButton(Color.RED));
        buttons.put(Colors.YELLOW, new RoundButton(Color.YELLOW));
        buttons.put(Colors.BLUE, new RoundButton(Colors.BLUE.getAwtColor()));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // spazio tra i bottoni
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = 0; gbc.gridy = 0;
        colorPanel.add(buttons.get(Colors.GREEN), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        colorPanel.add(buttons.get(Colors.RED), gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        colorPanel.add(buttons.get(Colors.YELLOW), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        colorPanel.add(buttons.get(Colors.BLUE), gbc);

        // Layout principale (GridBagLayout per adattabilità)
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(20, 20, 10, 20);
        mainPanel.add(messageLabel, c);
        c.gridy++;
        c.insets = new Insets(10, 20, 10, 20);
        c.fill = GridBagConstraints.BOTH; c.weighty = 1.0;
        mainPanel.add(colorPanel, c);
        c.gridy++; c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(infoPanel, c);
        c.gridy++;
        c.insets = new Insets(10, 20, 20, 20);
        mainPanel.add(startButton, c);
        setContentPane(mainPanel);

        // Dialoghi
        initDialogs();
        setVisible(true);
    }

    /** Pulsante rotondo custom con effetto glow, ridimensionabile e senza tagli. */
    private static class RoundButton extends JButton {
        private final Color baseColor;
        private boolean glowing = false;
        public RoundButton(Color color) {
            this.baseColor = color;
            setOpaque(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
        }
        public void setGlowing(boolean glowing) {
            this.glowing = glowing;
            repaint();
        }
        @Override
        public Insets getInsets() {
            // Margine interno per evitare tagli del glow
            return new Insets(18, 18, 18, 18);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int margin = 16;
            int size = Math.min(getWidth(), getHeight()) - 2 * margin;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;
            if (glowing) {
                g2.setColor(baseColor.brighter().brighter());
                g2.setStroke(new BasicStroke(18f));
                g2.drawOval(x - 9, y - 9, size + 18, size + 18);
            }
            g2.setColor(baseColor);
            g2.fillOval(x, y, size, size);
            g2.dispose();
        }
        @Override
        public Dimension getPreferredSize() {
            // Preferenza: quadrato grande, ma si adatta
            return new Dimension(120, 120);
        }
        @Override
        public Dimension getMinimumSize() {
            return new Dimension(60, 60);
        }
    }

    private void initDialogs() {
        // Dialogo di vittoria
        victoryDialog = new JDialog(this, HonkMandMessages.VICTORY_TITLE, true);
        victoryDialog.setSize(300, 200);
        victoryDialog.setLocationRelativeTo(this);
        JPanel victoryPanel = new JPanel();
        victoryPanel.setLayout(new BorderLayout());
        JLabel victoryLabel = new JLabel(HonkMandMessages.VICTORY_MESSAGE, SwingConstants.CENTER);
        victoryLabel.setFont(new Font("Arial", Font.BOLD, 18));
        victoryPanel.add(victoryLabel, BorderLayout.CENTER);
        JButton closeVictoryButton = new JButton(HonkMandMessages.CLOSE_GAME);
        closeVictoryButton.addActionListener(e -> System.exit(0));
        JPanel victoryButtonPanel = new JPanel();
        victoryButtonPanel.add(closeVictoryButton);
        victoryPanel.add(victoryButtonPanel, BorderLayout.SOUTH);
        victoryDialog.add(victoryPanel);
        // Dialogo di game over
        gameOverDialog = new JDialog(this, HonkMandMessages.GAME_OVER, true);
        gameOverDialog.setSize(300, 200);
        gameOverDialog.setLocationRelativeTo(this);
        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new BorderLayout());
        JLabel gameOverLabel = new JLabel(HonkMandMessages.GAME_OVER, SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gameOverPanel.add(gameOverLabel, BorderLayout.CENTER);
        JButton closeGameOverButton = new JButton(HonkMandMessages.CLOSE_GAME);
        closeGameOverButton.addActionListener(e -> System.exit(0));
        JPanel gameOverButtonPanel = new JPanel();
        gameOverButtonPanel.add(closeGameOverButton);
        gameOverPanel.add(gameOverButtonPanel, BorderLayout.SOUTH);
        gameOverDialog.add(gameOverPanel);
    }

    // --- Metodi pubblici per Controller ---

    public void updateLevel(int level) {
        levelLabel.setText(HonkMandMessages.LEVEL_LABEL + level);
    }
    public void updateScore(int score) {
        scoreLabel.setText(HonkMandMessages.SCORE_LABEL + score);
    }
    public void showMessage(String message, boolean isError) {
        messageLabel.setText(message);
        messageLabel.setForeground(isError ? Color.RED : Color.DARK_GRAY);
    }
    public void clearMessage() {
        messageLabel.setText("");
    }
    public void showVictoryDialog() {
        victoryDialog.setVisible(true);
    }
    public void showGameOverDialog() {
        gameOverDialog.setVisible(true);
    }
    public void setButtonsEnabled(boolean enabled) {
        for (RoundButton btn : buttons.values()) {
            btn.setEnabled(enabled);
        }
    }
    public void setGameActive(boolean active) {
        startButton.setText(active ? HonkMandMessages.RESTART_BUTTON : HonkMandMessages.START_BUTTON);
        startButton.setEnabled(!active);
    }
    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }
    public void addColorButtonListener(Colors colorId, ActionListener listener) {
        buttons.get(colorId).addActionListener(listener);
    }
    /**
     * Illumina un pulsante per la durata specificata (effetto glow).
     */
    public void lightUpButton(Colors colorId, int duration) {
        RoundButton btn = buttons.get(colorId);
        btn.setGlowing(true);
        Timer t = new Timer(duration, e -> btn.setGlowing(false));
        t.setRepeats(false);
        t.start();
    }
}
