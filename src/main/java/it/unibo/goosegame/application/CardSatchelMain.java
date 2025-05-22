package it.unibo.goosegame.application;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unibo.goosegame.controller.cardsatchel.CardSatchelController;
import it.unibo.goosegame.model.cardsatchel.impl.CardSatchelModelImpl;
import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.view.cardsatchel.impl.CardSatchelViewImpl;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Main class to test the CardSatchel GUI.
 */
public final class CardSatchelMain {

    private static final Logger LOGGER = Logger.getLogger(CardSatchelMain.class.getName());

    private CardSatchelMain() {
        throw new AssertionError();
    }

    /**
     * Launches the CardSatchel test GUI.
     * @param args the command line arguments (not used)
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(CardSatchelMain::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        final CardSatchelModelImpl model = new CardSatchelModelImpl();
        final CardSatchelController controller = new CardSatchelController(model);
        final CardSatchelViewImpl view = new CardSatchelViewImpl(controller);

        final JFrame frame = new JFrame("Satchel Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(view, BorderLayout.CENTER);

        // Pannello bottoni per test
        final JPanel buttonPanel = new JPanel();
        final JButton addRandomButton = new JButton("Aggiungi carta random");
        final JButton removeRandomButton = new JButton("Rimuovi carta random");
        final JButton playRandomButton = new JButton("Gioca carta random");
        buttonPanel.add(addRandomButton);
        buttonPanel.add(removeRandomButton);
        buttonPanel.add(playRandomButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

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

        final int frameWidth = 900;
        final int frameHeight = 300;
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
