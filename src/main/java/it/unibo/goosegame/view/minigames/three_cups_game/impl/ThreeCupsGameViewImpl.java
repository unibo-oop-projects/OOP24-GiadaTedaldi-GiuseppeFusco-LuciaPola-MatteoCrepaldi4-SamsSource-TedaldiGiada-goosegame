package it.unibo.goosegame.view.minigames.three_cups_game.impl;

import it.unibo.goosegame.model.minigames.three_cups_game.api.ThreeCupsGameModel;
import it.unibo.goosegame.view.minigames.three_cups_game.api.ThreeCupsGameView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link ThreeCupsGameView} interface with Java Swing
 */
public class ThreeCupsGameViewImpl implements ThreeCupsGameView {
    private final List<JButton> buttons = new ArrayList<JButton>();
    private final JFrame frame;
    private final JLabel infoLabel;
    private final ThreeCupsGameModel model;

    /**
     * Builds the interface
     */
    public ThreeCupsGameViewImpl(ThreeCupsGameModel model) {
        this.frame = new JFrame();
        this.infoLabel = new JLabel();
        this.model = model;

        initInterface();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initInterface() {
        frame.setTitle(model.getName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Barra superiore
        JPanel infoBar = new JPanel();
        infoBar.setPreferredSize(new Dimension(0, 40));  // Altezza fissa, larghezza flessibile
        infoBar.setBackground(Color.LIGHT_GRAY);
        infoBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        infoBar.add(infoLabel);
        frame.add(infoBar, BorderLayout.NORTH);

        // Pannello centrale con le tazze
        JPanel cupsPanel = new JPanel();
        cupsPanel.setLayout(new GridLayout(1, 3, 10, 10));  // Tre colonne, con spazio tra loro
        cupsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Icon image = new ImageIcon("tazza.png");

        for (int i = 0; i < 3; i++) {
            JButton cup = new JButton();
            cup.setIcon(image);
            //cup.setBackground(Color.LIGHT_GRAY);

            cupsPanel.add(cup);
            buttons.add(cup);

            cup.addActionListener(e -> {
                // Function created just to make the reading of code better
                onClickActions(cup);
            });
        }

        frame.add(cupsPanel, BorderLayout.CENTER);

        // Impostazioni base della finestra
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);  // Centra la finestra
    }

    private void onClickActions(JButton cup) {
        // Registers which button has been pressed by the user
        boolean correctGuess = model.makeChoice(buttons.indexOf(cup));
        String message;

        if (correctGuess) {
            message = "You guessed correctly!";
        }
        else {
            message = "You guessed incorrectly!";
        }

        // Updates the information on the info bar
        infoLabel.setText(model.getStatus());

        // Code executed when the game is over
        if(this.model.isOver()) {
            if(this.model.getResult() >= 3) message = "You win!";
            else message = "You lose!";


            this.frame.dispose();
        }

        JOptionPane.showMessageDialog(frame, message);
    }

    @Override
    public void show() {
        this.infoLabel.setText(model.getStatus());
        this.frame.setVisible(true);
    }
}
