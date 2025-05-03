package it.unibo.goosegame.view.minigames.Hangman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HangmanView extends JFrame {
    
    private final JLabel wordLabel; 
    private final JLabel imageLabel;
    private final JLabel attemptsLabel;
    private final JPanel mainPanel;
    private final JPanel keyboardPanel;
    private HangmanController controller;
    private int currentAttempts;
    private boolean image = false;

    public HangmanView() {
        setTitle("Hangman");
        setSize(820, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Verdana", Font.BOLD, 34));
        wordLabel.setForeground(Color.ORANGE);
        wordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Component gap = Box.createVerticalStrut(15);
        currentAttempts = 5;
        
        imageLabel = new JLabel();
        imageLabel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateImage(currentAttempts);
            }
        });
        imageLabel.setVisible(false);
        imageLabel.setPreferredSize(new Dimension(170, 90));
        imageLabel.setAlignmentX(CENTER_ALIGNMENT);

        attemptsLabel = new JLabel();
        attemptsLabel.setFont(new Font("Verdana", Font.ITALIC, 12));
        attemptsLabel.setAlignmentX(CENTER_ALIGNMENT);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);

        rightPanel.add(attemptsLabel);
        rightPanel.add(gap);
        rightPanel.add(imageLabel);

        keyboardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        for(char letter = 'A'; letter <= 'Z'; letter++) {
            JButton button = new JButton(String.valueOf(letter));
            button.setBackground(new Color(255, 255, 180));
            button.setForeground(Color.ORANGE);
            final char selectedLetter = letter;
            button.addActionListener(e -> {
                controller.onLetterPressed(selectedLetter);
                disableButton(selectedLetter);
            });
            keyboardPanel.add(button);
        }
        keyboardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(wordLabel);
        mainPanel.add(gap);
        mainPanel.add(keyboardPanel);
        mainPanel.add(Box.createVerticalGlue());
        add(mainPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }

    public void updateWord(String word) {
        wordLabel.setText(word);
    }

    public void updateImage(int attempts) {
        currentAttempts = attempts;
        attemptsLabel.setText("Tentativi rimasti: " + attempts);
        if(attempts < 5 && !image) {
            image = true;
            imageLabel.setVisible(true);
        }
        java.net.URL imgURL = getClass().getResource("/hangman" + attempts + ".png");
        if(imgURL != null) {
            ImageIcon original = new ImageIcon(imgURL);
            int w = imageLabel.getWidth();
            int h = imageLabel.getHeight();
            if(w > 0 && h > 0) {
                Image scaled = original.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaled));
            }
        } else {
            imageLabel.setIcon(null);
        }
    }

    public void disableButton(char car) {
        for(Component comp : keyboardPanel.getComponents()) {
            if(comp instanceof  JButton button && button.getText().equals(String.valueOf(car))) {
                button.setEnabled(false);
            }
        }
    }

    public void disableAllButton() {
        for(Component comp : keyboardPanel.getComponents()) {
            if(comp instanceof  JButton button) {
                button.setEnabled(false);
            }
        }
    }

    public void setController(HangmanController controller) {
       this.controller = controller;
    }
}
