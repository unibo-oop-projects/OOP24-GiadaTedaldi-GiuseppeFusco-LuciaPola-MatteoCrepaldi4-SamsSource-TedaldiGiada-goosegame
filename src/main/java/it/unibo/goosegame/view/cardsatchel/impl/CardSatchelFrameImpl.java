package it.unibo.goosegame.view.cardsatchel.impl;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.goosegame.view.cardsatchel.api.CardSatchelFrame;

import java.awt.BorderLayout;

/**
 * JFrame per mostrare il satchel delle carte del giocatore.
 * Non contiene logica di test, solo la view vera e propria.
 */
public class CardSatchelFrameImpl extends JFrame implements CardSatchelFrame {
    private static final long serialVersionUID = 1L;
    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 300;

    /**
     *Receives the view to show.
     *@param view La View of the Satchel
     */
    public CardSatchelFrameImpl(final JPanel view) {
        super("Satchel");
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        super.setLocationRelativeTo(null);
        super.add(view, BorderLayout.CENTER);
    }

    /**
     * Closes the Satchel window.
     */
    @Override
    public void close() {
        setVisible(false);
        dispose();
    }
}
