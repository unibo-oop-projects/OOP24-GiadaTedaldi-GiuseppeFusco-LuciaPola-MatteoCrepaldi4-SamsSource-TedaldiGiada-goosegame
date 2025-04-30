package it.unibo.goosegame.model.minigames.RockPaperScissors.impl;

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

import it.unibo.goosegame.application.GameMenu;

public class RockPaperScissorsMenu extends JFrame {
    private JPanel cardPanel;
    private final JButton start;
    private final JButton info;
    private CardLayout cardLayout;
    private final JPanel panel, infoPanel;
    private final JTextArea infoText;
    private final RockPaperScissorsView view = new RockPaperScissorsView(); 
    private final RockPaperScissorsModel model = new RockPaperScissorsModel(); 
    @SuppressWarnings("unused")
    private final RockPaperScissorsController controller = new RockPaperScissorsController(model, view); 
    private final GameMenu gameMenu = new GameMenu();

    Image background = new ImageIcon(getClass().getResource("/RPS.png")).getImage();
    ImageIcon startImage = new ImageIcon(getClass().getResource("/play.png"));
    ImageIcon backImage = new ImageIcon(getClass().getResource("/torna.png"));
    ImageIcon infoImage  = new ImageIcon(getClass().getResource("/i.png"));

    public RockPaperScissorsMenu() {
        setTitle("Rock-Paper-Scissors");
        setSize(480, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        setContentPane(cardPanel);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleComponents();
            }
        });

        panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setOpaque(false);


        start = gameMenu.createButtonIcon(startImage, 130, 110);
        info = gameMenu.createButtonIcon(infoImage, 25, 25);
        start.addActionListener((ActionEvent e) -> startGame());  
        info.addActionListener((ActionEvent e) -> showGameInfo());
    
        JPanel centerpanel = new JPanel(new GridBagLayout());
        centerpanel.setOpaque(false);
        centerpanel.add(start);
        panel.add(centerpanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setOpaque(false);
        bottomPanel.add(info);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        infoPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        infoPanel.setOpaque(false);
        infoText = new JTextArea(
            """
            The game is based on choosing one of three possible moves: Rock, Paper, or Scissors.
            At the same time, the computer will also make its choice. The winner of each round is determined based on the combination of both moves.
            
            -> Rock beats Scissors
            -> Scissors beat Paper
            -> Paper beats Rock
            
            The first player to score three points wins the game."""
        );
        infoText.setEditable(false);
        infoText.setFont(new Font("Verdana", Font.PLAIN, 16));
        infoText.setBackground(new Color(240, 240, 240, 255));
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(infoText);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(150, 150, 50, 150));
        infoPanel.add(scrollPane, BorderLayout.CENTER);

        JButton back = gameMenu.createButtonIcon(backImage, 25, 25);
        back.setPreferredSize(new Dimension(40, 40));
        back.addActionListener((ActionEvent e) -> {
            cardLayout.show(cardPanel, "Menu");
        });

        JPanel southPanel = new JPanel();
        southPanel.setOpaque(false);
        southPanel.add(back);
        infoPanel.add(southPanel, BorderLayout.SOUTH);

        cardPanel.add(panel, "Menu");
        cardPanel.add(view, "Game");
        cardPanel.add(infoPanel, "Info");

        cardLayout.show(cardPanel, "Menu");

    }

    private void showGameInfo() {
        cardLayout.show(cardPanel, "Info");
    }
    private void startGame() {
            cardLayout.show(cardPanel, "Game");
    } 

    private void scaleComponents() {
        double scaleX = (double) getWidth()/400;
        double scaleY = (double) getHeight()/400;
        double scale = Math.min(scaleX, scaleY);

        infoPanel.setBorder(BorderFactory.createEmptyBorder((int)(100*scale), (int)(50*scale), (int)(30*scale), (int)(50*scale)));

        int btnW = (int)(150 * scale);
        int btnH = (int)(110 * scale);
        start.setPreferredSize(new Dimension(btnW, btnH));
        Image scaledImage = startImage.getImage().getScaledInstance(btnW, btnH, Image.SCALE_SMOOTH);
        start.setIcon(new ImageIcon(scaledImage));

        int iconSize = (int)(25*scale);
        info.setPreferredSize(new Dimension(iconSize, iconSize));
        Image scaledIcon = infoImage.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        info.setIcon(new ImageIcon(scaledIcon));

        panel.revalidate();
        panel.repaint();
    }
}