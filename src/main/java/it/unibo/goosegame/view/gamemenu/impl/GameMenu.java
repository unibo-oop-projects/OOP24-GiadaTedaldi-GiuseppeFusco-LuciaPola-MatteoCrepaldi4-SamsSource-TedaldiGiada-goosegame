package it.unibo.goosegame.view.gamemenu.impl;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.goosegame.model.gamemenu.api.MenuLogic;
import it.unibo.goosegame.model.gamemenu.impl.MenuLogicImpl;
import it.unibo.goosegame.view.gamemenu.GameInfo;
import it.unibo.goosegame.view.gamemenu.api.GameMenuInterface;

/**
 * The class GameMenu implements the view of the game's menu.
 */
public class GameMenu extends JFrame implements GameMenuInterface {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 400;
    private static final int MARGIN = 10;
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 110;
    private static final int ICON_SIZE = 35;
    private static final int FONT_SIZE = 14;
    private static final int COLUMN = 15;
    private static final int COLOR = 240;
    private static final long serialVersionUID = 1L;

    private final ImageIcon icon = new ImageIcon(getClass().getResource("/i.png"));
    private final ImageIcon startImage = new ImageIcon(GameMenu.class.getResource("/play.png"));
    private final transient Image background = new ImageIcon(getClass().getResource("/ImmagineMenu.png")).getImage();
    private transient MenuLogic logic;
    private JTextField playerNameField;
    private JLabel playerNameLabel;
    private JButton start, instruction;
    private JPanel  mainPanel, menuPanel;
    private CardLayout cardLayout;
    /**
     * Constructs the main game menu window and initializes its components.
     */
    @SuppressFBWarnings(value = "EI2", justification = "The GameMenu is intentionally passed as part of MVC structure.")
    public GameMenu() {
        super();
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setTitle("GIOCO DELL'OCA");
        super.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        super.setResizable(true);
        super.setLocationRelativeTo(null);
        initializeView();

        super.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                scaleComponents();
            }
        });
        super.setContentPane(mainPanel);
    }
    private void initializeView() {
        logic = new MenuLogicImpl(this);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        menuPanel = createMenuPanel();
        final GameInfo infoPanel = new GameInfo(this);
        infoPanel.initializeView();

        mainPanel.add(menuPanel, "menu");
        mainPanel.add(infoPanel, "info");
    }

    /**
     * @return return mainPanel.
     */
    private JPanel createMenuPanel() {
        final JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new BorderLayout(10, 10));
        panel.add(createCenterPanel(), BorderLayout.CENTER);
        panel.add(createBottomPanel(), BorderLayout.SOUTH);
        panel.setOpaque(true);
        return panel;
    }

    /**
     * @return return centerPanel.
     */
    private JPanel createCenterPanel() {
        final JPanel centerPanel = new JPanel(new GridBagLayout()); 
        centerPanel.setOpaque(false);
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
        start = createButtonIcon(startImage, BUTTON_WIDTH, BUTTON_HEIGHT);
        final JButton addPlayer = new JButton("Add Player");
        playerNameField = new JTextField(COLUMN);
        final JPanel panelPlayer = new JPanel(new FlowLayout());
        panelPlayer.add(playerNameField);
        panelPlayer.add(addPlayer);
        panelPlayer.setOpaque(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(panelPlayer, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(start, gbc);
        addPlayer.addActionListener(e -> logic.addPlayer());
        start.addActionListener(e -> logic.startGame());
        return centerPanel;
    }

    /**
     * @return return bottomPanel.
     */
    private JPanel createBottomPanel() {
        final JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        instruction = createButtonIcon(icon, ICON_SIZE, ICON_SIZE);
        final JPanel bottomLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomLeft.add(instruction);
        bottomLeft.setOpaque(false);

        playerNameLabel = new JLabel(" PLAYERS: ");
        playerNameLabel.setOpaque(true);
        playerNameLabel.setBackground(new Color(COLOR, COLOR, COLOR, 100));

        final JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomRight.add(playerNameLabel);
        bottomRight.setOpaque(false);

        bottomPanel.add(bottomLeft, BorderLayout.PAGE_END);
        bottomPanel.add(bottomRight, BorderLayout.NORTH);

        instruction.addActionListener(e -> logic.showInstructions());
        return bottomPanel;
    } 

    /**
     * {@inheritDoc}
     */
    @Override
    public final JButton createButtonIcon(final ImageIcon image, final int w, final int h) {
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
    * {@inheritDoc}
    */
    @Override
    public void showInstructions() {
        cardLayout.show(mainPanel, "info");
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void showMenu() {
        cardLayout.show(mainPanel, "menu");
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public String getPlayerName() {
        return playerNameField.getText();
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void updatePlayerField() {
        playerNameField.setText("");
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void updatePlayerLabel(final String text) {
        playerNameLabel.setText(text);
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

    private void scaleComponents() {
        final double scaleX = (double) getWidth() / WINDOW_WIDTH;
        final double scaleY = (double) getHeight() / WINDOW_HEIGHT;
        final double scale = Math.min(scaleX, scaleY);

        final int fontSize = (int) (FONT_SIZE * scale);
        final Font scaledFont = new Font("Dialog", Font.BOLD, fontSize);

        playerNameField.setFont(scaledFont);
        playerNameLabel.setFont(scaledFont);

        scaleFontRecursively(menuPanel, scaledFont);

        final int btnW = (int) (BUTTON_WIDTH * scale);
        final int btnH = (int) (BUTTON_HEIGHT * scale);
        start.setPreferredSize(new Dimension(btnW, btnH));
        final Image scaledImage = startImage.getImage().getScaledInstance(btnW, btnH, Image.SCALE_SMOOTH);
        start.setIcon(new ImageIcon(scaledImage));

        final int iconSize = (int) (ICON_SIZE * scale);
        instruction.setPreferredSize(new Dimension(iconSize, iconSize));
        final Image scaledIcon = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        instruction.setIcon(new ImageIcon(scaledIcon));

        menuPanel.revalidate();
        menuPanel.repaint();
    }

    /**
     * @param c The component to update.
     * @param font The font to apply.
     */
    private void scaleFontRecursively(final Component c, final Font font) {
        c.setFont(font);
        if (c instanceof Container container) {
            for (final Component child : container.getComponents()) {
                scaleFontRecursively(child, font);
            }
        }
    }
}
