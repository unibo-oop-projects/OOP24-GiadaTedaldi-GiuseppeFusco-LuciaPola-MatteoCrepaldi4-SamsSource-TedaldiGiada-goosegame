package it.unibo.goosegame.view;

import it.unibo.goosegame.controller.CardSatchelController;
import it.unibo.goosegame.utilities.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CardSatchelView extends JPanel {
    private static final int MAX_CARDS = 6;
    private final CardPanel[] cardPanels;
    private final CardSatchelController controller;

    public CardSatchelView(CardSatchelController controller) {
        this.controller = controller;
        setBackground(new Color(139, 69, 19)); // Marrone sacchetto
        setLayout(new GridLayout(1, MAX_CARDS, 10, 10));
        cardPanels = new CardPanel[MAX_CARDS];
        List<Card> cards = this.controller.getCards();
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
        private final JButton playButton;

        public CardPanel(Card card) {
            setOpaque(false);
            setLayout(null); // Layout libero per posizionamento custom
            playButton = new JButton("Gioca");
            playButton.setVisible(false);
            playButton.setFocusable(false);
            playButton.setMargin(new Insets(2, 8, 2, 8));
            playButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (CardPanel.this.card != null) {
                        controller.playCard(CardPanel.this.card);
                        // Aggiorna la view dopo aver giocato la carta
                        CardSatchelView.this.updateCards(controller.getCards());
                    }
                }
            });
            add(playButton);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (CardPanel.this.card != null) {
                        showDescription = !showDescription;
                        playButton.setVisible(showDescription);
                        repaint();
                    }
                }
            });
            setCard(card); // CHIAMA setCard SOLO ALLA FINE!
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
            playButton.setVisible(false);
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

            int padding = 16; // margine interno per testo e contenuti

            // Posiziona il bottone in basso a destra
            int btnW = 60;
            int btnH = 28;
            playButton.setBounds(x + cardW - btnW - padding, y + cardH - btnH - padding, btnW, btnH);

            // Disegna sfondo carta
            g.setColor(card == null ? new Color(210, 180, 140) : new Color(210, 180, 140));
            g.fillRoundRect(x, y, cardW, cardH, 16, 16);

            // Disegna bordo più spesso
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(101, 67, 33));
            g2.setStroke(new BasicStroke(6)); // bordo più spesso
            g2.drawRoundRect(x, y, cardW, cardH, 16, 16);
            g2.dispose();

            // Disegna contenuti (nome, descrizione, immagine) in modo proporzionale
            if (card != null) {
                g.setColor(Color.BLACK);
                String name = card.getName();
                int maxNameWidth = cardW - 2 * padding;
                float fontSize = cardH / 12f;
                Font font = getFont().deriveFont(Font.BOLD, fontSize);
                FontMetrics fm = g.getFontMetrics(font);
                int nameW = fm.stringWidth(name);
                // Riduci il font finché il nome non ci sta
                while (nameW > maxNameWidth && fontSize > 8) {
                    fontSize -= 1f;
                    font = getFont().deriveFont(Font.BOLD, fontSize);
                    fm = g.getFontMetrics(font);
                    nameW = fm.stringWidth(name);
                }
                g.setFont(font);
                // Centra il nome orizzontalmente ma rispetta il padding
                int nameX = x + (cardW - nameW) / 2;
                if (nameX < x + padding) nameX = x + padding;
                g.drawString(name, nameX, y + fm.getAscent() + padding);

                if (showDescription) {
                    g.setFont(getFont().deriveFont(cardH / 16f));
                    fm = g.getFontMetrics();
                    String desc = card.getDescription();
                    // Word wrap con padding
                    int descX = x + padding;
                    int descY = y + cardH / 4;
                    int maxWidth = cardW - 2 * padding;
                    for (String line : wrapText(desc, fm, maxWidth)) {
                        g.drawString(line, descX, descY);
                        descY += fm.getHeight();
                    }
                } else {
                    // Disegna immagine se presente
                    String imgPath = "/img/" + card.name() + "_image.png";
                    java.net.URL imgURL = getClass().getResource(imgPath);
                    if (imgURL != null) {
                        ImageIcon icon = new ImageIcon(imgURL);
                        Image img = icon.getImage();
                        int imgW = cardW - 2 * padding;
                        int imgH = cardH / 2;
                        g.drawImage(img, x + padding, y + cardH / 4, imgW, imgH, this);
                    }
                }
            }
        }

        // Utility per word wrap
        private java.util.List<String> wrapText(String text, FontMetrics fm, int maxWidth) {
            java.util.List<String> lines = new java.util.ArrayList<>();
            StringBuilder line = new StringBuilder();
            for (String word : text.split(" ")) {
                int lineWidth = fm.stringWidth(line + word + " ");
                if (lineWidth > maxWidth && line.length() > 0) {
                    lines.add(line.toString());
                    line = new StringBuilder();
                }
                line.append(word).append(" ");
            }
            if (line.length() > 0) {
                lines.add(line.toString());
            }
            return lines;
        }
    }
}
