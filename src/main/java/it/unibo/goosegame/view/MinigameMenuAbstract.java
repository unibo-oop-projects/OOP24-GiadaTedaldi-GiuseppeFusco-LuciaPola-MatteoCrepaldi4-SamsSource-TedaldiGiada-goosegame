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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public abstract class MinigameMenuAbstract extends JFrame {

    private JPanel cardPanel;
    private CardLayout cardLayout;
    private final JButton startButton, infoButton;
    private final JPanel mainPanel, infoPanel;
    private final Image background;
    private final JTextArea infoArea;

    private final ImageIcon startIcon = new ImageIcon(getClass().getResource("/play.png"));
    private final ImageIcon infoIcon = new ImageIcon(getClass().getResource("/i.png"));
    private final ImageIcon backIcon = new ImageIcon(getClass().getResource("/torna.png"));

    public MinigameMenuAbstract(String imgPath, String title, String infoMsg, JPanel gamePanel) {
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        background = new ImageIcon(getClass().getResource(imgPath)).getImage();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        setContentPane(cardPanel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleComponents();
            }
        });

        mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setOpaque(false);

        
        startButton = createButtonIcon(startIcon, 130, 110);
        infoButton = createButtonIcon(infoIcon, 25, 25);
        
        startButton.addActionListener(e -> cardLayout.show(cardPanel, "Game"));
        infoButton.addActionListener(e -> cardLayout.show(cardPanel, "Info"));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(startButton);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setOpaque(false);
        bottomPanel.add(infoButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);


        infoPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        infoPanel.setOpaque(false);

        infoArea = new JTextArea(infoMsg);
        infoArea.setWrapStyleWord(true);
        infoArea.setLineWrap(true);
        infoArea.setEditable(false);
        infoArea.setBackground(new Color(240, 240, 240, 255));
        infoArea.setFont(new Font("Dialog", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setBackground(new Color(240, 240, 240, 90));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 30, 50));

        JPanel southPanel = new JPanel();
        JButton backButton = createButtonIcon(backIcon, 25, 25);
        backButton.addActionListener((ActionEvent e) -> {
            cardLayout.show(cardPanel, "Menu");
        });
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 40,10));
        southPanel.setOpaque(false);
        southPanel.add(backButton, BorderLayout.WEST);

        infoPanel.add(scrollPane, BorderLayout.CENTER);
        infoPanel.add(southPanel, BorderLayout.SOUTH);

        cardPanel.add(mainPanel, "Menu");
        cardPanel.add(infoPanel, "Info");
        cardPanel.add(gamePanel, "Game");

        cardLayout.show(cardPanel, "Menu");
        setVisible(true);
    }
        

    private JButton createButtonIcon(ImageIcon image, int w, int h) {
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

    private void scaleComponents() {
        double scaleX = (double) getWidth()/600;
        double scaleY = (double) getHeight()/400;
        double scale = Math.min(scaleX, scaleY);

        int btnW = (int)(150 * scale);
        int btnH = (int)(110 * scale);
        startButton.setPreferredSize(new Dimension(btnW, btnH));
        Image scaledImage = startIcon.getImage().getScaledInstance(btnW, btnH, Image.SCALE_SMOOTH);
        startButton.setIcon(new ImageIcon(scaledImage));

        if(scale < 1) infoArea.setFont(new Font("Verdana", Font.BOLD, (int)(14*scale)));


        int iconSize = (int)(25*scale);
        infoButton.setPreferredSize(new Dimension(iconSize, iconSize));
        Image scaledIcon = infoIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        infoButton.setIcon(new ImageIcon(scaledIcon));

        infoPanel.setBorder(BorderFactory.createEmptyBorder((int)(100*scale), (int)(50*scale), (int)(30*scale), (int)(50*scale)));

        mainPanel.revalidate();
        mainPanel.repaint();
    }
   
}
