package it.unibo.goosegame.view.minigames.herdinghound.impl;

import it.unibo.goosegame.controller.herdinghound.HerdingHoundController;
import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModelImpl;
import it.unibo.goosegame.view.general.GameMenuPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

/**
 * Main frame for the Herding Hound minigame.
 * Encapsulates all UI setup and game start logic.
 */
public class HerdingHoundFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int GRID_SIZE = 31;
    private static final int LEFT_PANEL_WIDTH = 60;
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

    /**
     * HerdingHoundFrame constructor.
     */
    @SuppressFBWarnings("MC_OVERRIDABLE_METHOD_CALL_IN_CONSTRUCTOR")
    public HerdingHoundFrame() {
        super("Herding Hound");
        super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        super.setLocationRelativeTo(null);
        super.setResizable(true);

        final GameMenuPanel menuPanel = new GameMenuPanel("HerdingHound", "Start Game", this::startGame);
        super.add(menuPanel, BorderLayout.CENTER);
    }

    private void startGame() {
        final HerdingHoundModelImpl model = new HerdingHoundModelImpl(GRID_SIZE);
        final HerdingHoundViewImpl view = new HerdingHoundViewImpl(model);
        final RightPanelImpl rightPanel = new RightPanelImpl(model);

        final JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, 0));
        leftPanel.setBackground(Color.LIGHT_GRAY);

        getContentPane().removeAll();
        add(leftPanel, BorderLayout.WEST);
        add(view, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        revalidate();
        repaint();

        final HerdingHoundController controller = new HerdingHoundController(model, view, this, rightPanel);
        view.requestFocusInWindow();

        // Initial countdown, then the game starts
        view.startCountdown(() -> {
            model.startGame();
            controller.startGame();
            view.requestFocusInWindow();
        });
    }
}
