package it.unibo.goosegame.controller.cardsatchel;

import it.unibo.goosegame.model.cardsatchel.api.CardSatchelModel;
import it.unibo.goosegame.model.cardsatchel.impl.CardSatchelModelImpl;
import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.utilities.Player;
import it.unibo.goosegame.view.cardsatchel.impl.CardSatchelFrame;
import it.unibo.goosegame.view.cardsatchel.impl.CardSatchelViewImpl;

import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Controller for managing the player's card satchel (bag).
 * Gestisce modello, view e frame secondo MVC.
 */
public class CardSatchelController {
    private final CardSatchelModel satchelModel;
    private final Player owner;
    private CardSatchelViewImpl view;
    private CardSatchelFrame frame;

    /**
     * Costruttore: inizializza modello, view e frame.
     * @param owner il giocatore proprietario
     */
    public CardSatchelController(Player owner) {
        this.satchelModel = new CardSatchelModelImpl();
        this.owner = owner;
        this.view = new CardSatchelViewImpl(this);
        this.frame = new CardSatchelFrame(view);
        // Listener per chiusura tramite X
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeSatchel();
            }
        });
    }

    /**
     * Mostra la finestra del satchel.
     */
    public void showSatchel() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
        view.updateCards(getCards());
    }

    /**
     * Chiude la finestra del satchel.
     */
    public void closeSatchel() {
        if (frame != null) {
            frame.close();
        }
    }

    /**
     * Tenta di aggiungere una carta al satchel.
     */
    public boolean addCard(final Card card) {
        return satchelModel.addCard(card);
    }

    /**
     * Rimuove una carta dal satchel.
     */
    public boolean removeCard(final Card card) {
        return satchelModel.removeCard(card);
    }

    /**
     * Gioca una carta: muove il giocatore, rimuove la carta e chiude il frame.
     */
    public boolean playCard(final Card card) {
        this.owner.move(card.getSteps(), card.isBonus());
        boolean removed = this.removeCard(card);
        closeSatchel(); // Chiude la finestra dopo aver giocato
        return removed;
    }

    /**
     * Restituisce la lista delle carte nel satchel.
     */
    public List<Card> getCards() {
        return satchelModel.getCards();
    }

    /**
     * Svuota il satchel.
     */
    public void clearSatchel() {
        satchelModel.clear();
    }

    /**
     * Verifica se il satchel è pieno.
     */
    public boolean isSatchelFull() {
        return satchelModel.isFull();
    }
}
