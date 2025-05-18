package it.unibo.goosegame.application;

import it.unibo.goosegame.model.CardSatchelModel;
import it.unibo.goosegame.controller.CardSatchelController;
import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.view.CardSatchelView;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.util.*;

public class CardSatchelMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CardSatchelMain::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        CardSatchelModel model = new CardSatchelModel();
        CardSatchelController controller = new CardSatchelController(model);
        CardSatchelView view = new CardSatchelView(controller);

        JFrame frame = new JFrame("Satchel Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(view, BorderLayout.CENTER);

        // Pannello bottoni per test
        JPanel buttonPanel = new JPanel();
        JButton addRandomButton = new JButton("Aggiungi carta random");
        JButton removeRandomButton = new JButton("Rimuovi carta random");
        JButton playRandomButton = new JButton("Gioca carta random");
        buttonPanel.add(addRandomButton);
        buttonPanel.add(removeRandomButton);
        buttonPanel.add(playRandomButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        Random random = new Random();

        addRandomButton.addActionListener((ActionEvent e) -> {
            Card[] allCards = Card.values();
            Card card = allCards[random.nextInt(allCards.length)];
            boolean added = controller.addCard(card);
            if (added) {
                System.out.println("Aggiunta carta: " + card.getName());
            } else {
                System.out.println("Impossibile aggiungere la carta: " + card.getName());
            }
            view.updateCards(controller.getCards());
        });

        removeRandomButton.addActionListener((ActionEvent e) -> {
            List<Card> cards = controller.getCards();
            if (cards.isEmpty()) {
                System.out.println("Nessuna carta da rimuovere.");
                return;
            }
            Card card = cards.get(random.nextInt(cards.size()));
            boolean removed = controller.removeCard(card);
            if (removed) {
                System.out.println("Rimossa carta: " + card.getName());
            } else {
                System.out.println("Impossibile rimuovere la carta: " + card.getName());
            }
            view.updateCards(controller.getCards());
        });

        playRandomButton.addActionListener((ActionEvent e) -> {
            List<Card> cards = controller.getCards();
            if (cards.isEmpty()) {
                System.out.println("Nessuna carta da giocare.");
                return;
            }
            Card card = cards.get(random.nextInt(cards.size()));
            boolean played = controller.playCard(card);
            if (played) {
                System.out.println("Giocata carta: " + card.getName());
            } else {
                System.out.println("Impossibile giocare la carta: " + card.getName());
            }
            view.updateCards(controller.getCards());
        });

        frame.setSize(900, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}