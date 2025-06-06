package it.unibo.goosegame.model.gamemenu.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.goosegame.controller.gameboard.impl.GameBoardImpl;
import it.unibo.goosegame.model.gamemenu.api.MenuLogic;
import it.unibo.goosegame.model.player.api.Player;
import it.unibo.goosegame.model.player.impl.PlayerImpl;
import it.unibo.goosegame.view.gamemenu.api.GameMenuInterface;
/**
 * The MenuLogicImpl class implements the logic behind the game menu, 
 * handling the player management (adding players, checking the maximum number of players).
 */
public class MenuLogicImpl implements MenuLogic {

    private static final int MAX_PLAYERS = 4;
    private static final int CAR = 15;
    /**
     * Reference to the GameMenu view.
     * This is assumed to be owned by this class and not modified externally after being passed in the constructor.
    */
    private final GameMenuInterface view;
    private final List<Player> players;
    private int playerCount;

    /**
     * @param view the GameMenu UI associated with this logic
     */
    @SuppressFBWarnings(value = "EI2", justification = "Direct reference to the view is intentional in MVC pattern.")
    public MenuLogicImpl(final GameMenuInterface view) {
        this.players = new ArrayList<>();
        this.view = Objects.requireNonNull(view);
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void startGame() {
        final long validPlayers = this.players.stream()
            .filter(p -> p != null && p.getName() != null && !p.getName().isBlank())
            .count();
        if (validPlayers < 2) {
            JOptionPane.showMessageDialog(null, "Please enter at least two valid names");
            return;
        }
        this.view.dispose();
        new GameBoardImpl(players);
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void addPlayer() {
        final String playerName = view.getPlayerName();
        if (!playerName.isBlank()) {
            if (this.players.size() == MAX_PLAYERS) {
               JOptionPane.showMessageDialog(null, "Maximum Players Reached.");
            } else if (playerName.length() > CAR) {
               JOptionPane.showMessageDialog(null, "Maximum Players Reached.");
            } else if (players.stream().noneMatch(p -> p.getName().equalsIgnoreCase(playerName))) {
                this.players.add(new PlayerImpl(playerName, playerCount));
                this.playerCount++;
                this.updatePlayerList();
            } else {
                JOptionPane.showMessageDialog(null, "Player Name Already Existing.");
            }
            this.view.updatePlayerField();
        }
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void showInstructions() {
        this.view.showInstructions();
    }

   /**
    * {@inheritDoc}
    */
    @Override
    public int getPlayersCount() {
        return this.playerCount;
    }

    /**
     * Updates the player list displayed on the view, showing the names of all players.
     * This method generates a formatted string with the player names and updates the player label.
     */
    private void updatePlayerList() {
        final StringBuilder playerListText = new StringBuilder("PLAYERS: ");
        for (int i = 0; i < this.players.size(); i++) {
            playerListText
                .append(' ')
                .append(i + 1)
                .append(") ")
                .append(this.players.get(i).getName());
        }
        playerListText.append(' ');
        this.view.updatePlayerLabel(playerListText.toString());
    }
}
