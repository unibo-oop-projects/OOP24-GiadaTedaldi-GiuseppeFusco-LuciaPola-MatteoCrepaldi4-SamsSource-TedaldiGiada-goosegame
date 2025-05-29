package it.unibo.goosegame.view.minigames.herdinghound.impl;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.goosegame.view.minigames.herdinghound.api.HerdingHoundFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

/**
 * Main frame for the Herding Hound minigame.
 * Now only responsible for layout and panel management. All initialization is done in the controller.
 */
public class HerdingHoundFrameImpl extends JFrame implements HerdingHoundFrame {
    private static final long serialVersionUID = 1L;
    private static final int LEFT_PANEL_WIDTH = 60;
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

    private final JPanel leftPanel;

    /**
     * HerdingHoundFrame constructor.
     * Sets up the frame but does not add game panels.
     */
    public HerdingHoundFrameImpl() {
        super("Herding Hound");
        super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Only closable via GameEndPanel
        super.setLayout(new BorderLayout());
        super.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        super.setLocationRelativeTo(null);
        super.setResizable(true);

        this.leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, 0));
        leftPanel.setBackground(Color.LIGHT_GRAY);
        add(leftPanel, BorderLayout.WEST);
    }

    /**
     * Sets up the main game panels (center view and right panel).
     * @param view the main game view (center)
     * @param rightPanel the right panel (east)
     */
    public void setupGamePanels(final Component view, final Component rightPanel) {
        getContentPane().removeAll();
        add(leftPanel, BorderLayout.WEST);
        add(view, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        revalidate();
        repaint();
    }
}
