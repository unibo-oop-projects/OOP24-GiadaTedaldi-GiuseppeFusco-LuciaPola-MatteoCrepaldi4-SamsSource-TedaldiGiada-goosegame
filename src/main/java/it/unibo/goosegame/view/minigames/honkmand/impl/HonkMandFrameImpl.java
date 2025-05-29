package it.unibo.goosegame.view.minigames.honkmand.impl;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.goosegame.view.minigames.honkmand.api.HonkMandFrame;

import java.awt.BorderLayout;

/**
 * Main frame for the HonkMand (Simon Game) minigame.
 * Now only responsible for layout and panel management. All initialization is done in the controller.
 */
public class HonkMandFrameImpl extends JFrame implements HonkMandFrame {
    private static final long serialVersionUID = 1L;
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

    public HonkMandFrameImpl() {
        super("HonkMand");
        super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Only closable via GameEndPanel
        super.setLayout(new BorderLayout());
        super.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        super.setLocationRelativeTo(null);
        super.setResizable(true);
    }

    /**
     * Sets up the main game panel (center view).
     * @param view the main game view (center)
     */
    public void setupGamePanel(final JPanel view) {
        getContentPane().removeAll();
        add(view, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
