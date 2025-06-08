package it.unibo.goosegame.model.gameboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goosegame.model.gameboard.api.TurnManager;
import it.unibo.goosegame.model.gameboard.impl.TurnManagerImpl;
import it.unibo.goosegame.model.player.api.Player;
import it.unibo.goosegame.model.player.impl.PlayerImpl;

/**
 * Unit tests for {@link TurnManagerImpl}.
 */
class TestTurnManagerImpl {
    private Player p1, p2, p3;
    private List<Player> players;

    /**
    * Initializes a three Players list before each test.
     */
    @BeforeEach
    void init() {
        p1 = new PlayerImpl("Lucia", 0);
        p2 = new PlayerImpl("Giuseppe", 1);
        p3 = new PlayerImpl("Giada", 2);
        this.players = List.of(p1, p2, p3);
    }

    /**
     * Tests that the constructor accepts valid player list,
     * and throws IllegalArgumentException for invalid player list.
     */
    @Test
    void testValidPlayers() {
        final TurnManager tm = new TurnManagerImpl(players);
        assertEquals(p1, tm.getCurrentPlayer());
        assertThrows(IllegalArgumentException.class, () -> new TurnManagerImpl(List.of()));
        assertThrows(IllegalArgumentException.class, () -> new TurnManagerImpl(List.of(p1)));
        assertThrows(
            IllegalArgumentException.class, 
            () -> new TurnManagerImpl(
                List.of(
                    p1,
                    p2,
                    p3,
                    new PlayerImpl("Samuele", 3), 
                    new PlayerImpl("Matteo", 4)
                )
            )
        );
    }

    /**
     * Tests the normal turn order rotation.
     */
    @Test
    void testNextTurn() {
        final TurnManager tm = new TurnManagerImpl(players);
        assertEquals(p1, tm.getCurrentPlayer());
        assertEquals(p2, tm.nextTurn());
        assertEquals(p3, tm.nextTurn());
        assertEquals(p1, tm.nextTurn());
    }

}
