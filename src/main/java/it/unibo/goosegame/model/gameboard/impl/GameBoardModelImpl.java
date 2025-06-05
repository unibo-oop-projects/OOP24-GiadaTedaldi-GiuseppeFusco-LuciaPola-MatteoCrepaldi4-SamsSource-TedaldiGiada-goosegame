package it.unibo.goosegame.model.gameboard.impl;

import it.unibo.goosegame.controller.cell.api.Cell;
import it.unibo.goosegame.model.gameboard.api.DoubleDice;
import it.unibo.goosegame.model.gameboard.api.GameBoardModel;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.model.player.api.Player;
import it.unibo.goosegame.model.turnmanager.api.TurnManager;
import it.unibo.goosegame.utilities.Card;

import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.JOptionPane;

/**
 * Implementation of {@link GameBoardModel}.
 */
public final class GameBoardModelImpl implements GameBoardModel {
    private final TurnManager turnManager;
    private final DoubleDice dice;
    private final List<Cell> cells;
    private boolean hasPlayerMoved;
    private GameState gameState;
    private Timer timer;
    private Boolean isGameOver;

    /**
     * Constructor for the gameboard model element.
     *
     * @param turnManager number of players
     * @param cells list of cells representing the game board
     */
    public GameBoardModelImpl(final TurnManager turnManager, final List<Cell> cells) {
        this.turnManager = turnManager;
        this.cells = List.copyOf(cells);
        this.dice = new DoubleDiceImpl();
        this.hasPlayerMoved = false;
        this.isGameOver = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWindowTitle() {
        return "Goose Game";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void throwDices() {
        if (this.hasPlayerMoved) {
            JOptionPane.showMessageDialog(null, turnManager.getCurrentPlayer().getName() + " has already moved this turn.");
            return;
        }

        gameState = GameState.NOT_STARTED;

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                dice.rollDices();
                return null;
            }

            @Override
            protected void done() {
                final int result = 58;//dice.getResult();
                JOptionPane.showMessageDialog(null, result);

                move(turnManager.getCurrentPlayer(), result, true);

                final Cell newCell = cells.get(turnManager.getCurrentPlayer().getPosition());
                newCell.triggerMinigame();

                 timer = new Timer(100, e -> {
                    gameState = newCell.checkGameState();

                    if (gameState == GameState.WON || gameState == GameState.LOST || gameState == GameState.TIE) {
                        stopTimer();
                    }
                 });

                 timer.start();
                 hasPlayerMoved = true;
            }
        }.execute();
    }

    /**
     *  Utility method to stop the timer and handle the end of the minigame.
     */
    private void stopTimer() {
        timer.stop();

        if (gameState == GameState.WON) {
            final Card drawnCard = Card.drawBonusCard();

            JOptionPane.showMessageDialog(
                    null,
                    drawnCard.getDescription(),
                    drawnCard.getName(),
                    JOptionPane.INFORMATION_MESSAGE
            );

            this.turnManager.getCurrentPlayer().getSatchel().addCard(drawnCard);
        } else if (gameState == GameState.LOST) {
                final Card drawnCard = Card.drawMalusCard();

                JOptionPane.showMessageDialog(
                        null,
                        drawnCard.getDescription(),
                        drawnCard.getName(),
                        JOptionPane.INFORMATION_MESSAGE
                );

                if (drawnCard.isRemove()) {
                    this.turnManager.getCurrentPlayer().getSatchel().clearSatchel();
                } else {
                    this.move(getCurrentPlayer(), drawnCard.getSteps(), drawnCard.isBonus());
                }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextTurn() {
        if (this.hasPlayerMoved) {
            JOptionPane.showMessageDialog(null, turnManager.getCurrentPlayer().getName() + " ended their turn.");
            this.turnManager.nextTurn();
            this.hasPlayerMoved = false;
        } else {
            JOptionPane.showMessageDialog(null, "You must move before ending your turn.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(final Player player, final int steps, final boolean isForward) {
        final Cell currentCell = searchPlayer(player);
        final int newPosition = calcMovement(steps, isForward);
        final Cell newCell = cells.get(newPosition);

        player.setIndex(newPosition);
        currentCell.movePlayer(newCell, player);

        if (player.getPosition() == cells.size() - 1) {
            this.isGameOver = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getCurrentPlayer() {
        return turnManager.getCurrentPlayer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        return isGameOver;
    }

    /**
     * Searches for the cell containing the specified player.
     *
     * @param player the player to search for
     * @return the cell containing the player
     */
    private Cell searchPlayer(final Player player) {
        for (final Cell cell : cells) {
            if (cell.containsPlayer(player)) {
                return cell;
            }
        }

        throw new IllegalArgumentException("Player not found on the board.");
    }

    /**
     * Calculates the new position of the player based on the number of steps.
     *
     * @param steps number of steps to move
     * @param isForward true if the movement is forward, false if backward
     * @return the new position of the player on the board
     */
    private int calcMovement(final int steps, final boolean isForward) {
        final int position = cells.indexOf(searchPlayer(turnManager.getCurrentPlayer()));
        final int cellCount = cells.size() - 1;
        final int currentPosition = this.turnManager.getCurrentPlayer().getPosition();

        if (isForward) {
            if (currentPosition + steps > cellCount) {
                return cellCount - (steps - (cellCount - currentPosition));
            } else {
                return position + steps;
            }
        }

        return Math.max(position - steps, 0);
    }
}
