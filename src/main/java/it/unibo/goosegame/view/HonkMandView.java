package it.unibo.goosegame.view;

import it.unibo.goosegame.utilities.Colors;
import it.unibo.goosegame.utilities.HonkMandMessages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EnumMap;
import java.util.Map;

public final class HonkMandView extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int BG_COLOR_B = 245;
    private static final int BG_COLOR_G = 245;
    private static final int BG_COLOR_R = 245;
    private static final int BUTTON_INSET = 20;
    private static final int MAIN_PANEL_BOTTOM_INSET = 20;
    private static final int MAIN_PANEL_MID_INSET = 10;
    private static final int MAIN_PANEL_SIDE_INSET = 20;
    private static final int MAIN_PANEL_TOP_INSET = 20;
    private static final int MESSAGE_LABEL_HEIGHT = 40;
    private static final int MESSAGE_LABEL_WIDTH = 400;
    private static final int ROUND_BUTTON_GLOW_OFFSET = 9;
    private static final int ROUND_BUTTON_GLOW_STROKE = 18;
    private static final int ROUND_BUTTON_INSETS = 18;
    private static final int ROUND_BUTTON_MIN_SIZE = 60;
    private static final int ROUND_BUTTON_PREFERRED_SIZE = 120;
    private static final int SIDE_PANEL_WIDTH = 60;
    private static final int START_BUTTON_HEIGHT = 40;
    private static final int START_BUTTON_WIDTH = 180;

    private final Map<Colors, RoundButton> buttons;
    private final JButton startButton;
    private final JLabel levelLabel;
    private final JLabel scoreLabel;
    private final JLabel messageLabel;
    private JFrame frameRef; // Reference to the main frame

    /**
     * Constructs a HonkMandView and initializes the UI components.
     */
    public HonkMandView() {
        setLayout(new BorderLayout());
        setBackground(new Color(BG_COLOR_R, BG_COLOR_G, BG_COLOR_B));

        // Side gray panels
        final JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(SIDE_PANEL_WIDTH, 0));
        leftPanel.setBackground(Color.LIGHT_GRAY);
        final JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(SIDE_PANEL_WIDTH, 0));
        rightPanel.setBackground(Color.LIGHT_GRAY);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        // Modern fonts
        final Font mainFont = new Font("Segoe UI", Font.BOLD, 22);
        final Font labelFont = new Font("Segoe UI", Font.PLAIN, 18);

        // Central message
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(mainFont);
        messageLabel.setPreferredSize(new Dimension(MESSAGE_LABEL_WIDTH, MESSAGE_LABEL_HEIGHT));

        // Info panel
        final JPanel infoPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        infoPanel.setOpaque(false);
        levelLabel = new JLabel(HonkMandMessages.LEVEL_LABEL + "0");
        levelLabel.setFont(labelFont);
        scoreLabel = new JLabel(HonkMandMessages.SCORE_LABEL + "0");
        scoreLabel.setFont(labelFont);
        infoPanel.add(levelLabel);
        infoPanel.add(scoreLabel);

        // Start/restart button
        startButton = new JButton(HonkMandMessages.START_BUTTON);
        startButton.setFont(labelFont);
        startButton.setFocusPainted(false);
        startButton.setPreferredSize(new Dimension(START_BUTTON_WIDTH, START_BUTTON_HEIGHT));

        // Colored buttons panel (GridBagLayout for adaptability)
        final JPanel colorPanel = new JPanel(new GridBagLayout());
        colorPanel.setOpaque(false);
        buttons = new EnumMap<>(Colors.class);
        buttons.put(Colors.GREEN, new RoundButton(Color.GREEN));
        buttons.put(Colors.RED, new RoundButton(Color.RED));
        buttons.put(Colors.YELLOW, new RoundButton(Color.YELLOW));
        buttons.put(Colors.BLUE, new RoundButton(Colors.BLUE.getAwtColor()));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(BUTTON_INSET, BUTTON_INSET, BUTTON_INSET, BUTTON_INSET); // space between buttons
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        colorPanel.add(buttons.get(Colors.GREEN), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        colorPanel.add(buttons.get(Colors.RED), gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        colorPanel.add(buttons.get(Colors.YELLOW), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        colorPanel.add(buttons.get(Colors.BLUE), gbc);

        // Central layout (GridBagLayout for adaptability)
        final JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(MAIN_PANEL_TOP_INSET, MAIN_PANEL_SIDE_INSET, MAIN_PANEL_MID_INSET, MAIN_PANEL_SIDE_INSET);
        mainPanel.add(messageLabel, c);
        c.gridy++;
        c.insets = new Insets(MAIN_PANEL_MID_INSET, MAIN_PANEL_SIDE_INSET, MAIN_PANEL_MID_INSET, MAIN_PANEL_SIDE_INSET);
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0;
        mainPanel.add(colorPanel, c);
        c.gridy++;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(infoPanel, c);
        c.gridy++;
        c.insets = new Insets(MAIN_PANEL_MID_INSET, MAIN_PANEL_SIDE_INSET, MAIN_PANEL_BOTTOM_INSET, MAIN_PANEL_SIDE_INSET);
        mainPanel.add(startButton, c);
        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Sets the reference to the main JFrame.
     * @param frame the main JFrame
     */
    public void setFrameRef(final JFrame frame) {
        this.frameRef = frame;
    }

    /**
     * Updates the level label.
     * @param level the current level
     */
    public void updateLevel(final int level) {
        levelLabel.setText(HonkMandMessages.LEVEL_LABEL + level);
    }

    /**
     * Updates the score label.
     * @param score the current score
     */
    public void updateScore(final int score) {
        scoreLabel.setText(HonkMandMessages.SCORE_LABEL + score);
    }

    /**
     * Shows a message in the central label.
     * @param message the message to show
     * @param isError true if the message is an error, false otherwise
     */
    public void showMessage(final String message, final boolean isError) {
        messageLabel.setText(message);
        messageLabel.setForeground(isError ? Color.RED : Color.DARK_GRAY);
    }

    /**
     * Clears the central message label.
     */
    public void clearMessage() {
        messageLabel.setText("");
    }

    /**
     * Shows the end-of-game panel.
     * @param hasWon true if the player has won, false otherwise
     */
    public void showGameOverPanel(final boolean hasWon) {
        if (frameRef == null) {
            return;
        }
        final String msg = hasWon ? "You Win!" : "You Lose!";
        frameRef.getContentPane().removeAll();
        frameRef.getContentPane().add(new GameEndPanel(msg, frameRef::dispose, "HonkMand", hasWon), BorderLayout.CENTER);
        frameRef.revalidate();
        frameRef.repaint();
    }

    /**
     * Enables or disables the colored buttons.
     * @param enabled true to enable, false to disable
     */
    public void setButtonsEnabled(final boolean enabled) {
        for (final RoundButton btn : buttons.values()) {
            btn.setEnabled(enabled);
        }
    }

    /**
     * Sets the game as active or inactive (changes start button text and state).
     * @param active true if the game is active, false otherwise
     */
    public void setGameActive(final boolean active) {
        startButton.setText(active ? HonkMandMessages.RESTART_BUTTON : HonkMandMessages.START_BUTTON);
        startButton.setEnabled(!active);
    }

    /**
     * Adds an ActionListener to the start button.
     * @param listener the ActionListener to add
     */
    public void addStartButtonListener(final ActionListener listener) {
        startButton.addActionListener(listener);
    }

    /**
     * Adds an ActionListener to a colored button.
     * @param colorId the color of the button
     * @param listener the ActionListener to add
     */
    public void addColorButtonListener(final Colors colorId, final ActionListener listener) {
        buttons.get(colorId).addActionListener(listener);
    }

    /**
     * Lights up a button for the specified duration (glow effect).
     * @param colorId the color of the button
     * @param duration the duration in milliseconds
     */
    public void lightUpButton(final Colors colorId, final int duration) {
        final RoundButton btn = buttons.get(colorId);
        btn.setGlowing(true);
        final Timer t = new Timer(duration, e -> btn.setGlowing(false));
        t.setRepeats(false);
        t.start();
    }

    /**
     * Custom round button with glow effect, resizable and without clipping.
     */
    private static class RoundButton extends JButton {
        private static final long serialVersionUID = 1L;
        private final Color baseColor;
        private boolean glowing;

        /**
         * Constructs a RoundButton with the given color.
         * @param color the base color
         */
        RoundButton(final Color color) {
            this.baseColor = color;
            setOpaque(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
        }

        /**
         * Sets the glowing state of the button.
         * @param glowing true to enable glow, false to disable
         */
        public void setGlowing(final boolean glowing) {
            this.glowing = glowing;
            repaint();
        }

        @Override
        public Insets getInsets() {
            // Internal margin to avoid glow clipping
            return new Insets(ROUND_BUTTON_INSETS, ROUND_BUTTON_INSETS, ROUND_BUTTON_INSETS, ROUND_BUTTON_INSETS);
        }

        @Override
        protected void paintComponent(final Graphics g) {
            final Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            final int margin = 16;
            final int size = Math.min(getWidth(), getHeight()) - 2 * margin;
            final int x = (getWidth() - size) / 2;
            final int y = (getHeight() - size) / 2;
            if (glowing) {
                g2.setColor(baseColor.brighter().brighter());
                g2.setStroke(new BasicStroke(ROUND_BUTTON_GLOW_STROKE));
                g2.drawOval(x - ROUND_BUTTON_GLOW_OFFSET, y - ROUND_BUTTON_GLOW_OFFSET,
                    size + ROUND_BUTTON_GLOW_STROKE, size + ROUND_BUTTON_GLOW_STROKE);
            }
            // Cambia colore se disabilitato
            if (!isEnabled()) {
                g2.setColor(baseColor.darker().darker());
            } else {
                g2.setColor(baseColor);
            }
            g2.fillOval(x, y, size, size);
            g2.dispose();
        }

        @Override
        public Dimension getPreferredSize() {
            // Preference: large square, but adapts
            return new Dimension(ROUND_BUTTON_PREFERRED_SIZE, ROUND_BUTTON_PREFERRED_SIZE);
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension(ROUND_BUTTON_MIN_SIZE, ROUND_BUTTON_MIN_SIZE);
        }

        // Blocco fisico degli eventi mouse se disabilitato
        @Override
        protected void processMouseEvent(final MouseEvent e) {
            if (!isEnabled()) {
                return;
            }
            super.processMouseEvent(e);
        }
    }
}
