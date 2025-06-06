package it.unibo.goosegame.view.minigames.hangman.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import static java.util.Arrays.stream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.goosegame.controller.minigames.hangman.api.HangmanController;
import it.unibo.goosegame.view.minigames.hangman.api.HangmanView;

/**
 * A class that extends the hangman game's view.
 */
public class HangmanViewImpl extends JFrame implements HangmanView {
    private static final int MAX_ATTEMPTS = 5;
    private static final long serialVersionUID = 1L;
    private static final int FONT_SIZE = 34;
    private static final int DIMENSION = 200;
    private static final int COLOR_WHITE = 255;
    private static final int WINDOW_WIDTH = 650;
    private static final int WINDOW_HEIGHT = 400;
    private static final int BUTTONW = 55;
    private static final int BUTTONH = 25;
    private final JPanel panel;

    private JLabel wordLabel; 
    private JLabel imageLabel;
    private JPanel keyboardPanel;
    private transient HangmanController controller;
    private int currentAttempts;
    private boolean image;

    /**
     * Constructor of the class. Initializes the view of Hangman game.
     */
    @SuppressFBWarnings(
        value = "OverridableMethodCallInConstructor",
        justification = "Calling methods in the constructor is safe since no overridable methods run before full initialization"
    )
    public HangmanViewImpl() {
        super("Hangman");
        this.currentAttempts = MAX_ATTEMPTS;
        this.panel = new JPanel();
        this.panel.setLayout(new BorderLayout());
        this.panel.setOpaque(false);
        super.setContentPane(panel);
        super.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                scaleComponents();
            }
        });
        super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        super.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        super.setLocationRelativeTo(null);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeView() {
        final JPanel mainPanel = createMainPanel();
        final JPanel rightPanel = createImagePanel();
        this.panel.add(mainPanel, BorderLayout.CENTER);
        this.panel.add(rightPanel, BorderLayout.EAST);
        super.setVisible(true);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateWord(final String word) {
        this.wordLabel.setText(word);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateImage(final int attempts) {
        this.currentAttempts = attempts;
        if (attempts < MAX_ATTEMPTS && !this.image) {
            this.image = true;
            this.imageLabel.setVisible(true);
        }
        final java.net.URL imgURL = HangmanViewImpl.class.getResource("/img/minigames/hangman/hangman" + attempts + ".png");
        if (imgURL != null) {
            final ImageIcon original = new ImageIcon(imgURL);
            final int w = this.imageLabel.getWidth();
            final int h = this.imageLabel.getHeight();
            if (w > 0 && h > 0) {
                final Image scaled = original.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
                this.imageLabel.setIcon(new ImageIcon(scaled));
            }
        } else {
            this.imageLabel.setIcon(null);
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void disableButton(final char car) {
        stream(this.keyboardPanel.getComponents())
            .filter(JButton.class::isInstance)
            .map(JButton.class::cast)
            .filter(button -> button.getText().equals(String.valueOf(car)))
            .findFirst()
            .ifPresent(button -> button.setEnabled(false));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void disableAllButton() {
        stream(this.keyboardPanel.getComponents())
            .filter(JButton.class::isInstance)
            .map(JButton.class::cast)
            .forEach(button -> button.setEnabled(false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setController(final HangmanController controller) {
       this.controller = controller;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void enableAllButton() {
        stream(this.keyboardPanel.getComponents())
            .filter(JButton.class::isInstance)
            .map(JButton.class::cast)
            .forEach(button -> button.setEnabled(true));
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(
        value = "EI2", 
        justification = "Safe usage within UI context; no subclass is expected to override this behavior.")
    @Override
    public void dispose() {
        super.dispose();
    }

    private JPanel createMainPanel() {
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        this.wordLabel = new JLabel("", SwingConstants.CENTER);
        this.wordLabel.setFont(new Font("Verdana", Font.BOLD, FONT_SIZE));
        this.wordLabel.setForeground(Color.ORANGE);
        this.wordLabel.setAlignmentX(CENTER_ALIGNMENT);

        final Component gap = Box.createVerticalStrut(15);

        this.keyboardPanel = createKeyboardPanel();

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(this.wordLabel);
        mainPanel.add(gap);
        mainPanel.add(this.keyboardPanel);
        mainPanel.add(Box.createVerticalGlue());

        return mainPanel;
    }

    private JPanel createImagePanel() {
        this.imageLabel = new JLabel();
        this.imageLabel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                updateImage(currentAttempts);
            }
        });
        this.imageLabel.setVisible(false);
        this.imageLabel.setPreferredSize(new Dimension(DIMENSION, DIMENSION));
        this.imageLabel.setAlignmentX(CENTER_ALIGNMENT);

        final JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setOpaque(false);

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        rightPanel.add(this.imageLabel, gbc);
        return rightPanel;
    }

    private JPanel createKeyboardPanel() {
        final JPanel keyPanel  = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            final JButton button = new JButton(String.valueOf(letter));
            button.setBackground(new Color(COLOR_WHITE, COLOR_WHITE, 180));
            button.setForeground(Color.ORANGE);
            final char selectedLetter = letter;
            button.addActionListener(e -> {
                if (this.controller != null) {
                    this.controller.onLetterPressed(selectedLetter);
                    this.disableButton(selectedLetter);
                }
            });
            keyPanel.add(button);
        }
        return keyPanel;
    }

    private void scaleComponents() {
        final double scaleX = (double) getWidth() / WINDOW_WIDTH;
        final double scaleY = (double) getHeight() / WINDOW_HEIGHT;
        final double scale = Math.max(0.5, Math.min(scaleX, scaleY));

        final int fontSize = (int) (FONT_SIZE * scale);
        final int fontKeyboard = (int) (12 * scale);
        this.wordLabel.setFont(new Font("Verdana", Font.BOLD, fontSize));

        final int imageW = Math.max(100, (int) (DIMENSION * scale));
        final int imageH = Math.max(100, (int) (DIMENSION * scale));
        this.imageLabel.setPreferredSize(new Dimension(imageW, imageH));
        this.imageLabel.revalidate();
        this.updateImage(this.currentAttempts);

        final int btw = (int) (BUTTONW * scale);
        final int bth = (int) (BUTTONH * scale);

        for (final Component c : this.keyboardPanel.getComponents()) {
            if (c instanceof JButton button) {
                button.setPreferredSize(new Dimension(btw, bth));
                button.setFont(new Font("Verdana", Font.BOLD, fontKeyboard));
            }
        }
        this.keyboardPanel.revalidate();
        this.keyboardPanel.repaint();
        this.revalidate();
        this.repaint();
    }
}
