package it.unibo.goosegame.view.general;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public abstract class MinigameMenuAbstract extends JFrame {

    private final int size = 4;

    public MinigameMenuAbstract(String imgPath, String title, String infoMsg, ActionListener al) {
        BackgroundPanel background = new BackgroundPanel(imgPath);
        background.setLayout(new GridLayout(size,size));
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.RED);
        background.add(titleLabel);
        JButton startButton = new JButton("Start Game");
        changeBtnStyle(startButton);
        startButton.addActionListener(al);
        startButton.addActionListener(e -> {
            this.dispose();
        });
        JButton instrButton = new JButton("Instructions");
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
                JOptionPane.showMessageDialog(this, "Image not found: " + path);
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
