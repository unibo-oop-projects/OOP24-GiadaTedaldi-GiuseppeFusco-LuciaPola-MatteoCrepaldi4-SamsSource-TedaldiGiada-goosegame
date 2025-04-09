package it.unibo.goosegame.model.minigames.tris.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import it.unibo.goosegame.model.minigames.tris.api.TrisModel;
import it.unibo.goosegame.utilities.Position;

public class TrisModelImpl implements TrisModel{

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

    public TrisModelImpl() {
        this.resetGame();
    }

    @Override
    public void resetGame() {
        this.grid.clear();
        this.currentPlayer = Player.HUMAN;
    }

    @Override
    public int getResult() {
        if (this.checkWin()) {
            if(this.currentPlayer == Player.HUMAN) {
                return -1;
            } else {
                return 1;
            }
            
        } else if (this.isFull()) {
            return GRID_SIZE;
        }
        return 0;
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
    public boolean makeHumanMove(Position position) {
        if (!this.grid.containsKey(position) && this.currentPlayer == Player.HUMAN) {
            this.grid.put(position, Player.HUMAN);
            this.currentPlayer = Player.PC;
            return true;
        }
        return false;
    }

    @Override
    public void makePcMove() {
        if(this.currentPlayer != Player.PC) {
            return;
        }

        for(Position pos: availablePos()) {
            this.grid.put(pos, Player.PC);
            if(checkWin()) {
                this.currentPlayer = Player.HUMAN;
                return;
            }
            this.grid.remove(pos);
        }

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

        List<Position> avPos = availablePos();
        if(!avPos.isEmpty()) {
            Position random = avPos.get(new Random().nextInt(avPos.size()));
            this.grid.put(random, Player.PC);
            this.currentPlayer = Player.HUMAN;
        }
        
    }

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

    @Override
    public boolean isFull() {
        return this.grid.size() == GRID_SIZE*GRID_SIZE;
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

    @Override
    public String getStatus() {
        if(checkWin()) {
            return "Game Over!";
        } else {
            return "Your turn!";
        }
    }

}
