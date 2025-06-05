package it.unibo.goosegame.view.minigames.three_cups_game.impl;

import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.model.minigames.three_cups_game.api.ThreeCupsGameModel;
import it.unibo.goosegame.view.general.api.MinigameMenu;
import it.unibo.goosegame.view.minigames.three_cups_game.api.ThreeCupsGameView;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of {@link ThreeCupsGameView} interface with Java Swing.
 */
public final class ThreeCupsGameViewImpl implements ThreeCupsGameView {
    private static final double SCALE_FACTOR = 3.5;

    private final List<JLabel> imageLabels;
    private final ImageIcon cupImage;
    private final ImageIcon marbleImage;
    private final int frameSize;
    private final JFrame frame;
    private final JLabel infoLabel;
    private final ThreeCupsGameModel model;
    private final Timer gameTimer;
    private final MinigameMenu menu;

    /**
     * Builds the interface.
     *
     * @param model the model of the Three Cups Game minigame
     * @param menu  the menu used to start the minigame
     */
    public ThreeCupsGameViewImpl(final ThreeCupsGameModel model, final MinigameMenu menu) {
        this.frame = new JFrame();
        this.infoLabel = new JLabel("Prova");
        this.model = model;
        this.menu = menu;
        this.imageLabels = new ArrayList<>();

        this.cupImage = new ImageIcon(Objects.requireNonNull(
                getClass().getResource("/img/minigames/three_cups_game/cup.png"))
        );

        this.marbleImage = new ImageIcon(Objects.requireNonNull(
           getClass().getResource("/img/minigames/three_cups_game/marble.png")
        ));

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.frameSize = screenSize.width / 2;

        this.gameTimer = new Timer(100, e -> {
            if (model.isOver()) {
                finishGame();
            }

            final int index = model.update();

            if (index == -1) {
                for (final JLabel label : imageLabels) {
                    label.setIcon(getResized(cupImage));
                }
            } else {
                imageLabels.get(index).setIcon(getResized(marbleImage));
            }

            updateInfo();
        });

        initInterface();
        gameTimer.start();
    }

    /**
     * Utility method used to stop the game cycle.
     */
    private void finishGame() {
        JOptionPane.showMessageDialog(frame, model.getGameState() == MinigamesModel.GameState.WON ? "You won!" : "You lost!");

        this.gameTimer.stop();
        this.menu.dispose();
        this.frame.dispose();
    }

    /**
     * Utility method used to update the information bar.
     */
    private void updateInfo() {
        final StringBuilder statusMessage = new StringBuilder();

        statusMessage.append(
                String.format(
                        "Round number: %d, victories: %d / %d",
                        model.getRoundNumber(),
                        model.getVictories(),
                        model.getMaxRounds()
                )
        );

        final Optional<Boolean> lastChoice = model.getLastChoice();

        if (lastChoice.isPresent()) {
            if (lastChoice.get()) {
                statusMessage.append("\n Correct!");
            } else {
                statusMessage.append("\n Incorrect!");
            }
        }

        infoLabel.setText(statusMessage.toString());
    }

    /**
     * Utility function used to group all the building instruction for the game.
     */
    private void initInterface() {
        // Initializing frame parameters
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(frameSize, frameSize / 2);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        // Adding the info panel on the frame's top
        final JPanel infoPanel = new JPanel();
        infoPanel.add(infoLabel);
        frame.add(infoPanel, BorderLayout.NORTH);

        // Creating the cups container
        final JPanel cupsContainer = new JPanel();
        cupsContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.add(cupsContainer, BorderLayout.CENTER);

        for (int i = 0; i < 3; i++) {
            final Image scaledImage = getResized(cupImage).getImage();

            final JLabel imageContainer = getImageContainer(scaledImage);

            imageLabels.add(imageContainer);
            cupsContainer.add(imageContainer);
        }
    }

    /**
     * Utility function to group all the instruction to create the images.
     *
     * @param scaledImage already scaled down version of the original image
     * @return the built graphic element
     */
    private JLabel getImageContainer(final Image scaledImage) {
        final JLabel imageContainer = new JLabel(new ImageIcon(scaledImage));

        imageContainer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                model.clicked(imageLabels.indexOf(imageContainer));
            }
        });
        return imageContainer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        this.frame.setVisible(true);
    }

    /**
     * Utility function used to scale down images.
     *
     * @param original original sized image
     * @return scaled down image
     */
    private ImageIcon getResized(final ImageIcon original) {
        final Image scaled = original.getImage().getScaledInstance(
                (int) Math.floor(frameSize / SCALE_FACTOR),
                (int) Math.floor(frameSize / SCALE_FACTOR),
                Image.SCALE_SMOOTH
        );

        return new ImageIcon(scaled);
    }
}
