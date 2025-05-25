package it.unibo.goosegame.model.gameboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goosegame.model.gameboard.impl.TurnManager;
import it.unibo.goosegame.utilities.player.api.Player;
import it.unibo.goosegame.utilities.player.impl.PlayerImpl;

/**
 * Unit tests for {@link TurnManager}.
 */
class TestTurnManager {
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
        final TurnManager tm = new TurnManager(players);
        assertEquals(p1, tm.getCurrentPlayer());
        assertThrows(IllegalArgumentException.class, () -> new TurnManager(List.of()));
        assertThrows(IllegalArgumentException.class, () -> new TurnManager(List.of(p1)));
        assertThrows(
            IllegalArgumentException.class, 
            () -> new TurnManager(
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
     * Tests the normal turn order rotation without any skipped turns.
     */
    @Test
    void testNextTurnNoSkips() {
        final TurnManager tm = new TurnManager(players);
        assertEquals(p1, tm.getCurrentPlayer());
        assertEquals(p2, tm.nextTurn());
        assertEquals(p3, tm.nextTurn());
        assertEquals(p1, tm.nextTurn());
    }

    /**
     * Tests that a player skipping 1 turn is correctly skipped in the turn rotation.
     */
    @Test
    void testSkipNextTurn() {
        final TurnManager tm = new TurnManager(players);
        tm.skipNextTurn(p2, 1);
        assertEquals(p3, tm.nextTurn());
        assertEquals(p1, tm.nextTurn());
        assertEquals(p2, tm.nextTurn());
    }

    /**
     * Tests that a player skipping multiple turns is correctly skipped multiple times.
     */
    @Test
    void testSkipNextTurnMultipleSkips() {
        final TurnManager tm = new TurnManager(players);
        tm.skipNextTurn(p2, 2);
        assertEquals(p3, tm.nextTurn());
        assertEquals(p1, tm.nextTurn());
        assertEquals(p3, tm.nextTurn());
        assertEquals(p1, tm.nextTurn());
        assertEquals(p2, tm.nextTurn());
    }

    /**
     * Tests that skipNextTurn throws an exception when called with a player not in the current player list.
     */
    @Test
    void testSkipNextTurnInvalidPlayers() {
        final TurnManager tm = new TurnManager(players);
        final Player unknown = new PlayerImpl("Unknown", 0);
        assertThrows(IllegalArgumentException.class, () -> tm.skipNextTurn(unknown, 1));
    }

    /**
     * Tests that skipNextTurn throws an exception when the number of turns to skip is zero or negative.
     */
    @Test
    void testSkipNextTurnInvalidTurns() {
        final TurnManager tm = new TurnManager(players);
        assertThrows(IllegalArgumentException.class, () -> tm.skipNextTurn(p1, 0));
        assertThrows(IllegalArgumentException.class, () -> tm.skipNextTurn(p1, -1));
    }

}
