package it.unibo.goosegame.view.playcard.impl;

import it.unibo.goosegame.controller.playcard.PlayCardController;
import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.utilities.Player;
import it.unibo.goosegame.view.playcard.api.PlayerCardView;
import it.unibo.goosegame.view.general.CardPanelView;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * PlayCardView: panel that shows a single card (drawn or from the satchel) and allows the player to play it,
 * throw it, or put it back in the satchel.
 * The dialog closes only after a move (play/throw) or if the card is put back in the satchel.
 */
public final class PlayCardViewImp extends JDialog implements PlayerCardView {
    private static final long serialVersionUID = 1L;
    private static final int DIALOG_WIDTH = 400;
    private static final int DIALOG_HEIGHT = 350;
    private static final int CARD_PANEL_WIDTH = 300;
    private static final int CARD_PANEL_HEIGHT = 180;

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

        // Use CardPanelView for card visualization
        final CardPanelView cardPanel = new CardPanelView(card, false, null, null, CARD_PANEL_WIDTH, CARD_PANEL_HEIGHT);
        add(cardPanel, BorderLayout.CENTER);

        final JPanel buttonPanel = new JPanel();
        final JButton playButton = new JButton("Play");
        final JButton throwButton = new JButton("Throw");
        final JButton addToSatchelButton = new JButton("Put in satchel");

        final JComboBox<Player> playerCombo = new JComboBox<>(otherPlayers.toArray(new Player[0]));
        playerCombo.setVisible(this.controller.isBonusThrowable(this.card)
                || this.controller.isRemoveOpponent(this.card));

        playButton.setEnabled(this.controller.isRemoveSelf(this.card)
                || this.controller.isMalusNotThrowable(this.card)
                || this.controller.isBonus(this.card));
        throwButton.setEnabled(this.controller.isBonusThrowable(this.card)
                || this.controller.isRemoveOpponent(this.card));
        addToSatchelButton.setEnabled(this.controller.canPlayCardFromSatchel(this.card));

        playButton.addActionListener(e -> {
            controller.playCardOnSelf();
            closeAndNotifySatchel();
        });

        throwButton.addActionListener(e -> {
            final Player target = (Player) playerCombo.getSelectedItem();
            if (target != null) {
                controller.playCardOnPlayer(target);
                closeAndNotifySatchel();
            }
        });

        addToSatchelButton.addActionListener(e -> {
            controller.addCardToSatchel();
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

    /**
     * Shows the card dialog.
     * @param card the card to show
     * @param otherPlayers the list of other players
     */
    @Override
    public void showCard(final Card card, final List<Player> otherPlayers) {
        // In this implementation, the card is already shown by the constructor.
        // This method can be used to make the dialog visible.
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
        // Update the UI if needed (not implemented in this version)
        // You can add logic to update the card and the buttons
    }

    private void closeAndNotifySatchel() {
        setVisible(false);
        dispose();
        // Here you can notify the main view/controller to also close the satchel
    }

    private void closeThis() {
        setVisible(false);
        dispose();
        // Here you simply return to the satchel, which remains open
    }
}
