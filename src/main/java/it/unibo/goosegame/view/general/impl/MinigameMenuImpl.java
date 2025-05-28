package it.unibo.goosegame.view.general.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.goosegame.view.general.api.MinigameMenu;

/**
 * This is an abstract class representing a menu for the mini-games.
 */
public abstract class MinigameMenuImpl extends JFrame implements MinigameMenu {

    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;
    private static final int BUTTON_WIDTH = 130;
    private static final int BUTTON_HEIGHT = 110;
    private static final int ICON_SIZE = 35;
    private static final long serialVersionUID = 1L;
    private final JPanel mainPanel;
    private final Image background;

    private final ImageIcon startIcon = new ImageIcon(MinigameMenuImpl.class.getResource("/play.png"));
    private final ImageIcon infoIcon = new ImageIcon(MinigameMenuImpl.class.getResource("/i.png"));
    private final String infoMsg;

    private JButton startButton;
    private JButton infoButton;

    /**
     * Constructor for MinigameMenuAbstract.
     * Initializes the menu with a background image, title, instructions message, gamePanel and action listener.
     * @param imgPath the path of the background image.
     * @param title the title of the menu window.
     * @param infoMsg the information message displayed in the info section.
     * @param al the listener to start the game.
     */
    @SuppressFBWarnings(
        value = "ConstructorCallsOverridableMethod", 
        justification = "createMainPanel is not overridden; this usage is safe in this context.")
    public MinigameMenuImpl(final String imgPath, final String title, 
            final String infoMsg) {
        super(title);
        background = new ImageIcon(MinigameMenuImpl.class.getResource(imgPath)).getImage();
        this.infoMsg = infoMsg;

        mainPanel = createMainPanel();
        super.setContentPane(mainPanel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeView() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                scaleComponents();
            }
        });
        setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP", 
        justification = "Access to the button is necessary to add external ActionListeners.")
    @Override
    public final JButton getStartButton() {
        return startButton;
    }

    /**
     * @param al the Actionerlistener
     * @return mainPanel
     */
    private JPanel createMainPanel() {
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
        infoButton.addActionListener(e -> JOptionPane.showInternalMessageDialog(null, infoMsg));

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

        final int iconSize = (int) (ICON_SIZE * scale);
        infoButton.setPreferredSize(new Dimension(iconSize, iconSize));
        final Image scaledIcon = infoIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        infoButton.setIcon(new ImageIcon(scaledIcon));

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
