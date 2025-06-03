package it.unibo.goosegame.controller.cardsatchel;

import it.unibo.goosegame.controller.gameboard.api.GameBoard;
import it.unibo.goosegame.model.cardsatchel.api.CardSatchelModel;
import it.unibo.goosegame.model.cardsatchel.impl.CardSatchelModelImpl;
import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.view.cardsatchel.impl.CardSatchelFrameImpl;
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
    private final GameBoard board;
    private final CardSatchelViewImpl view;
    private final CardSatchelFrameImpl frame;

    /**
     *Initialize model, view and frames.
     *@param owner the owner player.
     */
    public CardSatchelController(final GameBoard board) {
        this.satchelModel = new CardSatchelModelImpl();
        this.board = board;
        this.view = new CardSatchelViewImpl(this);
        this.frame = new CardSatchelFrameImpl(view);
        //Listener for closing via X
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                closeSatchel();
            }
        });
    }

    /**
     *Shows the Satchel window.
     */
    public void showSatchel() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
        view.updateCards(getCards());
    }

    /**
     * Closes the Satchel window.
     */
    public void closeSatchel() {
        if (frame != null) {
            frame.close();
        }
    }

    /**
     *Tries to add a card to the Satchel.
     * @param card the card to add

     *@return True if the card has been added to Satchel, false otherwise
     */
    public boolean addCard(final Card card) {
        return satchelModel.addCard(card);
    }

    /**
     * Removes a card from Satchel.
     * @param card the card to remove
     * @return true if the card has been removed, false otherwise
     */
    public boolean removeCard(final Card card) {
        return satchelModel.removeCard(card);
    }

    /**
     * Play a card: move the player, remove the card and close the frame.
     * @param card the card to play
     * @return true if the card has been played, false otherwise
     */
    public boolean playCard(final Card card) {
        this.board.move(card.getSteps(), card.isBonus());
        final boolean removed = this.removeCard(card);
        closeSatchel(); //Closes the window after playing
        return removed;
    }

    /**
     * Returns the list of cards in the Satchel.
     * @return the list of cards
     */
    public List<Card> getCards() {
        return satchelModel.getCards();
    }

    /**
     * Empty the Satchel.
     */
    public void clearSatchel() {
        satchelModel.clear();
    }

    /**
     * Checks if Satchel is full.
     * @return true if Satchel is full, false otherwise
     */
    public boolean isSatchelFull() {
        return satchelModel.isFull();
    }
}
