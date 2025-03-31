package it.unibo.goosegame.application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.SwingUtilities;

public class GameMenu extends JFrame {
    private Image background;
    private ImageIcon icon;
    private JTextField playerNameField;
    private JLabel playerNameLabel;
    public GameMenu() {
        setTitle("GIOCO DELL'OCA");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(440,415));

        icon = new ImageIcon("C:/Users/Utente LENOVO/OneDrive/Desktop/OOP24-goosegame/info.jpg/");
        background = new ImageIcon("C:/Users/Utente LENOVO/OneDrive/Desktop/OOP24-goosegame/ImmagineMenù.png").getImage();

        setContentPane(createMainPanel());
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setLayout(new BorderLayout(10, 10));
        panel.setOpaque(true);
        panel.add(createCenterPanel(), BorderLayout.CENTER);
        panel.add(createBottomPanel(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridBagLayout()); 
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton start = new JButton("INIZIA");
        JButton addPlayer = new JButton("Aggiungi Giocatore");   
        playerNameField = new JTextField(15);
        JPanel panelPlayer = new JPanel(new FlowLayout());
     
        panelPlayer.add(playerNameField);
        panelPlayer.add(addPlayer);
        panelPlayer.setOpaque(false);
        
        //addPlayer.addActionListener(e -> addPlayer());
        //start.addActionListener(e -> startGame());

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(panelPlayer, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(start, gbc);

        return centerPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        JButton instruction = createInstructionButton();
        JPanel bottomLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomLeft.add(instruction);
        bottomLeft.setOpaque(false);

        playerNameLabel = new JLabel("GIOCATORI: ");
        JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomRight.add(playerNameLabel);
        bottomRight.setOpaque(false);

        bottomPanel.add(bottomLeft, BorderLayout.WEST);
        bottomPanel.add(bottomRight, BorderLayout.EAST);
        return bottomPanel;
    }
        
    private JButton createInstructionButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(20,20));
        button.setIcon(icon);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int width = button.getWidth();
                int height = button.getHeight();
                if(width > 0 && height > 0) {
                    Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(scaledImage));
                }
            }
        });

        //instruction.addActionListener(e -> showInstruction());
        return button;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameMenu menu = new GameMenu();
            menu.setVisible(true);
        });
    }
}
