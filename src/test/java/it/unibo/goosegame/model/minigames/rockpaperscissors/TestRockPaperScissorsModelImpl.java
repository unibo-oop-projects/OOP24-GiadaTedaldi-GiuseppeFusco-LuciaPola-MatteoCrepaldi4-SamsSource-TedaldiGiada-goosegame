package it.unibo.goosegame.model.minigames.rockpaperscissors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goosegame.model.minigames.rockpaperscissors.api.RockPaperScissorsModel;
import it.unibo.goosegame.model.minigames.rockpaperscissors.impl.RockPaperScissorsModelImpl;
/**
 * This class contains unit tests for the RockPaperScissorsModel class.
 */
class TestRockPaperScissorsModelImpl {
    private static final String ROCK = "ROCK";
    private static final String PAPER = "PAPER";
    private static final String SCISSORS = "SCISSORS"; 
    private RockPaperScissorsModel model;

    /**
     * Set up the test environment before each test method is run.
     * Initializes a new RockPaperScissorsModel instance.
     */
    @BeforeEach
    public void setUp() {
        this.model = new RockPaperScissorsModelImpl();
    }

    /**
     * Tests that the initial player and computer scores are set to zero,
     * and the game is not over at the start.
     */
    @Test
    void testInitialScoresAsZero() {
        assertEquals(0, this.model.getPlayerScore());
        assertEquals(0, this.model.getComputerScore());
        assertFalse(this.model.isOver());
    }

    /**
     * Tests the logic for determining the winner of a round between the player and the computer.
     * The expected results are:
     * - Rock beats Scissors (1)
     * - Scissors beats Paper (-1)
     * - Paper equals Paper (0)
     */
    @Test
    void testDetermineWinnerLogic() {
        assertEquals(1, this.model.determineWinner(ROCK, SCISSORS));
        assertEquals(-1, this.model.determineWinner(PAPER, SCISSORS));
        assertEquals(0, this.model.determineWinner(PAPER, PAPER));
    }

     /**
     * Tests that the score changes after playing one or more rounds.
     * The player's or computer's score should increment depending on the result of each round.
     */
    @Test
    void testPlayRoundIncrementsScore() {
        final int originalPlayerScore = this.model.getPlayerScore();
        final int originalComputerScore = this.model.getComputerScore();

        boolean scoreChanged = false;
        for (int i = 0; i < 10; i++) {
            this.model.playRound(ROCK);
            if (this.model.getPlayerScore() != originalPlayerScore 
                || this.model.getComputerScore() != originalComputerScore) {
                    scoreChanged = true;
            }
        }
        assertTrue(scoreChanged, "Il punteggio dovrebbe cambiare dopo uno o più round.");
    }

    /**
     * Tests that the game can be reset correctly, setting the scores back to zero
     * and marking the game as not over.
     */
    @Test
    void testResetGameWorks() {
        this.model.playRound(ROCK);
        this.model.playRound(PAPER);
        this.model.playRound(SCISSORS);
        this.model.resetGame();
        assertEquals(0, this.model.getComputerScore());
        assertEquals(0, this.model.getPlayerScore());
        assertFalse(this.model.isOver());
    }
}
