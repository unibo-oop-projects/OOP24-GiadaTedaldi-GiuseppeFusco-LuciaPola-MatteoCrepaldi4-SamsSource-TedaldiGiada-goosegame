package it.unibo.goosegame.view.cardsatchel.impl;

import it.unibo.goosegame.controller.cardsatchel.CardSatchelController;
import it.unibo.goosegame.model.cardsatchel.impl.CardSatchelModelImpl;
import it.unibo.goosegame.utilities.Card;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * A JFrame for testing and demonstrating the Card Satchel GUI component.
 * <p>
 * This frame initializes the CardSatchelModel, CardSatchelController, and CardSatchelViewImpl,
 * and provides buttons to add, remove, and play random cards for testing purposes.
 * </p>
 */
public class CardSatchelFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(CardSatchelFrame.class.getName());
    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 300;

    /**
     * CardSatchel Frame constructor.
     */
    public CardSatchelFrame() {
        super("Satchel Test");
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setLayout(new BorderLayout());
        super.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        super.setLocationRelativeTo(null);

        final CardSatchelModelImpl model = new CardSatchelModelImpl();
        final CardSatchelController controller = new CardSatchelController(model);
        final CardSatchelViewImpl view = new CardSatchelViewImpl(controller);
        super.add(view, BorderLayout.CENTER);

        // Button panel for test
        final JPanel buttonPanel = new JPanel();
        final JButton addRandomButton = new JButton("Aggiungi carta random");
        final JButton removeRandomButton = new JButton("Rimuovi carta random");
        final JButton playRandomButton = new JButton("Gioca carta random");
        buttonPanel.add(addRandomButton);
        buttonPanel.add(removeRandomButton);
        buttonPanel.add(playRandomButton);
        super.add(buttonPanel, BorderLayout.SOUTH);

        final Random random = new Random();

        addRandomButton.addActionListener((ActionEvent e) -> {
            final Card[] allCards = Card.values();
            final Card card = allCards[random.nextInt(allCards.length)];
            final boolean added = controller.addCard(card);
            if (added) {
                LOGGER.info("Aggiunta carta: " + card.getName());
            } else {
                LOGGER.info("Impossibile aggiungere la carta: " + card.getName());
            }
            view.updateCards(controller.getCards());
        });

        removeRandomButton.addActionListener((ActionEvent e) -> {
            final List<Card> cards = controller.getCards();
            if (cards.isEmpty()) {
                LOGGER.info("Nessuna carta da rimuovere.");
                return;
            }
            final Card card = cards.get(random.nextInt(cards.size()));
            final boolean removed = controller.removeCard(card);
            if (removed) {
                LOGGER.info("Rimossa carta: " + card.getName());
            } else {
                LOGGER.info("Impossibile rimuovere la carta: " + card.getName());
            }
            view.updateCards(controller.getCards());
        });

        playRandomButton.addActionListener((ActionEvent e) -> {
            final List<Card> cards = controller.getCards();
            if (cards.isEmpty()) {
                LOGGER.info("Nessuna carta da giocare.");
                return;
            }
            final Card card = cards.get(random.nextInt(cards.size()));
            final boolean played = controller.playCard(card);
            if (played) {
                LOGGER.info("Giocata carta: " + card.getName());
            } else {
                LOGGER.info("Impossibile giocare la carta: " + card.getName());
            }
            view.updateCards(controller.getCards());
        });
    }
}
