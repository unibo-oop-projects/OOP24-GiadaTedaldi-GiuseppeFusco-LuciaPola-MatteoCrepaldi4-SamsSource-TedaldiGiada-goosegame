package it.unibo.goosegame.view.minigames.honkmand.impl;

import it.unibo.goosegame.view.general.GameMenuPanel;

import javax.swing.JFrame;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.awt.BorderLayout;

/**
 * Main frame for the HonkMand (Simon Game) minigame.
 * Encapsulates all UI setup and game start logic.
 */
public class HonkMandFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

    /**
     * HonkMand Frame constructor.
     */
    @SuppressFBWarnings("MC_OVERRIDABLE_METHOD_CALL_IN_CONSTRUCTOR")
    public HonkMandFrame() {
        super("HonkMand");
        super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        super.setLocationRelativeTo(null);
        super.setResizable(true);

        final GameMenuPanel menuPanel = new GameMenuPanel("HonkMand", "Start Game", this::startGame);
        super.add(menuPanel, BorderLayout.CENTER);
    }

    private void startGame() {
        getContentPane().removeAll();
        final it.unibo.goosegame.view.minigames.honkmand.api.HonkMandView view = new HonkMandViewImpl();
        view.setFrameRef(this);
        add((javax.swing.JPanel) view, BorderLayout.CENTER);
        revalidate();
        repaint();
        final it.unibo.goosegame.model.minigames.honkmand.api.HonkMandModel model =
         new it.unibo.goosegame.model.minigames.honkmand.impl.HonkMandModelImpl();
        new it.unibo.goosegame.controller.honkmand.HonkMandController(model, view);
    }
}
