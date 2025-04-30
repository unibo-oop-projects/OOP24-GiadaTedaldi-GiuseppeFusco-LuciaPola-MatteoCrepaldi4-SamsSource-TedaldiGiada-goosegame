package it.unibo.goosegame.model.minigames.RockPaperScissors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goosegame.model.minigames.RockPaperScissors.impl.RockPaperScissorsModel;

public class RockPaperScissorsModelTest {
    private RockPaperScissorsModel model;

    @BeforeEach
    public void setUp() {
        model = new RockPaperScissorsModel();
    }

    @Test
    public void testInitialScoresAsZero() {
        assertEquals(0, model.getPlayerScore());
        assertEquals(0, model.getComputerScore());
        assertFalse(model.isOver());
    }

    @Test
    public void testDetermineWinnerLogic() {
        assertEquals(-1, model.determineWinner("Rock", "Scissors"));
        assertEquals(-1, model.determineWinner("Paper", "Scissors"));
        assertEquals(0, model.determineWinner("Paper", "Paper"));
    }

    @Test
    public void testPlayRoundIncrementsScore() {
        int originalPlayerScore = model.getPlayerScore();
        int originalComputerScore = model.getComputerScore();

        boolean scoreChanged = false;
        for(int i = 0; i < 10; i++) {
            @SuppressWarnings("unused")
            String result = model.playRound("Rock");
            if(model.getPlayerScore() != originalPlayerScore || 
                model.getComputerScore() != originalComputerScore) {
                    scoreChanged = true;
            }
        }
        assertTrue(scoreChanged, "Il punteggio dovrebbe cambiare dopo uno o più round.");
    }

    @Test
    public void testGameEndsAtThreePoints() {
        for(int i = 0; i < 3; i++) {
            model.playRound("Rock");
        }
        assertTrue(model.isOver() || model.getPlayerScore() >= 3 || model.getComputerScore() >= 3);
    }

    @Test
    public void testResetGameWorks() {
        model.playRound("Rock");
        model.playRound("Paper");
        model.playRound("Scissors");
        model.resetGame();
        assertEquals(0, model.getComputerScore());
        assertEquals(0, model.getPlayerScore());
        assertFalse(model.isOver());
    }
}