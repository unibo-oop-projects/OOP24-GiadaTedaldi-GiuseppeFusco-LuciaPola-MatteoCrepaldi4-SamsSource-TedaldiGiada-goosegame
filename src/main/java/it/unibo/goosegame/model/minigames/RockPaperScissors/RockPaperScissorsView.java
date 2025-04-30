package it.unibo.goosegame.model.minigames.RockPaperScissors.impl;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.goosegame.application.GameMenu;

public class RockPaperScissorsView extends JPanel{
    public final JLabel text = new JLabel("Make your choice: ", SwingConstants.CENTER);
    public final JLabel playerLabel = new JLabel("PLAYER: 0", SwingConstants.CENTER);
    public final JLabel computerLabel = new JLabel("COMPUTER: 0", SwingConstants.CENTER);
    public final JLabel result = new JLabel("", SwingConstants.CENTER);
    public JButton playerChoice = new JButton();
    public JButton computerChoice = new JButton();
    public final JButton rock = new JButton("Rock");
    public final JButton paper = new JButton("Paper");
    public final JButton scissors = new JButton("Scissors");
    public final GameMenu gameMenu = new GameMenu();

    public ImageIcon image;
    
    public RockPaperScissorsView() {
        setSize(480,400);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(255, 255, 180));

        image = new ImageIcon(getClass().getResource("/scelta.png"));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setOpaque(false);
        text.setFont(new Font("Verdana", Font.PLAIN, 14));
        topPanel.add(text);
        topPanel.add(rock);
        topPanel.add(paper);
        topPanel.add(scissors);
        add(topPanel);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        namePanel.setOpaque(false);
        playerLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        computerLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        namePanel.add(playerLabel);
        namePanel.add(computerLabel);
        add(namePanel);

        JPanel choicePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        choicePanel.setOpaque(false);

        playerChoice = gameMenu.createButtonIcon(image, 100, 100);
        playerChoice.setContentAreaFilled(false);
        playerChoice.setBorderPainted(false);
        playerChoice.setFocusPainted(false);

        computerChoice = gameMenu.createButtonIcon(image, 100 ,100);
        computerChoice.setContentAreaFilled(false);
        computerChoice.setBorderPainted(false);
        computerChoice.setFocusPainted(false);

        choicePanel.add(playerChoice);
        choicePanel.add(computerChoice);
        add(choicePanel);

        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        resultPanel.setOpaque(false);
        result.setFont(new Font("Verdana", Font.PLAIN, 14));
        resultPanel.add(result);
        add(resultPanel);

    }

    public void updatePlayerScore(int score) {
        playerLabel.setText("PLAYER: " + score);
    }

    public void updateComputerScore(int score) {
        computerLabel.setText("COMPUTER: " + score);
    }

    public void updateComputerChoice(ImageIcon icon) {
        Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        computerChoice.setIcon(new ImageIcon(scaled));
    }

    public void updatePlayerChoice(ImageIcon icon) {
        Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        playerChoice.setIcon(new ImageIcon(scaled));
    }

    public void updateResult(String string) {
        result.setText(string);
    }

}
