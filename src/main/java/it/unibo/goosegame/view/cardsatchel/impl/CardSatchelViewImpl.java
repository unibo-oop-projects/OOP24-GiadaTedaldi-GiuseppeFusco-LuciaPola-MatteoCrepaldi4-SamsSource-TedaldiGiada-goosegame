package it.unibo.goosegame.view.cardsatchel.impl;

import it.unibo.goosegame.controller.cardsatchel.CardSatchelController;
import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.view.cardsatchel.api.CardSatchelView;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.util.function.Consumer;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Implementation of the CardSatchelView for displaying the player's card satchel.
 */
public final class CardSatchelViewImpl extends JPanel implements CardSatchelView {
    private static final long serialVersionUID = 1L;
    private static final int MAX_CARDS = 6;
    private static final int CARD_WIDTH = 120;
    private static final int CARD_HEIGHT = 180;
    private static final Color SATCHEL_BROWN = new Color(139, 69, 19);

    private final CardPanelViewImpl[] cardPanels;
    private final transient CardSatchelController controller;

    /**
     * Constructs the CardSatchelViewImpl.
     * @param controller the controller for the satchel
     */
    @SuppressFBWarnings(
    value = "EI2",
    justification = "The controller reference is intentionally stored to allow the view to interact with the game logic."
    + "The controller's lifecycle and mutability are managed externally."
    )
    public CardSatchelViewImpl(final CardSatchelController controller) {
        this.controller = controller;
        setBackground(SATCHEL_BROWN); // brown satchel
        setLayout(new GridLayout(1, MAX_CARDS, 10, 10));
        this.cardPanels = new CardPanelViewImpl[MAX_CARDS];
        final List<Card> cards = this.controller.getCards();
        for (int i = 0; i < MAX_CARDS; i++) {
            final Card card = i < cards.size() ? cards.get(i) : null;
            this.cardPanels[i] = this.createCardPanel(card);
            add(this.cardPanels[i]);
        }
    }

    /**
     * Updates the cards displayed in the satchel view.
     * @param cards the list of cards to display
     */
    @Override
    public void updateCards(final List<Card> cards) {
        for (int i = 0; i < MAX_CARDS; i++) {
            final Card card = i < cards.size() ? cards.get(i) : null;
            this.cardPanels[i].setCard(card);
        }
        revalidate();
        repaint();
    }

    private CardPanelViewImpl createCardPanel(final Card card) {
        final Consumer<Card> playAction = c -> {
            this.controller.playCard(c);
            this.updateCards(this.controller.getCards());
        };
        return new CardPanelViewImpl(card, true, "Play", playAction, CARD_WIDTH, CARD_HEIGHT);
    }
}
