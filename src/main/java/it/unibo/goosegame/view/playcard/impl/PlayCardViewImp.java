package it.unibo.goosegame.view.playcard.impl;

import it.unibo.goosegame.controller.playcard.PlayCardController;
import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.utilities.Player;
import it.unibo.goosegame.view.playcard.api.PlayerCardView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * PlayCardView: pannello che mostra una singola carta (pescata o dal satchel) e permette di giocarla, lanciarla o rimetterla nel satchel.
 * Si chiude solo dopo una mossa (gioca/lancia) o se si rimette la carta nel satchel.
 */
public class PlayCardViewImp extends JDialog implements PlayerCardView {
    private final PlayCardController controller;
    private final Card card;
    private final List<Player> otherPlayers;

    /**
     * @param parent finestra principale (può essere null)
     * @param controller controller PlayCard
     * @param card la carta da mostrare/giocare
     */
    public PlayCardViewImp(JFrame parent, PlayCardController controller, Card card) {
        super(parent, "Play Card", true);
        this.controller = controller;
        this.card = card;
        this.otherPlayers = controller.getOtherPlayers();

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 350));

        // Card display (center)
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(210, 180, 140));
                g.fillRoundRect(50, 20, 300, 180, 16, 16);
                g.setColor(new Color(101, 67, 33));
                g.drawRoundRect(50, 20, 300, 180, 16, 16);
                g.setColor(Color.BLACK);
                g.setFont(getFont().deriveFont(Font.BOLD, 20f));
                g.drawString(card.getName(), 60, 60);
                g.setFont(getFont().deriveFont(14f));
                g.drawString(card.getDescription(), 60, 100);
            }
        };
        cardPanel.setPreferredSize(new Dimension(400, 200));
        add(cardPanel, BorderLayout.CENTER);

        // Pulsanti
        JPanel buttonPanel = new JPanel();
        JButton playButton = new JButton("Gioca");
        JButton throwButton = new JButton("Lancia");
        JButton addToSatchelButton = new JButton("Metti nel satchel");

        JComboBox<Player> playerCombo = new JComboBox<>(otherPlayers.toArray(new Player[0]));
        playerCombo.setVisible(this.controller.isMalusThrowable(this.card) || this.controller.isRemoveOpponent(this.card));

        playButton.setEnabled(this.controller.isRemoveSelf(this.card) || this.controller.isMalusNotThrowable(this.card) || this.controller.isBonus(this.card));
        throwButton.setEnabled(this.controller.isMalusThrowable(this.card) || this.controller.isRemoveOpponent(this.card));
        addToSatchelButton.setEnabled(this.controller.canPlayCardFromSatchel(this.card));

        playButton.addActionListener(e -> {
            this.controller.playCardOnSelf();
            closeAndNotifySatchel();
        });

        throwButton.addActionListener(e -> {
            Player target = (Player) playerCombo.getSelectedItem();
            if (target != null) {
                this.controller.playCardOnPlayer(target);
                closeAndNotifySatchel();
            }
        });

        addToSatchelButton.addActionListener(e -> {
            this.controller.addCardToSatchel();
            closeThis();
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

    @Override
    public void showCard(Card card, List<Player> otherPlayers) {
        // In questa implementazione, la card viene già mostrata dal costruttore.
        // Questo metodo può essere usato per rendere visibile la dialog.
        setVisible(true);
    }

    @Override
    public void close() {
        setVisible(false);
        dispose();
    }

    @Override
    public void update(Card card, List<Player> otherPlayers) {
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
