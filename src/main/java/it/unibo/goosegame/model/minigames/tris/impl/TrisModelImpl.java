package it.unibo.goosegame.model.minigames.tris.impl;

import java.util.List;
import java.util.ArrayList;
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
public class TrisModelImpl implements TrisModel{

    /**
     * A set of all possible winning lines (rows, columns, diagonals).
     */
    private final static Set<Set<Position>> WINNING_LINES = Set.of(
        Set.of(new Position(0, 0), new Position(0, 1), new Position(0, 2)),
        Set.of(new Position(1, 0), new Position(1, 1), new Position(1, 2)),
        Set.of(new Position(2, 0), new Position(2, 1), new Position(2, 2)),
        Set.of(new Position(0, 0), new Position(1, 0), new Position(2, 0)),
        Set.of(new Position(0, 1), new Position(1, 1), new Position(2, 1)),
        Set.of(new Position(0, 2), new Position(1, 2), new Position(2, 2)),
        Set.of(new Position(0, 0), new Position(1, 1), new Position(2, 2)),
        Set.of(new Position(0, 2), new Position(1, 1), new Position(2, 0))
        );
    private final static int GRID_SIZE = 3;
    private Map<Position, Player> grid = new HashMap<>();
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
    public void resetGame() {
        this.grid.clear();
        this.currentPlayer = Player.HUMAN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getResult() {
        if (this.checkWin()) {
           for(Set<Position> line : WINNING_LINES) {
                if(line.stream().allMatch(p -> this.grid.get(p) == Player.HUMAN)) {
                    return 1;
                } else if(line.stream().allMatch(p -> this.grid.get(p) == Player.PC)) {
                    return -1;
                }   
            } 
        } else if (this.isFull()) {
            return GRID_SIZE;
        }
        return 0;
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
    public boolean makeHumanMove(Position position) {
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
        if(this.currentPlayer != Player.PC) {
            return;
        }

        // Try to win
        for(Position pos: availablePos()) {
            this.grid.put(pos, Player.PC);
            if(checkWin()) {
                this.currentPlayer = Player.HUMAN;
                return;
            }
            this.grid.remove(pos);
        }

        // Try to block the human 
        for(Position pos: availablePos()) {
            this.grid.put(pos, Player.HUMAN);
            if(checkWin()) {
                this.grid.remove(pos);
                this.grid.put(pos, Player.PC);
                this.currentPlayer = Player.HUMAN;
                return;
            }
            this.grid.remove(pos);
        }

        // Make a random move 
        List<Position> avPos = availablePos();
        if(!avPos.isEmpty()) {
            Position random = avPos.get(new Random().nextInt(avPos.size()));
            this.grid.put(random, Player.PC);
            this.currentPlayer = Player.HUMAN;
        }
        
    }

    /**
     * @return a list of all available positions on the board
     */
    private List<Position> availablePos() {
        List<Position> avPos = new ArrayList<>();
        for(int i=0; i<GRID_SIZE; i++) {
            for(int j=0; j<GRID_SIZE; j++) {
                Position checkPos = new Position(i, j);
                if(!this.grid.containsKey(checkPos)) {
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
        return this.grid.size() == GRID_SIZE*GRID_SIZE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHuman(Position position) {
        return this.grid.containsKey(position) && this.grid.get(position).equals(Player.HUMAN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPc(Position position) {
        return this.grid.containsKey(position) && this.grid.get(position).equals(Player.PC);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkWin() {
        return WINNING_LINES.stream()
            .anyMatch(l -> l.stream().allMatch(p -> this.grid.containsKey(p) && this.grid.get(p) == Player.HUMAN)
                || l.stream().allMatch(p -> this.grid.containsKey(p) && this.grid.get(p) == Player.PC));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStatus() {
        if(checkWin()) {
            return "Game Over!";
        } else {
            return "Your turn!";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Position, Player> getGrid() {
        return this.grid;
    }
}
