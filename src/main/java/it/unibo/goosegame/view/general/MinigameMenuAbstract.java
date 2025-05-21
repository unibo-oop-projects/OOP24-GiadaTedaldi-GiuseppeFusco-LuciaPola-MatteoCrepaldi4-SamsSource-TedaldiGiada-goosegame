package it.unibo.goosegame.view.general;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

/**
 * The MinigameMenuAbstract class is an abstract class that represents a menu for a minigame.
 * It provides a background image, a title, and buttons to start the game and show instructions.
 */
public abstract class MinigameMenuAbstract extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final int BUTTON_FONT_SIZE = 25;
    private static final int BUTTON_HEIGHT = 50;
    private static final int BUTTON_WIDTH = 200;
    private static final int TITLE_LABEL_SIZE = 50;
    private static final int SIZE = 4;

    /**
     * Constructor for MinigameMenuAbstract.
     * Initializes the menu with a background image, title, instructions message, and action listener.
     *
     * @param imgPath  the path to the background image
     * @param title    the title of the menu
     * @param infoMsg  the instructions message
     * @param al       the action listener for button clicks
     */
    @SuppressFBWarnings(
        value = "MC_OVERRIDABLE_METHOD_CALL_IN_CONSTRUCTOR", 
        justification = "The dispose() call is wrapped in a lambda and is safe."
    )
    public MinigameMenuAbstract(final String imgPath, final String title, final String infoMsg, final ActionListener al) {
        super();
        final BackgroundPanel background = new BackgroundPanel(imgPath);
        background.setLayout(new GridLayout(SIZE, SIZE));

        super.setTitle(title);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);

        final JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, TITLE_LABEL_SIZE));
        titleLabel.setForeground(Color.RED);
        background.add(titleLabel);

        final JButton startButton = new JButton("Start Game");
        changeBtnStyle(startButton);
        startButton.addActionListener(al);
        startButton.addActionListener(e -> dispose());

        final JButton instrButton = new JButton("Instructions");
        changeBtnStyle(instrButton);
        instrButton.addActionListener(e -> JOptionPane.showMessageDialog(this, infoMsg));

        background.add(startButton);
        background.add(instrButton);

        super.add(background);

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();

        super.setSize(sw / 2, sh / 2);
        super.setMinimumSize(new Dimension(sw / 3, sh / 3));
        super.setVisible(true);
        super.setLocationRelativeTo(null);
    }

    private void changeBtnStyle(final JButton button) {
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(Color.RED);
        button.setFont(new Font("Arial", Font.BOLD, BUTTON_FONT_SIZE));
    }

    private static class BackgroundPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private transient Image backgroundImage;

        BackgroundPanel(final String path) {
            if (path != null && !path.isBlank()) {
                final ImageIcon icon = new ImageIcon(path);
                backgroundImage = icon.getImage();
                if (backgroundImage == null) {
                    JOptionPane.showMessageDialog(this, "Image not found or invalid: " + path);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid image path: " + path);
            }
        }

        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
