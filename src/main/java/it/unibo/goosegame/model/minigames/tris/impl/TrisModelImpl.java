package it.unibo.goosegame.model.minigames.tris.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import it.unibo.goosegame.model.minigames.tris.api.TrisModel;
import it.unibo.goosegame.utilities.Position;

/**
 * Implementation of the {@link TrisModel} interface.
 * This class handles the game logic for a Tris(Tic-Tac-Toe) minigame.
 */
public class TrisModelImpl implements TrisModel {

    /**
     * A set of all possible winning lines (rows, columns, diagonals).
     */
    private static final Set<Set<Position>> WINNING_LINES = Set.of(
        Set.of(new Position(0, 0), new Position(0, 1), new Position(0, 2)),
        Set.of(new Position(1, 0), new Position(1, 1), new Position(1, 2)),
        Set.of(new Position(2, 0), new Position(2, 1), new Position(2, 2)),
        Set.of(new Position(0, 0), new Position(1, 0), new Position(2, 0)),
        Set.of(new Position(0, 1), new Position(1, 1), new Position(2, 1)),
        Set.of(new Position(0, 2), new Position(1, 2), new Position(2, 2)),
        Set.of(new Position(0, 0), new Position(1, 1), new Position(2, 2)),
        Set.of(new Position(0, 2), new Position(1, 1), new Position(2, 0))
        );
    private static final int GRID_SIZE = 3;
    private final Random random = new Random();
    private final Map<Position, Player> grid = new HashMap<>();
    private Player currentPlayer;

    /**
     * Constructs a new instance of {@link TrisModelImpl}.
     */
    public TrisModelImpl() {
        this.resetGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void resetGame() {
        this.grid.clear();
        this.currentPlayer = Player.HUMAN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {
        if (this.hasPlayerWon(Player.HUMAN)) {
            return GameState.WON;
        } else if (this.hasPlayerWon(Player.PC)) {
            return GameState.LOST;
        } else if (this.isFull()) {
            return GameState.TIE;
        }
        return GameState.ONGOING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Tris(Tic-Tac-Toe)";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        return this.checkWin() || this.isFull();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean makeHumanMove(final Position position) {
        if (!this.grid.containsKey(position) && this.currentPlayer == Player.HUMAN) {
            this.grid.put(position, Player.HUMAN);
            this.currentPlayer = Player.PC;
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void makePcMove() {
        if (this.currentPlayer != Player.PC) {
            return;
        }

        // Try to win
        final Position winningPos = this.getWinningMove(Player.PC);
        if (winningPos != null) {
            this.grid.put(winningPos, Player.PC);
            this.currentPlayer = Player.HUMAN;
            return;
        }

        // Try to block the human 
        final Position blockingPos = this.getWinningMove(Player.HUMAN);
        if (blockingPos != null) {
            this.grid.put(blockingPos, Player.PC);
            this.currentPlayer = Player.HUMAN;
            return;
        }

        // Make a random move 
        final List<Position> avPos = availablePos();
        if (!avPos.isEmpty()) {
            final Position randomPos = avPos.get(this.random.nextInt(avPos.size()));
            this.grid.put(randomPos, Player.PC);
            this.currentPlayer = Player.HUMAN;
        }
    }

    /**
     * Finds a winning move for the specified player, if any.
     * 
     * @param player the player to check for a winning move
     * @return the position where the player can win with the next move, or null if no winning move exists
     */
    private Position getWinningMove(final Player player) {
        for (final Position pos: availablePos()) {
            this.grid.put(pos, player);
            final boolean haswon = this.checkWin();
            this.grid.remove(pos);
            if (haswon) {
                return pos;
            }
        }
        return null;
    }

    /**
     * @return a list of all available positions on the board
     */
    private List<Position> availablePos() {
        final List<Position> avPos = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                final Position checkPos = new Position(i, j);
                if (!this.grid.containsKey(checkPos)) {
                    avPos.add(checkPos);
                }
            }
        }
        return avPos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFull() {
        return this.grid.size() == GRID_SIZE * GRID_SIZE;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean isHuman(final Position position) {
        return this.grid.containsKey(position) && this.grid.get(position).equals(Player.HUMAN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPc(final Position position) {
        return this.grid.containsKey(position) && this.grid.get(position).equals(Player.PC);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkWin() {
        return this.hasPlayerWon(Player.HUMAN) || this.hasPlayerWon(Player.PC);
    }

    /**
     * Checks whether the specified player has won the game.
     * 
     * @param player the player to check for a  win condition
     * @return true if the player has won, false otherwise
     */
    private boolean hasPlayerWon(final Player player) {
        return WINNING_LINES.stream()
            .anyMatch(l -> l.stream().allMatch(p -> player.equals(this.grid.get(p))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Position, Player> getGrid() {
        return Collections.unmodifiableMap(this.grid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGrid(final Map<Position, Player> newGrid) {
        if (newGrid == null) {
            throw new IllegalArgumentException("Empty grid");
        }
        this.grid.clear();
        this.grid.putAll(newGrid);
    }
}
