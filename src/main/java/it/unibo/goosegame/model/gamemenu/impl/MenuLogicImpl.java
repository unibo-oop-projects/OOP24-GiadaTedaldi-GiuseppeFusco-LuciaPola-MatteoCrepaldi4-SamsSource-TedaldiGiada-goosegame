package it.unibo.goosegame.model.gamemenu.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.goosegame.controller.gameboard.impl.GameBoardImpl;
import it.unibo.goosegame.model.gamemenu.api.MenuLogic;
import it.unibo.goosegame.view.gamemenu.impl.GameMenu;
/**
 * The MenuLogicImpl class implements the logic behind the game menu, 
 * handling the player management (adding players, checking the maximum number of players).
 */
public class MenuLogicImpl implements MenuLogic {

    private static final int MAX_PLAYERS = 4;
    /**
     * Reference to the GameMenu view.
     * This is assumed to be owned by this class and not modified externally after being passed in the constructor.
    */
    private final GameMenu view;
    private final List<String> players;
    private int playerCount;

    /**
     * @param view the GameMenu UI associted with this logic
     */
    @SuppressFBWarnings("EI2")
    public MenuLogicImpl(final GameMenu view) {
        this.players = new ArrayList<>();
        this.view = Objects.requireNonNull(view);
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void startGame() {
        if (players.size() == 1) {
            JOptionPane.showMessageDialog(view, "Minimum number of players: 2");
        } else {
            @SuppressWarnings("unused")
            final GameBoardImpl gameBoard = new GameBoardImpl(playerCount);
        }
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void addPlayer() {
        final String playerName = view.getPlayerName();
        if (!playerName.isBlank()) {
            if (players.size() == MAX_PLAYERS) {
               JOptionPane.showMessageDialog(view, "Maximum Players Reached.");
            } else if (players.contains(playerName)) {
                JOptionPane.showMessageDialog(view, "Player Name Already Existing.");
            } else {
                players.add(playerName);
                playerCount++;
                updatePlayerList();
            }
            view.updatePlayerField();
        }
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void showInstructions() {
        view.showInstructions();
    }

   /**
    * {@inheritDoc}
    */
    @Override
    public int getPlayersCount() {
        return playerCount;
    }

    /**
     * Updates the player list displayed on the view, showing the names of all players.
     * This method generates a formatted string with the player names and updates the player label.
     */
    private void updatePlayerList() {
        final StringBuilder playerListText = new StringBuilder("PLAYERS: ");
        for (int i = 0; i < players.size(); i++) {
            playerListText
                .append(' ')
                .append(i + 1)
                .append(") ")
                .append(players.get(i));
        }
        playerListText.append(' ');
        view.updatePlayerLabel(playerListText.toString());
    }
}
