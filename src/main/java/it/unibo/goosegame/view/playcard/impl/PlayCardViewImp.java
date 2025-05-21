package it.unibo.goosegame.view.playcard.impl;

import it.unibo.goosegame.controller.playcard.PlayCardController;
import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.utilities.Player;
import it.unibo.goosegame.view.playcard.api.PlayerCardView;

import javax.swing.JDialog;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayCardView: pannello che mostra una singola carta (pescata o dal satchel) e permette di giocarla,
 * lanciarla o rimetterla nel satchel.
 * Si chiude solo dopo una mossa (gioca/lancia) o se si rimette la carta nel satchel.
 */
public final class PlayCardViewImp extends JDialog implements PlayerCardView {
    private static final long serialVersionUID = 1L;
    private static final int DIALOG_WIDTH = 400;
    private static final int DIALOG_HEIGHT = 350;
    private static final int CARD_PANEL_WIDTH = 400;
    private static final int CARD_PANEL_HEIGHT = 200;
    private static final int CARD_X = 50;
    private static final int CARD_Y = 20;
    private static final int CARD_W = 300;
    private static final int CARD_H = 180;
    private static final int CARD_ARC = 16;
    private static final int BORDER_R = 101;
    private static final int BORDER_G = 67;
    private static final int BORDER_B = 33;
    private static final int CARD_COLOR_R = 210;
    private static final int CARD_COLOR_G = 180;
    private static final int CARD_COLOR_B = 140;
    private static final float NAME_FONT_SIZE = 20f;
    private static final float DESC_FONT_SIZE = 14f;
    private static final int NAME_X = 60;
    private static final int NAME_Y = 60;
    private static final int DESC_X = 60;
    private static final int DESC_Y = 100;

    private final transient PlayCardController controller;
    private final Card card;
    @SuppressFBWarnings(
    value = "SE_TRANSIENT_FIELD_NOT_RESTORED",
    justification = "UI-only field, not needed after deserialization"
)
    private final transient List<Player> otherPlayers;

    /**
     * Constructs the PlayCardViewImp dialog.
     * @param parent the parent JFrame (can be null)
     * @param controller the PlayCardController
     * @param card the card to display/play
     */
    @SuppressFBWarnings(
    value = "EI2",
    justification = ""
)
    public PlayCardViewImp(
            final JFrame parent,
            final PlayCardController controller,
            final Card card
    ) {
        super(parent, "Play Card", true);
        this.controller = controller;
        this.card = card;
        this.otherPlayers = new ArrayList<>(controller.getOtherPlayers());

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));

        final JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(CARD_COLOR_R, CARD_COLOR_G, CARD_COLOR_B));
                g.fillRoundRect(CARD_X, CARD_Y, CARD_W, CARD_H, CARD_ARC, CARD_ARC);
                g.setColor(new Color(BORDER_R, BORDER_G, BORDER_B));
                g.drawRoundRect(CARD_X, CARD_Y, CARD_W, CARD_H, CARD_ARC, CARD_ARC);
                g.setColor(Color.BLACK);
                g.setFont(getFont().deriveFont(Font.BOLD, NAME_FONT_SIZE));
                g.drawString(card.getName(), NAME_X, NAME_Y);
                g.setFont(getFont().deriveFont(DESC_FONT_SIZE));
                g.drawString(card.getDescription(), DESC_X, DESC_Y);
            }
        };
        cardPanel.setPreferredSize(new Dimension(CARD_PANEL_WIDTH, CARD_PANEL_HEIGHT));
        add(cardPanel, BorderLayout.CENTER);

        final JPanel buttonPanel = new JPanel();
        final JButton playButton = new JButton("Play");
        final JButton throwButton = new JButton("Throw");
        final JButton addToSatchelButton = new JButton("Metti nel satchel");

        final JComboBox<Player> playerCombo = new JComboBox<>(otherPlayers.toArray(new Player[0]));
        playerCombo.setVisible(this.controller.isMalusThrowable(this.card)
                || this.controller.isRemoveOpponent(this.card));

        playButton.setEnabled(this.controller.isRemoveSelf(this.card)
                || this.controller.isMalusNotThrowable(this.card)
                || this.controller.isBonus(this.card));
        throwButton.setEnabled(this.controller.isMalusThrowable(this.card)
                || this.controller.isRemoveOpponent(this.card));
        addToSatchelButton.setEnabled(this.controller.canPlayCardFromSatchel(this.card));

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PlayCardViewImp.this.controller.playCardOnSelf();
                closeAndNotifySatchel();
            }
        });

        throwButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final Player target = (Player) playerCombo.getSelectedItem();
                if (target != null) {
                    PlayCardViewImp.this.controller.playCardOnPlayer(target);
                    closeAndNotifySatchel();
                }
            }
        });

        addToSatchelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PlayCardViewImp.this.controller.addCardToSatchel();
                closeThis();
            }
        });

        buttonPanel.add(playButton);
        if (throwButton.isEnabled()) {
            buttonPanel.add(throwButton);
            buttonPanel.add(playerCombo);
        }
        buttonPanel.add(addToSatchelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    /**
     * Shows the card dialog.
     * @param card the card to show
     * @param otherPlayers the list of other players
     */
    @Override
    public void showCard(final Card card, final List<Player> otherPlayers) {
        // In questa implementazione, la card viene già mostrata dal costruttore.
        // Questo metodo può essere usato per rendere visibile la dialog.
        setVisible(true);
    }

    /**
     * Closes the dialog.
     */
    @Override
    public void close() {
        setVisible(false);
        dispose();
    }

    /**
     * Updates the card view (not implemented in this version).
     * @param card the card to update
     * @param otherPlayers the list of other players
     */
    @Override
    public void update(final Card card, final List<Player> otherPlayers) {
        // Aggiorna la UI se necessario (non implementato in questa versione)
        // Si può aggiungere logica per aggiornare la card e i bottoni
    }

    private void closeAndNotifySatchel() {
        setVisible(false);
        dispose();
        // Qui puoi notificare la main view/controller di chiudere anche il satchel
    }

    private void closeThis() {
        setVisible(false);
        dispose();
        // Qui torni semplicemente al satchel, che resta aperto
    }
}
