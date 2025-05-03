package it.unibo.goosegame.controller.minigames.RockPaperScissors;

import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class RockPaperScissorsController{
    private final RockPaperScissorsView view;
    private final RockPaperScissorsModel model;

    public ImageIcon rockImage;
    public ImageIcon paperImage;
    public ImageIcon scissorsImage;

    public RockPaperScissorsController(RockPaperScissorsModel m, RockPaperScissorsView v) {
        this.model = m;
        this.view = v;

        view.rock.addActionListener(e -> playTurn("Rock"));
        view.paper.addActionListener(e -> playTurn("Paper"));
        view.scissors.addActionListener(e -> playTurn("Scissors"));

        rockImage = new ImageIcon(getClass().getResource("/rock.png"));
        paperImage = new ImageIcon(getClass().getResource("/paper.png"));
        scissorsImage = new ImageIcon(getClass().getResource("/scissors.png"));
    }

    private void playTurn(String playerChoice) {
        String computerChoice = model.playRound(playerChoice);

        switch(computerChoice) {
            case "Rock" -> {
                view.updateComputerChoice(rockImage);
            }
            case "Paper" -> {
                view.updateComputerChoice(paperImage);
            }
            case "Scissors" -> {
                view.updateComputerChoice(scissorsImage);
            }
        }

        switch(playerChoice) {
            case "Rock" -> {
                view.updatePlayerChoice(rockImage);
            }
            case "Paper" -> {
                view.updatePlayerChoice(paperImage);
            }
            case "Scissors" -> {
                view.updatePlayerChoice(scissorsImage);
            }
        }

        view.updatePlayerScore(model.getPlayerScore());
        view.updateComputerScore(model.getComputerScore());
        view.updateResult("");

        int roundResult = model.determineWinner(playerChoice, computerChoice);
        switch (roundResult) {
            case 1 -> view.updateResult("You win this round!");
            case -1 -> view.updateResult("Computer win this round!");
            default -> {
                view.updateResult("It's a tie!");
            }
        }

        if(model.isOver()) {
            if(model.getName().equals("PLAYER")) {
                JOptionPane.showMessageDialog(view, "YUO WIN");
            } else {
                JOptionPane.showMessageDialog(view, "GAME OVER - YOU LOSE");
            }

            Window window = SwingUtilities.getWindowAncestor(view);
            if (window != null) {
                window.dispose();
            }
        }
    }
}
