package it.unibo.goosegame.view;

import it.unibo.goosegame.controller.CardSatchelController;
import it.unibo.goosegame.utilities.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CardSatchelView extends JPanel {
    private static final int MAX_CARDS = 6;
    private final CardPanel[] cardPanels;
    private final CardSatchelController controller;
    private final List<Card> cards;

    public CardSatchelView(CardSatchelController controller) {
        this.controller = controller;
        this.cards = this.controller.getCards();
        setBackground(new Color(139, 69, 19)); // Marrone sacchetto
        setLayout(new GridLayout(1, MAX_CARDS, 10, 10));
        cardPanels = new CardPanel[MAX_CARDS];
        for (int i = 0; i < MAX_CARDS; i++) {
            Card card = i < cards.size() ? cards.get(i) : null;
            cardPanels[i] = new CardPanel(card);
            add(cardPanels[i]);
        }
    }

    // Aggiorna le carte mostrate
    public void updateCards(List<Card> cards) {
        for (int i = 0; i < MAX_CARDS; i++) {
            Card card = i < cards.size() ? cards.get(i) : null;
            cardPanels[i].setCard(card);
        }
        revalidate();
        repaint();
    }

    // Pannello singola carta
    private class CardPanel extends JPanel {
        private Card card;
        private boolean showDescription = false;

        public CardPanel(Card card) {
            setCard(card);
            setOpaque(false);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (card != null) {
                        showDescription = !showDescription;
                        repaint();
                    }
                }
            });
        }

        @Override
        public Dimension getPreferredSize() {
            // Mantieni proporzione 2:3 (esempio)
            int width = 120;
            int height = 180;
            return new Dimension(width, height);
        }

        public void setCard(Card card) {
            this.card = card;
            showDescription = false;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int w = getWidth();
            int h = getHeight();
            double aspect = 2.0 / 3.0;
            int cardW = w;
            int cardH = (int)(w / aspect);
            if (cardH > h) {
                cardH = h;
                cardW = (int)(h * aspect);
            }
            int x = (w - cardW) / 2;
            int y = (h - cardH) / 2;

            // Disegna sfondo carta
            g.setColor(card == null ? new Color(210, 180, 140) : new Color(210, 180, 140));
            g.fillRoundRect(x, y, cardW, cardH, 16, 16);

            // Disegna bordo
            g.setColor(new Color(101, 67, 33));
            g.drawRoundRect(x, y, cardW, cardH, 16, 16);

            // Disegna contenuti (nome, descrizione, immagine) in modo proporzionale
            if (card != null) {
                g.setColor(Color.BLACK);
                g.setFont(getFont().deriveFont(Font.BOLD, cardH / 12f));
                FontMetrics fm = g.getFontMetrics();
                String name = card.getName();
                int nameW = fm.stringWidth(name);
                g.drawString(name, x + (cardW - nameW) / 2, y + fm.getAscent() + 8);

                if (showDescription) {
                    g.setFont(getFont().deriveFont(cardH / 16f));
                    fm = g.getFontMetrics();
                    String desc = card.getDescription();
                    // Semplice: disegna la descrizione su una riga (puoi migliorare con word wrap)
                    g.drawString(desc, x + 10, y + cardH / 2);
                } else {
                    // Disegna immagine se presente
                    String imgPath = "/img/" + card.name() + "_image.png";
                    java.net.URL imgURL = getClass().getResource(imgPath);
                    if (imgURL != null) {
                        ImageIcon icon = new ImageIcon(imgURL);
                        Image img = icon.getImage();
                        int imgW = cardW - 16;
                        int imgH = cardH / 2;
                        g.drawImage(img, x + 8, y + cardH / 4, imgW, imgH, this);
                    }
                }
            }
        }
    }
}
