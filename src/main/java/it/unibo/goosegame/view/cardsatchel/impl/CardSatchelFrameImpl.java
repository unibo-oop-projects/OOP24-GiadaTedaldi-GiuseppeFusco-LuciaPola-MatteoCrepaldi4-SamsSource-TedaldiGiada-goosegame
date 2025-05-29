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
     * Costruttore: riceve la view da mostrare.
     * @param view la view del satchel
     */
    public CardSatchelFrameImpl(final JPanel view) {
        super("Satchel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        add(view, BorderLayout.CENTER);
    }

    /**
     * Chiude la finestra del satchel.
     */
    public void close() {
        setVisible(false);
        dispose();
    }
}
