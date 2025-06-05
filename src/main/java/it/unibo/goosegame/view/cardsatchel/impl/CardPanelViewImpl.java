package it.unibo.goosegame.view.cardsatchel.impl;

import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.view.cardsatchel.api.CardPanelView;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A reusable panel for displaying a card, its image, name, and description, with click-to-toggle description.
 * Optionally, a button can be shown (e.g., Play) with a callback.
 */
public final class CardPanelViewImpl extends JPanel implements CardPanelView {
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_WIDTH = 120;
    private static final int DEFAULT_HEIGHT = 180;
    private static final int CARD_PADDING = 16;
    private static final int BUTTON_WIDTH = 60;
    private static final int BUTTON_HEIGHT = 28;
    private static final int BORDER_THICKNESS = 6;
    private static final int BORDER_ARC = 16;
    private static final int NAME_FONT_DIV = 12;
    private static final int DESC_FONT_DIV = 16;
    private static final Color CARD_COLOR = new Color(210, 180, 140);
    private static final Color BORDER_COLOR = new Color(101, 67, 33);

    private Card card;
    private boolean showDescription;
    private final JButton actionButton;
    private final int width;
    private final int height;

    /**
     * Constructs a CardPanelView.
     * @param card the card to display
     * @param showButton whether to show the action button
     * @param buttonText the text for the button (ignored if showButton is false)
     * @param buttonAction the action to perform when the button is pressed (can be null)
     * @param width preferred width
     * @param height preferred height
     */
    public CardPanelViewImpl(final Card card, final boolean showButton, final String buttonText,
     final Consumer<Card> buttonAction, final int width, final int height) {
        this.card = card;
        this.width = width;
        this.height = height;
        setOpaque(false);
        setLayout(null);
        if (showButton) {
            actionButton = new JButton(buttonText);
            actionButton.setVisible(false);
            actionButton.setFocusable(false);
            actionButton.setMargin(new Insets(2, 8, 2, 8));
            actionButton.addActionListener((final java.awt.event.ActionEvent e) -> {
                if (this.card != null && buttonAction != null) {
                    buttonAction.accept(this.card);
                }
            });
            add(actionButton);
        } else {
            actionButton = null;
        }
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (CardPanelViewImpl.this.card != null) {
                    showDescription = !showDescription;
                    if (actionButton != null) {
                        actionButton.setVisible(showDescription);
                    }
                    repaint();
                }
            }
        });
    }

    /**
     * Constructs a CardPanelView with default width and height.
     * @param card the card to display
     * @param showButton whether to show the action button
     * @param buttonText the text for the button (ignored if showButton is false)
     * @param buttonAction the action to perform when the button is pressed (can be null)
     */
    public CardPanelViewImpl(final Card card, final boolean showButton, final String buttonText,
     final Consumer<Card> buttonAction) {
        this(card, showButton, buttonText, buttonAction, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Sets the card to be displayed in this panel.
     * @param card the card to display
     */
    @Override
    public void setCard(final Card card) {
        this.card = card;
        showDescription = false;
        if (actionButton != null) {
            actionButton.setVisible(false);
        }
        repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    /**
     * {@inheritDoc}
     */
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
        final int padding = CARD_PADDING;

        // Button position
        if (actionButton != null) {
            final int btnW = BUTTON_WIDTH;
            final int btnH = BUTTON_HEIGHT;
            actionButton.setBounds(x + cardW - btnW - padding, y + cardH - btnH - padding, btnW, btnH);
        }

        // Card background
        g.setColor(CARD_COLOR);
        g.fillRoundRect(x, y, cardW, cardH, BORDER_ARC, BORDER_ARC);
        // Border
        final Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(BORDER_COLOR);
        g2.setStroke(new BasicStroke(BORDER_THICKNESS));
        g2.drawRoundRect(x, y, cardW, cardH, BORDER_ARC, BORDER_ARC);
        g2.dispose();

        // Card content
        if (card != null) {
            g.setColor(Color.BLACK);
            final String name = card.getName();
            final int maxNameWidth = cardW - 2 * padding;
            float fontSize = cardH / (float) NAME_FONT_DIV;
            Font font = getFont().deriveFont(Font.BOLD, fontSize);
            FontMetrics fm = g.getFontMetrics(font);
            int nameW = fm.stringWidth(name);
            while (nameW > maxNameWidth && fontSize > 8) {
                fontSize -= 1f;
                font = getFont().deriveFont(Font.BOLD, fontSize);
                fm = g.getFontMetrics(font);
                nameW = fm.stringWidth(name);
            }
            g.setFont(font);
            int nameX = x + (cardW - nameW) / 2;
            if (nameX < x + padding) {
                nameX = x + padding;
            }
            g.drawString(name, nameX, y + fm.getAscent() + padding);

            if (showDescription) {
                g.setFont(getFont().deriveFont(cardH / (float) DESC_FONT_DIV));
                fm = g.getFontMetrics();
                final String desc = card.getDescription();
                final int descX = x + padding;
                int descY = y + cardH / 4;
                final int maxWidth = cardW - 2 * padding;
                for (final String line : wrapText(desc, fm, maxWidth)) {
                    g.drawString(line, descX, descY);
                    descY += fm.getHeight();
                }
            } else {
                // Draw image if present
                final String imgPath = "/img/cards" + card.getName() + "_image.png";
                final java.net.URL imgURL = getClass().getResource(imgPath);
                if (imgURL != null) {
                    final ImageIcon icon = new ImageIcon(imgURL);
                    final Image img = icon.getImage();

                    final int nameHeight = fm.getHeight() + padding;
                    final int availableHeight = cardH - nameHeight - BUTTON_HEIGHT - 2 * padding;
                    final int imgW = cardW - 2 * padding;
                    final int imgH = (int) (availableHeight * 1.1);
                    final int imgX = x + padding;
                    final int imgY = y + nameHeight + padding;
                    g.drawImage(img, imgX, imgY, imgW, imgH, this);
                }
            }
        }
    }

    /**
     * Utility for word wrap.
     * @param text the text to wrap
     * @param fm the font metrics
     * @param maxWidth the maximum width for a line
     * @return a list of wrapped lines
     */
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
