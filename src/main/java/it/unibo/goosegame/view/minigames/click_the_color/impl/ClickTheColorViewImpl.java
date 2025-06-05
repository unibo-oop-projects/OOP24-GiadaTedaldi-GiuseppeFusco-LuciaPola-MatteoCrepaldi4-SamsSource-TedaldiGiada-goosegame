package it.unibo.goosegame.view.minigames.click_the_color.impl;

import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.model.minigames.click_the_color.api.ClickTheColorModel;
import it.unibo.goosegame.view.general.api.MinigameMenu;
import it.unibo.goosegame.view.minigames.click_the_color.api.ClickTheColorView;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
// import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

/**
 * Implementation of {@link ClickTheColorView}.
 */
public class ClickTheColorViewImpl implements ClickTheColorView {
    private static final int FRAME_SIZE = 600;
    private static final int FONT_SIZE = 25;
    private static final int INFO_PANEL_HEIGHT = 50;
    private static final Color[] OFF_COLORS = {
            Color.decode("#9f6060"),
            Color.decode("#9f9f60"),
            Color.decode("#609f60"),
            Color.decode("#60609f"),
    };
    private static final Color[] ON_COLORS = {
            Color.decode("#ff0000"),
            Color.decode("#ffff00"),
            Color.decode("#00ff00"),
            Color.decode("#0000ff"),
    };

    private final JFrame frame;                     //  Main frame of the application
    private final JLabel infoLabel;                 //  Label needed to store and update the game information
    private final List<JButton> buttons;            //  List containing the four buttons, needed to make the game work
    private final ClickTheColorModel model;         //  Model of the game, needed to make the application respond to game logic
    private final Timer gameTimer;                  //  Timer responsible for the updates to the graphical interface
    private final MinigameMenu menu;                //  Menu of the game, needed to make the application respond to user input

    /**
     * Constructor for the graphical interface.
     *
     * @param model model object for the game logic
     * @param menu  menu used to start the minigame
     */
    public ClickTheColorViewImpl(final ClickTheColorModel model, final MinigameMenu menu) {
        this.model = model;
        this.menu = menu;
        this.frame = new JFrame(model.getName());
        this.infoLabel = new JLabel("Click the color");
        this.buttons = new ArrayList<>();

        this.gameTimer = new Timer(100, e -> {
            final int buttonIndex = model.update();

            if (model.isOver()) {
                stopGame();
            }

            if (buttonIndex == -1) {
                for (final JButton button : buttons) {
                    updateColor(button, false);
                }
            } else {
                updateColor(buttons.get(buttonIndex), true);
            }

            updateScore();
        });

        this.gameTimer.start();

        initializeComponents();
    }

    private void stopGame() {
        this.gameTimer.stop();

        JOptionPane.showMessageDialog(frame, model.getGameState() == GameState.WON ? "You won!" : "You lost!");

        this.frame.dispose();
        this.menu.dispose();
    }

    /**
     * Utility function to make the switching of the colors more readable.
     *
     * @param button object representing the button
     * @param active button is on or off
     */
    private void updateColor(final JButton button, final boolean active) {
        if (active) {
            button.setBackground(ON_COLORS[buttons.indexOf(button)]);
        } else {
            button.setBackground(OFF_COLORS[buttons.indexOf(button)]);
        }
    }

    private void updateScore() {
        infoLabel.setText("Score: " + model.getScore());
    }

    /**
     * Utility function to group all the GUI building code.
     */
    private void initializeComponents() {

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(FRAME_SIZE, FRAME_SIZE);

        infoLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));

        final JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setPreferredSize(new Dimension(0, INFO_PANEL_HEIGHT));
        infoPanel.add(infoLabel);

        final JPanel buttonsPanel = new JPanel(new GridLayout(2, 2));

        for (int i = 0; i < 4; i++) {
            final JButton button = new JButton();
            button.setBackground(OFF_COLORS[i]);

            button.addActionListener(e -> model.clicked(buttons.indexOf(button)));

            buttons.add(button);
            buttonsPanel.add(button);
        }

        frame.add(infoPanel, BorderLayout.NORTH);
        frame.add(buttonsPanel, BorderLayout.CENTER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        this.frame.setVisible(true);
    }
}
