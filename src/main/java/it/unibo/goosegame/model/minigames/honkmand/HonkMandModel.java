package it.unibo.goosegame.model.minigames.honkmand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import it.unibo.goosegame.utilities.Colors;

/**
 * Modello logico del minigioco HonkMand (Simon Game).
 * Gestisce la sequenza, il punteggio, il livello e la verifica degli input.
 */
import it.unibo.goosegame.model.general.MinigamesModel;

public class HonkMandModel implements MinigamesModel {

    // Livello massimo per vincere il gioco (ora in HonkMandConstants)
    public static final int MAX_LEVEL = it.unibo.goosegame.utilities.HonkMandConstants.MAX_LEVEL; // per retrocompatibilità

    private List<Colors> sequence;
    private List<Colors> playerSequence;
    private int level;
    private int score;
    private Random random;

    /**
     * Stati possibili del gioco.
     */
    public enum GameState {
        NOT_STARTED,
        PLAYING,
        GAME_OVER,
        GAME_WIN
    }

    private GameState gameState;

    /**
     * Risultati possibili dopo un input del giocatore.
     */
    public enum InputResult {
        CORRECT,      // Input corretto, attendi il prossimo
        NEXT_ROUND,   // Sequenza completata, passa al prossimo round
        GAME_OVER,    // Input errato, partita persa
        GAME_WIN      // Partita vinta
    }

    /**
     * Costruttore. Inizializza lo stato del gioco.
     */
    public HonkMandModel() {
        sequence = new ArrayList<>();
        playerSequence = new ArrayList<>();
        level = 0;
        score = 0;
        random = new Random();
        gameState = GameState.NOT_STARTED;
    }

    /**
     * Avvia una nuova partita, azzerando punteggio e sequenze.
     */
    public void startGame() {
        sequence.clear();
        playerSequence.clear();
        level = 0;
        score = 0;
        gameState = GameState.PLAYING;
        nextRound();
    }

    /**
     * Passa al round successivo, incrementando il livello e generando una nuova sequenza.
     */
    public void nextRound() {
        level++;
        playerSequence.clear();

        if (level > MAX_LEVEL) {
            gameState = GameState.GAME_WIN;
            return;
        }
        generateNewSequence();
    }

    /**
     * Genera una nuova sequenza casuale per il livello corrente.
     */
    private void generateNewSequence() {
        sequence.clear();
        for (int i = 0; i < level; i++) {
            int index = random.nextInt(Colors.values().length);
            sequence.add(Colors.values()[index]);
        }
    }

    /**
     * Verifica l'input del giocatore confrontandolo con la sequenza.
     * @param colorId colore scelto dal giocatore
     * @return risultato dell'input
     */
    public InputResult checkPlayerInput(Colors colorId) {
        if (gameState != GameState.PLAYING) return InputResult.GAME_OVER;
        playerSequence.add(colorId);
        int currentIndex = playerSequence.size() - 1;

        if (playerSequence.get(currentIndex).equals(sequence.get(currentIndex))) {
            if (playerSequence.size() == sequence.size()) {
                score++;
                if (level == MAX_LEVEL) {
                    gameState = GameState.GAME_WIN;
                    return InputResult.GAME_WIN;
                }
                return InputResult.NEXT_ROUND;
            }
            return InputResult.CORRECT;
        } else {
            gameState = GameState.GAME_OVER;
            return InputResult.GAME_OVER;
        }
    }

    /** @return la sequenza da riprodurre */
    public List<Colors> getSequence() { return sequence; }
    /** @return il livello corrente */
    public int getLevel() { return level; }
    /** @return il punteggio corrente */
    public int getScore() { return score; }
    /** @return lo stato attuale del gioco */
    public GameState getGameState() { return gameState; }

    // --- Implementazione MinigamesModel ---
    @Override
    public void resetGame() {
        startGame();
    }

    @Override
    public boolean isOver() {
        return gameState == GameState.GAME_OVER || gameState == GameState.GAME_WIN;
    }

    /** Restituisce lo stato generale del minigioco secondo l'interfaccia MinigamesModel. */
    public MinigamesModel.GameState getGameStateGeneral() {
        return switch (gameState) {
            case PLAYING, NOT_STARTED -> MinigamesModel.GameState.ONGOING;
            case GAME_WIN -> MinigamesModel.GameState.WON;
            case GAME_OVER -> MinigamesModel.GameState.LOST;
        };
    }
}
