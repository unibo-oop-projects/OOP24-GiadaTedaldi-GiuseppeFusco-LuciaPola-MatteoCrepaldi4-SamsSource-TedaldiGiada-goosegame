package it.unibo.goosegame.model.tris.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import it.unibo.goosegame.model.tris.api.TrisModel;
import it.unibo.goosegame.utilities.Pair;
import it.unibo.goosegame.utilities.Position;

public class TrisModelImpl implements TrisModel{

    enum Player {
        HUMAN, PC;
    }

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
    private final static int GRID_SIZE = 9;
    private Map<Position, Player> grid = new HashMap<>();
    private Player currentPlayer;

    public TrisModelImpl() {
        this.resetGame();
    }

    @Override
    public void resetGame() {
        this.grid.clear();
        this.currentPlayer = Player.HUMAN;
    }

    @Override
    public Pair<String, Integer> getResult() {
        if (this.checkWin()) {
            return new Pair<String,Integer>(this.currentPlayer == Player.HUMAN? "You win!" : "Pc wins!", 1);
        } else if (this.isFull()) {
            return new Pair<String,Integer>("Draw", 0);
        }
        return new Pair<String,Integer>("Still playing!", -1);
    }

    @Override
    public String getName() {
        return "Tris(Tic-Tac-Toe)";
    }

    @Override
    public boolean isOver() {
        return this.checkWin() || this.isFull();
    }

    @Override
    public boolean makeMove(Position position) {
        if (!this.grid.containsKey(position) && this.currentPlayer == Player.HUMAN) {
            this.grid.put(position, Player.HUMAN);
            this.currentPlayer = Player.PC;
            return true;
        }
        return false;
    }

    @Override
    public boolean isFull() {
        return this.grid.size() == GRID_SIZE;
    }

    @Override
    public boolean isHuman(Position position) {
        return this.grid.containsKey(position) && this.grid.get(position).equals(Player.HUMAN);
    }

    @Override
    public boolean isPc(Position position) {
        return this.grid.containsKey(position) && this.grid.get(position).equals(Player.PC);
    }

    @Override
    public boolean checkWin() {
        return WINNING_LINES.stream()
            .anyMatch(l -> l.stream().allMatch(p -> this.grid.containsKey(p) && this.grid.get(p) == Player.HUMAN)
                || l.stream().allMatch(p -> this.grid.containsKey(p) && this.grid.get(p) == Player.PC));
    }
}
