package it.unibo.goosegame.application;

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

public class GameMenu extends JFrame implements GameMenuView{
    MenuLogicImpl logic;
    private final Image background = new ImageIcon(getClass().getResource("/ImmagineMenu.png")).getImage();
    private final ImageIcon icon = new ImageIcon(getClass().getResource("/i.png"));
    private final ImageIcon startImage = new ImageIcon(getClass().getResource("/play.png"));
    private JTextField playerNameField;
    private JLabel playerNameLabel;
    private JButton start, instruction;
    private final JPanel  mainPanel, menuPanel;
    private final CardLayout cardLayout;
    private final GameInfo infoPanel;

    public GameMenu() {
        setTitle("GIOCO DELL'OCA");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        logic = new MenuLogicImpl(this);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        menuPanel = createMenuPanel();
        infoPanel = new GameInfo(this);

        mainPanel.add(menuPanel, "menu");
        mainPanel.add(infoPanel, "info");
        setContentPane(mainPanel);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleComponents();
            }
        });
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
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

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridBagLayout()); 
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        start = createButtonIcon(startImage, 150, 110);
        start.setIcon(startImage);
        JButton addPlayer = new JButton("Add Player");   
        playerNameField = new JTextField(15);
        JPanel panelPlayer = new JPanel(new FlowLayout());
     
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

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        instruction = createButtonIcon(icon, 25, 25);
        JPanel bottomLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomLeft.add(instruction);
        bottomLeft.setOpaque(false);

        playerNameLabel = new JLabel(" PLAYERS: ");
        playerNameLabel.setOpaque(true);
        playerNameLabel.setBackground(new Color(240,240,240,100));

        JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomRight.add(playerNameLabel);
        bottomRight.setOpaque(false);
        bottomRight.setSize(new Dimension(200, 500));

        bottomPanel.add(bottomLeft, BorderLayout.PAGE_END);
        bottomPanel.add(bottomRight, BorderLayout.NORTH);

        instruction.addActionListener(e -> logic.showInstructions());
        return bottomPanel;
    }
        
    public JButton createButtonIcon(ImageIcon image, int w, int h) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(w,h));
        button.setIcon(image);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = button.getWidth();
                int height = button.getHeight();
                if(width > 0 && height > 0) {
                    Image scaledImage = image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(scaledImage));
                }
            }
        });
        return button;
    }

    public void showInstructions() {
        cardLayout.show(mainPanel, "info");
    }

    public void showMenu() {
        cardLayout.show(mainPanel, "menu");
    }

    public String getPlayerName() {
        return playerNameField.getText();
    }

    public void updatePlayerField() {
        playerNameField.setText("");
    }

    public void updatePlayerLabel(String text) {
        playerNameLabel.setText(text);
    }

    private void scaleComponents() {
        double scaleX = (double) getWidth()/500;
        double scaleY = (double) getHeight()/400;
        double scale = Math.min(scaleX, scaleY);

        int fontSize = (int) (14*scale);
        Font scaledFont = new Font("Dialog", Font.BOLD, fontSize);

        playerNameField.setFont(scaledFont);
        playerNameLabel.setFont(scaledFont);

        scaleFontRecursively(menuPanel, scaledFont);

        int btnW = (int)(150 * scale);
        int btnH = (int)(110 * scale);
        start.setPreferredSize(new Dimension(btnW, btnH));
        Image scaledImage = startImage.getImage().getScaledInstance(btnW, btnH, Image.SCALE_SMOOTH);
        start.setIcon(new ImageIcon(scaledImage));

        int iconSize = (int)(25*scale);
        instruction.setPreferredSize(new Dimension(iconSize, iconSize));
        Image scaledIcon = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        instruction.setIcon(new ImageIcon(scaledIcon));

        menuPanel.revalidate();
        menuPanel.repaint();
    }

    private void scaleFontRecursively(Component c, Font font) {
        c.setFont(font);
        if(c instanceof Container container) {
            for(Component child : container.getComponents()) {
                scaleFontRecursively(child, font);
            }
        }
    }
}
