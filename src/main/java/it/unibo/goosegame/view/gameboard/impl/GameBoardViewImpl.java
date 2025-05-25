package it.unibo.goosegame.view.gameboard.impl;

import it.unibo.goosegame.model.gameboard.api.GameBoardModel;
import it.unibo.goosegame.utilities.player.api.Player;
import it.unibo.goosegame.view.gameboard.api.GameBoardView;

import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.net.URL;
import java.util.Objects;

/**
 * Implementation of {@link GameBoardView}.
 */
public final class GameBoardViewImpl implements GameBoardView {
    private static final int TRANSPARENCY = 150;

    private final JLabel infoLabel;
    private final JFrame frame;

    /**
     * Constructor of the minigame view.
     *
     * @param model the model of the minigame
     */
    public GameBoardViewImpl(final GameBoardModel model) {
        frame = new JFrame(model.getWindowTitle());
        infoLabel = new JLabel("Lorem Ipsum is simply dummy text");

        initializeComponents();
    }

    /**
     * Initializes all the graphical components.
     */
    private void initializeComponents() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(size.width / 2, size.height / 2);

        // Immagine di sfondo
        final URL gametableImage = Objects.requireNonNull(getClass().getResource("/gameboard/tabellone_temp.png"));
        final Icon backgroundImage = new ImageIcon(gametableImage);

        final JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout()); // Per poter aggiungere componenti sopra

        // Topbar: pannello in alto per informazioni
        final JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topBar.setBackground(new Color(0, 0, 0, TRANSPARENCY)); // Trasparente parziale

        infoLabel.setForeground(Color.WHITE); // Colore testo
        topBar.add(infoLabel);

        backgroundLabel.add(topBar, BorderLayout.NORTH);

        frame.setContentPane(backgroundLabel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        frame.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawPlayer(final Player player) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateInformation(final String string) {
        infoLabel.setText(string);
    }
}
