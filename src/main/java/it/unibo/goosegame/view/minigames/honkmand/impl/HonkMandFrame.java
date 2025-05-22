package it.unibo.goosegame.view.minigames.honkmand.impl;

import it.unibo.goosegame.controller.honkmand.HonkMandController;
import it.unibo.goosegame.model.minigames.honkmand.impl.HonkMandModelImpl;
import it.unibo.goosegame.view.general.GameMenuPanel;

import javax.swing.JFrame;
import java.awt.BorderLayout;

/**
 * Main frame for the HonkMand (Simon Game) minigame.
 * Encapsulates all UI setup and game start logic.
 */
public class HonkMandFrame extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

    /**
     * HonkMand Frame constructor.
     */
    public HonkMandFrame() {
        super("HonkMand");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(true);

        final GameMenuPanel menuPanel = new GameMenuPanel("HonkMand", "Start Game", this::startGame);
        add(menuPanel, BorderLayout.CENTER);
    }

    private void startGame() {
        getContentPane().removeAll();
        final HonkMandViewImpl view = new HonkMandViewImpl();
        view.setFrameRef(this);
        add(view, BorderLayout.CENTER);
        revalidate();
        repaint();
        final HonkMandModelImpl model = new HonkMandModelImpl();
        new HonkMandController(model, view);
    }
}
