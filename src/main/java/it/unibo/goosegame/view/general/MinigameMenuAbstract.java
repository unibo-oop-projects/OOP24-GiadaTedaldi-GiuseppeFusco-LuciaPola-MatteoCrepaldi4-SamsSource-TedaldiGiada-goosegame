package it.unibo.goosegame.view.general;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This is an abstract class representing a menu for the mini-games.
 */
public abstract class MinigameMenuAbstract extends JFrame {

    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;
    private static final int BUTTON_WIDTH = 130;
    private static final int BUTTON_HEIGHT = 110;
    private static final int ICON_SIZE = 25;
    private static final int FONT_SIZE = 14;
    private static final int COLOR = 240;
    private static final int COLOR_WHITE = 255;
    private static final int TOP = 100;
    private static final int BOTTOM = 30;
    private static final int BOTTOM1 = 40;
    private static final int LEFT_RIGHT = 50;
    private static final long serialVersionUID = 1L;
    private final JPanel cardPanel;
    private final CardLayout cardLayout;
    private final JPanel mainPanel, infoPanel;
    private final Image background;

    private final ImageIcon startIcon = new ImageIcon(MinigameMenuAbstract.class.getResource("/play.png"));
    private final ImageIcon infoIcon = new ImageIcon(MinigameMenuAbstract.class.getResource("/i.png"));
    private final ImageIcon backIcon = new ImageIcon(MinigameMenuAbstract.class.getResource("/torna.png"));

    private JButton startButton;
    private JButton infoButton;
    private JTextArea infoArea;

    /**
     * Constructor for MinigameMenuAbstract.
     * Initializes the menu with a background image, title, instructions message, gamePanel and action listener.
     * @param imgPath the path of the background image.
     * @param title the title of the menu window.
     * @param infoMsg the information message displayed in the info section.
     * @param gamePanel the panel representing the game.
     * @param al the listener to start the game.
     */
    @SuppressWarnings("ConstructorCallsOverridableMethod")
    public MinigameMenuAbstract(final String imgPath, final String title, 
            final String infoMsg, final JPanel gamePanel, final ActionListener al) {
        super(title);
        background = new ImageIcon(MinigameMenuAbstract.class.getResource(imgPath)).getImage();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        mainPanel = createMainPanel(al);
        infoPanel = createInfoPanel(infoMsg);

        cardPanel.add(mainPanel, "Menu");
        cardPanel.add(infoPanel, "Info");
        cardPanel.add(gamePanel, "Game");

    }

    /**
     * Initializes and sets up the components of the minigames's menu view.
     */
    public void initializeView() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setContentPane(cardPanel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                scaleComponents();
            }
        });
        cardLayout.show(cardPanel, "Menu");
        setVisible(true);
    }

    /**
     * @return the start button
     */
    @SuppressWarnings("EI_EXPOSE_REP")
    public final JButton getStartButton() {
        return startButton;
    }

    /**
     * @return the start button
     */
    @SuppressWarnings("EI_EXPOSE_REP")
    public JPanel getCardPanel() {
        return cardPanel;
    }

    /**
     * @param al the Actionerlistener
     * @return mainPanel
     */
    private JPanel createMainPanel(final ActionListener al) {
        final JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setOpaque(false);

        startButton = createButtonIcon(startIcon, BUTTON_WIDTH, BUTTON_HEIGHT);
        infoButton = createButtonIcon(infoIcon, ICON_SIZE, ICON_SIZE);
        startButton.addActionListener(al);
        startButton.addActionListener(e -> cardLayout.show(cardPanel, "Game"));
        infoButton.addActionListener(e -> cardLayout.show(cardPanel, "Info"));

        final JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(startButton);
        panel.add(centerPanel, BorderLayout.CENTER);

        final JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setOpaque(false);
        bottomPanel.add(infoButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * @param infoMsg The information about the game to display.
     * @return infoPanel 
     */
    private JPanel createInfoPanel(final String infoMsg) {
        final JPanel info = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        info.setOpaque(false);

        infoArea = new JTextArea(infoMsg);
        infoArea.setWrapStyleWord(true);
        infoArea.setLineWrap(true);
        infoArea.setEditable(false);
        infoArea.setBackground(new Color(COLOR, COLOR, COLOR, COLOR_WHITE));
        infoArea.setFont(new Font("Verdana", Font.BOLD, FONT_SIZE));
        final JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setBackground(new Color(COLOR, COLOR, COLOR, 90));
        info.setBorder(BorderFactory.createEmptyBorder(TOP, LEFT_RIGHT, BOTTOM, LEFT_RIGHT));

        final JPanel southPanel = new JPanel();
        final JButton backButton = createButtonIcon(backIcon, ICON_SIZE, ICON_SIZE);
        backButton.addActionListener((ActionEvent e) -> {
            cardLayout.show(cardPanel, "Menu");
        });
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, BOTTOM1, 10));
        southPanel.setOpaque(false);
        southPanel.add(backButton, BorderLayout.WEST);

        info.add(scrollPane, BorderLayout.CENTER);
        info.add(southPanel, BorderLayout.SOUTH);
        return info;
    }

    /**
     * @param image the icon image for the button.
     * @param w the width of the button.
     * @param h the height of the button.
     * @return the created button.
     */
    private JButton createButtonIcon(final ImageIcon image, final int w, final int h) {
        final JButton button = new JButton();
        button.setPreferredSize(new Dimension(w, h));
        button.setIcon(image);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                final int width = button.getWidth();
                final int height = button.getHeight();
                if (width > 0 && height > 0) {
                    final Image scaledImage = image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(scaledImage));
                }
            }
        });
        return button;
    }

    /**
     * scales the components according to the window size.
     */
    private void scaleComponents() {
        final double scaleX = (double) getWidth() / WINDOW_WIDTH;
        final double scaleY = (double) getHeight() / WINDOW_HEIGHT;
        final double scale = Math.min(scaleX, scaleY);

        final int btnW = (int) (BUTTON_WIDTH * scale);
        final int btnH = (int) (BUTTON_HEIGHT * scale);
        startButton.setPreferredSize(new Dimension(btnW, btnH));
        final Image scaledImage = startIcon.getImage().getScaledInstance(btnW, btnH, Image.SCALE_SMOOTH);
        startButton.setIcon(new ImageIcon(scaledImage));

        if (scale < 1) {
            infoArea.setFont(new Font("Verdana", Font.BOLD, (int) (FONT_SIZE * scale)));
        }

        final int iconSize = (int) (ICON_SIZE * scale);
        infoButton.setPreferredSize(new Dimension(iconSize, iconSize));
        final Image scaledIcon = infoIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        infoButton.setIcon(new ImageIcon(scaledIcon));

        infoPanel.setBorder(BorderFactory.createEmptyBorder((int) (TOP * scale), 
            (int) (LEFT_RIGHT * scale), (int) (BOTTOM * scale), (int) (LEFT_RIGHT * scale)));

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
