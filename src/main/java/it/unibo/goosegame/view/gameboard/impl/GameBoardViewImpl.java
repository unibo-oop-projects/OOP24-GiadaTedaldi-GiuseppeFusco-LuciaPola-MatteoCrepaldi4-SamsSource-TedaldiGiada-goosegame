package it.unibo.goosegame.view.gameboard.impl;

import it.unibo.goosegame.model.gameboard.api.GameBoardModel;
import it.unibo.goosegame.utilities.player.api.Player;
import it.unibo.goosegame.view.gameboard.api.GameBoardView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GameBoardViewImpl implements GameBoardView {
    private static final String WINDOW_NAME = "Goose Game";

    JLabel infoLabel;
    JFrame frame;
    private final GameBoardModel model;

    public GameBoardViewImpl(GameBoardModel model) {
        this.model = model;
        frame = new JFrame(model.getWindowTitle());
        infoLabel = new JLabel("Lorem Ipsum is simply dummy text");

        InitializeComponents();
    }

    /**
     * Initializes all the graphical components
     */
    private void InitializeComponents() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(size.width / 2, size.height / 2);

        // Immagine di sfondo
        Icon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/gameboard/tabellone_temp.png")));

        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout()); // Per poter aggiungere componenti sopra

        // Topbar: pannello in alto per informazioni
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topBar.setBackground(new Color(0, 0, 0, 150)); // Trasparente parziale

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
    public void drawPlayer(Player player) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateInformation(String string) {
        infoLabel.setText(string);
    }
}
