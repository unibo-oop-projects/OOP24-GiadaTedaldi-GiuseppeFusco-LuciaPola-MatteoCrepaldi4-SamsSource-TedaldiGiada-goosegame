package it.unibo.goosegame.view.minigames.three_cups_game.impl;

import it.unibo.goosegame.model.minigames.three_cups_game.api.ThreeCupsGameModel;
import it.unibo.goosegame.view.minigames.three_cups_game.api.ThreeCupsGameView;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link ThreeCupsGameView} interface with Java Swing.
 */
public final class ThreeCupsGameViewImpl implements ThreeCupsGameView {
    private static final int FRAME_SIZE = 600;
    private static final int BUTTONS_HEIGHT = 40;

    private final List<JButton> buttons = new ArrayList<>();
    private final JFrame frame;
    private final JLabel infoLabel;
    private final ThreeCupsGameModel model;

    /**
     * Builds the interface.
     *
     * @param model the model of the Three Cups Game minigame
     */
    public ThreeCupsGameViewImpl(final ThreeCupsGameModel model) {
        this.frame = new JFrame();
        this.infoLabel = new JLabel();
        this.model = model;

        initInterface();
    }

    private void initInterface() {
        frame.setTitle(model.getName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Barra superiore
        final JPanel infoBar = new JPanel();
        infoBar.setPreferredSize(new Dimension(0, BUTTONS_HEIGHT));  // Altezza fissa, larghezza flessibile
        infoBar.setBackground(Color.LIGHT_GRAY);
        infoBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        infoBar.add(infoLabel);
        frame.add(infoBar, BorderLayout.NORTH);

        // Pannello centrale con le tazze
        final JPanel cupsPanel = new JPanel();
        cupsPanel.setLayout(new GridLayout(1, 3, 10, 10));  // Tre colonne, con spazio tra loro
        cupsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        final Icon image = new ImageIcon("tazza.png");

        for (int i = 0; i < 3; i++) {
            final JButton cup = new JButton();
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
        frame.setSize(FRAME_SIZE, FRAME_SIZE);
        frame.setLocationRelativeTo(null);  // Centra la finestra
    }

    private void onClickActions(final JButton cup) {
        // Registers which button has been pressed by the user
        final String message;

        if (model.makeChoice(buttons.indexOf(cup))) {
            message = "You guessed correctly!";
        } else {
            message = "You guessed incorrectly!";
        }

        // Updates the information on the info bar
        infoLabel.setText(model.getStatus());

        // Code executed when the game is over
        if (this.model.isOver()) {
            if (this.model.getGameState() == ThreeCupsGameModel.GameState.WON) {
                JOptionPane.showMessageDialog(frame, message, "Won", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, message, "Lost", JOptionPane.INFORMATION_MESSAGE);
            }

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
