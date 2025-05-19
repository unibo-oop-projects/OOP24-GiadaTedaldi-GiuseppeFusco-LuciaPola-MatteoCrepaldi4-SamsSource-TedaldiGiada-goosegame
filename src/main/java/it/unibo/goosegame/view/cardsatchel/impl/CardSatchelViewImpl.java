package it.unibo.goosegame.view.cardsatchel.impl;

import it.unibo.goosegame.controller.cardsatchel.CardSatchelController;
import it.unibo.goosegame.utilities.Card;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

import it.unibo.goosegame.view.cardsatchel.api.CardSatchelView;

/**
 * Implementation of the CardSatchelView for displaying the player's card satchel.
 */
public final class CardSatchelViewImpl extends JPanel implements CardSatchelView {
    private static final long serialVersionUID = 1L;
    private static final int MAX_CARDS = 6;
    private static final int CARD_WIDTH = 120;
    private static final int CARD_HEIGHT = 180;
    private static final int CARD_PADDING = 16;
    private static final int BUTTON_WIDTH = 60;
    private static final int BUTTON_HEIGHT = 28;
    private static final int BORDER_THICKNESS = 6;
    private static final int BORDER_ARC = 16;
    private static final int NAME_FONT_DIV = 12;
    private static final int DESC_FONT_DIV = 16;
    private static final Color SATCHEL_BROWN = new Color(139, 69, 19);
    private static final Color CARD_COLOR = new Color(210, 180, 140);
    private static final Color BORDER_COLOR = new Color(101, 67, 33);

    private final CardPanel[] cardPanels;
    private final CardSatchelController controller;

    /**
     * Constructs the CardSatchelViewImpl.
     * @param controller the controller for the satchel
     */
    public CardSatchelViewImpl(final CardSatchelController controller) {
        this.controller = controller;
        setBackground(SATCHEL_BROWN); // Marrone sacchetto
        setLayout(new GridLayout(1, MAX_CARDS, 10, 10));
        cardPanels = new CardPanel[MAX_CARDS];
        final List<Card> cards = this.controller.getCards();
        for (int i = 0; i < MAX_CARDS; i++) {
            final Card card = i < cards.size() ? cards.get(i) : null;
            cardPanels[i] = new CardPanel(card);
            add(cardPanels[i]);
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
            cardPanels[i].setCard(card);
        }
        revalidate();
        repaint();
    }

    // Pannello singola carta
    private class CardPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private Card card;
        private boolean showDescription;
        private final JButton playButton;

        CardPanel(final Card card) {
            setOpaque(false);
            setLayout(null); // Layout libero per posizionamento custom
            playButton = new JButton("Gioca");
            playButton.setVisible(false);
            playButton.setFocusable(false);
            playButton.setMargin(new Insets(2, 8, 2, 8));
            playButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    if (CardPanel.this.card != null) {
                        controller.playCard(CardPanel.this.card);
                        // Aggiorna la view dopo aver giocato la carta
                        CardSatchelViewImpl.this.updateCards(controller.getCards());
                    }
                }
            });
            add(playButton);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(final MouseEvent e) {
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
            final int width = CARD_WIDTH;
            final int height = CARD_HEIGHT;
            return new Dimension(width, height);
        }

        public void setCard(final Card card) {
            this.card = card;
            showDescription = false;
            playButton.setVisible(false);
            repaint();
        }

        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);
            final int w = getWidth();
            final int h = getHeight();
            final double aspect = 2.0 / 3.0;
            int cardW = w;
            int cardH = (int) (w / aspect);
            if (cardH > h) {
                cardH = h;
                cardW = (int) (h * aspect);
            }
            final int x = (w - cardW) / 2;
            final int y = (h - cardH) / 2;

            final int padding = CARD_PADDING; // margine interno per testo e contenuti

            // Posiziona il bottone in basso a destra
            final int btnW = BUTTON_WIDTH;
            final int btnH = BUTTON_HEIGHT;
            playButton.setBounds(x + cardW - btnW - padding, y + cardH - btnH - padding, btnW, btnH);

            // Disegna sfondo carta
            g.setColor(card == null ? CARD_COLOR : CARD_COLOR);
            g.fillRoundRect(x, y, cardW, cardH, BORDER_ARC, BORDER_ARC);

            // Disegna bordo più spesso
            final Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(BORDER_COLOR);
            g2.setStroke(new BasicStroke(BORDER_THICKNESS)); // bordo più spesso
            g2.drawRoundRect(x, y, cardW, cardH, BORDER_ARC, BORDER_ARC);
            g2.dispose();

            // Disegna contenuti (nome, descrizione, immagine) in modo proporzionale
            if (card != null) {
                g.setColor(Color.BLACK);
                final String name = card.getName();
                final int maxNameWidth = cardW - 2 * padding;
                float fontSize = cardH / (float) NAME_FONT_DIV;
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
                if (nameX < x + padding) {
                    nameX = x + padding;
                }
                g.drawString(name, nameX, y + fm.getAscent() + padding);

                if (showDescription) {
                    g.setFont(getFont().deriveFont(cardH / (float) DESC_FONT_DIV));
                    fm = g.getFontMetrics();
                    final String desc = card.getDescription();
                    // Word wrap con padding
                    final int descX = x + padding;
                    int descY = y + cardH / 4;
                    final int maxWidth = cardW - 2 * padding;
                    for (final String line : wrapText(desc, fm, maxWidth)) {
                        g.drawString(line, descX, descY);
                        descY += fm.getHeight();
                    }
                } else {
                    // Disegna immagine se presente
                    final String imgPath = "/img/" + card.name() + "_image.png";
                    final java.net.URL imgURL = getClass().getResource(imgPath);
                    if (imgURL != null) {
                        final ImageIcon icon = new ImageIcon(imgURL);
                        final java.awt.Image img = icon.getImage();
                        final int imgW = cardW - 2 * padding;
                        final int imgH = cardH / 2;
                        g.drawImage(img, x + padding, y + cardH / 4, imgW, imgH, this);
                    }
                }
            }
        }

        // Utility per word wrap
        private List<String> wrapText(final String text, final FontMetrics fm, final int maxWidth) {
            final List<String> lines = new ArrayList<>();
            StringBuilder line = new StringBuilder();
            for (final String word : text.split(" ")) {
                final int lineWidth = fm.stringWidth(line + word + " ");
                if (lineWidth > maxWidth && line.length() > 0) {
                    lines.add(line.toString());
                    line = new StringBuilder();
                }
                line.append(word).append(' ');
            }
            if (line.length() > 0) {
                lines.add(line.toString());
            }
            return lines;
        }
    }
}
