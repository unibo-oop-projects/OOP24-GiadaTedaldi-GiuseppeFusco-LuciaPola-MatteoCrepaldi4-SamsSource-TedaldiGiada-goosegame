package it.unibo.goosegame.view.minigames.click_the_color.impl;

import it.unibo.goosegame.model.minigames.click_the_color.api.ClickTheColorModel;
import it.unibo.goosegame.view.minigames.click_the_color.api.ClickTheColorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ClickTheColorViewImpl implements ClickTheColorView {
    private JFrame frame;                   //  Main frame of the application
    private JLabel infoLabel;               //  Label needed to store and update the game information
    private List<JButton> buttons;          //  List containing the four buttons, needed to make the game work
    private ClickTheColorModel model;       //  Model of the game, needed to make the application respond to game logic
    private Timer gameTimer;                //  Timer responsible for the updates to the graphical interface

    /**
     * Constructor for the graphical interface
     *
     * @param model model object for the game logic
     */
    public ClickTheColorViewImpl(ClickTheColorModel model) {
        this.model = model;
        this.frame = new JFrame(model.getName());
        this.infoLabel = new JLabel("Click the color");
        this.buttons = new ArrayList<>();

        this.gameTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int buttonIndex = model.update();

                if(model.isOver()) {
                    stopGame();
                }

                if (buttonIndex == -1) {
                    for (JButton button : buttons) {
                        updateColor(button, false);
                    }
                }
                else {
                    updateColor(buttons.get(buttonIndex), true);
                }
            }
        });

        this.gameTimer.start();

        initializeComponents();
    }

    private void stopGame() {
        this.gameTimer.stop();

        JOptionPane.showMessageDialog(frame, "Your score is: " + model.getScore());

        this.frame.dispose();
    }

    /**
     * Utility function to make the switching of the colors more readable
     *
     * @param button object representing the button
     * @param active button is on or off
     */
    private void updateColor(JButton button, boolean active) {
        if (active) {
            button.setBackground(on_colors[buttons.indexOf(button)]);
        }
        else {
            button.setBackground(off_colors[buttons.indexOf(button)]);
        }
    }

    /**
     * Utility function to group all the GUI building code
     */
    private void initializeComponents() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 600);

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setPreferredSize(new Dimension(0, 50));
        infoPanel.add(infoLabel);

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2));

        for (int i = 0; i < 4; i++) {
            JButton button = new JButton();
            button.setBackground(off_colors[i]);

            button.addActionListener(e -> {
                model.clicked(buttons.indexOf(button));
                this.infoLabel.setText("Score: " + model.getScore());
            });

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
