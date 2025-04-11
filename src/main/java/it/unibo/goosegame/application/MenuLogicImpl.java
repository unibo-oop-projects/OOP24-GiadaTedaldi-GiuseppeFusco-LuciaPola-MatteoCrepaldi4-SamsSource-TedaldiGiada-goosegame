package it.unibo.goosegame.application;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class MenuLogicImpl implements MenuLogic {

    private final GameMenu view;
    private final List<String> players;
    int playerCount = 0;

    public MenuLogicImpl(GameMenu view) {
        this.players = new ArrayList<>();
        this.view = view;
    }

    @Override
    public void startGame() {
        //funzione
    }

    @Override
    public void addPlayer() {
        String playerName = view.getPlayerName();
        if(!playerName.trim().isEmpty()) {
            if(players.size() == 6) {
               JOptionPane . showMessageDialog ( view , "Maximum Players Reached.");
            } else if (players.contains(playerName)) {
                JOptionPane . showMessageDialog ( view , "Player Name Already Existing.");
            } else {
                players.add(playerName);
                playerCount++;
                updatePlayerList();
            }
            view.updatePlayerField();
        }
    }

    @Override
    public void showInstructions() {
        view.showInstructions();
    }

    private void updatePlayerList() {
        StringBuilder playerListText = new StringBuilder("PLAYERS: ");
        for(int i = 0; i<players.size(); i++) {
            if(i!=0) {
                playerListText.append("  ").append(i+1).append(") ");
                playerListText.append(players.get(i));
            } else {
                playerListText.append("  ").append(i+1).append(")  ");
                playerListText.append(players.get(i));
            }
        }
        playerListText.append(" ");
        view.updatePlayerLabel(playerListText.toString());
    }
}