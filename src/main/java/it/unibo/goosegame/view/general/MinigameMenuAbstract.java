package it.unibo.goosegame.view.general;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public abstract class MinigameMenuAbstract extends JFrame {

    public MinigameMenuAbstract(String imgPath, String title, String infoMsg, ActionListener al) {
        // Create the background panel
        BackgroundPanel background = new BackgroundPanel(imgPath);
        background.setLayout(new BorderLayout());
        setTitle(title);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(400, 300));

        // Title label
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.RED);
        background.add(titleLabel, BorderLayout.NORTH);

        // Create a transparent panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false); 

        // Create the buttons
        JButton startButton = new JButton("Start Game");
        changeBtnStyle(startButton);
        JButton instrButton = new JButton("Instructions");
        changeBtnStyle(instrButton);

        // Add action listeners to buttons
        startButton.addActionListener(al);
        startButton.addActionListener(e -> {
            this.dispose();
        });
        instrButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, infoMsg);
        });

        // Add buttons to the transparent button panel
        buttonPanel.add(startButton);
        buttonPanel.add(instrButton);

        // Add the transparent button panel to the center of the background panel
        background.add(buttonPanel, BorderLayout.CENTER);

        // Set the content pane to be the background panel
        setContentPane(background);
        setVisible(true);
    }

    private void changeBtnStyle(JButton button) {
        button.setPreferredSize(new Dimension(200, 50));
        button.setContentAreaFilled(false); 
        button.setBorderPainted(false);
        button.setForeground(Color.RED);
        button.setFont(new Font("Arial", Font.BOLD, 25));
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String path) {
            try {
                backgroundImage = new ImageIcon(path).getImage();
            } catch (Exception e) {
                System.out.println("Image not found: " + path);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
