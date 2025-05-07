package it.unibo.goosegame.view.general;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
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

    /**
     * The size of the font of the buttons.
     */
    private static final int BUTTON_FONT_SIZE = 25;
    /**
     * The height of the buttons.
     */
    private static final int BUTTON_HEIGHT = 50;
    /**
     * The width of the buttons.
     */
    private static final int BUTTON_WIDTH = 200;
    /**
     * The size of the title label.
     */
    private static final int TITLE_LABEL_SIZE = 50;
    /**
     * The size of the grid. (4x4).
     * This is the default size for the minigame menu.
     */
    private static final int SIZE = 4;

    /**
     * Constructor.
     * @param imgPath the path of the image background
     * @param title the title of the frame
     * @param infoMsg the message to show in the instructions
     * @param al the action listener for the start button
     */
    public MinigameMenuAbstract(final String imgPath, final String title, final String infoMsg, final ActionListener al) {
        final BackgroundPanel background = new BackgroundPanel(imgPath);
        background.setLayout(new GridLayout(SIZE, SIZE));
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, TITLE_LABEL_SIZE));
        titleLabel.setForeground(Color.RED);
        background.add(titleLabel);
        final JButton startButton = new JButton("Start Game");
        changeBtnStyle(startButton);
        startButton.addActionListener(al);
        startButton.addActionListener(e -> {
            this.dispose();
        });
        final JButton instrButton = new JButton("Instructions");
        changeBtnStyle(instrButton);
        instrButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, infoMsg);
        });
        background.add(startButton);
        background.add(instrButton);
        this.add(background);
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        this.setSize(sw / 2, sh / 2);
        this.setMinimumSize(new Dimension(sw / 3, sh / 3));
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void changeBtnStyle(final JButton button) {
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(Color.RED);
        button.setFont(new Font("Arial", Font.BOLD, BUTTON_FONT_SIZE));
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        BackgroundPanel(final String path) {
            try {
                backgroundImage = new ImageIcon(path).getImage();
            } catch (final Exception e) {
                JOptionPane.showMessageDialog(this, "Image not found: " + path);
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
