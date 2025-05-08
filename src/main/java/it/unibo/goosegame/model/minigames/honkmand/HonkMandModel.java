package it.unibo.goosegame.model.minigames.honkmand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.goosegame.utilities.Colors;


/**
 * Modello logico del minigioco HonkMand (Simon Game).
 * Gestisce la sequenza, il punteggio, il livello e la verifica degli input.
 */
public class HonkMandModel {
    
    // Livello massimo per vincere il gioco (ora in HonkMandConstants)
    // Livello massimo per vincere il gioco (ora in HonkMandConstants)
    public static final int MAX_LEVEL = it.unibo.goosegame.utilities.HonkMandConstants.MAX_LEVEL; // per retrocompatibilità, usa direttamente HonkMandConstants dove possibile
    
    private List<Colors> sequence;
    private List<Colors> playerSequence;
    private int level;
    private int score;
    private boolean isPlaying;
    private Random random;
    
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
        isPlaying = false;
        random = new Random();
    }
    
    
    /**
     * Avvia una nuova partita, azzerando punteggio e sequenze.
     */
    public void startGame() {
        sequence.clear();
        playerSequence.clear();
        level = 0;
        score = 0;
        isPlaying = true;
        nextRound();
    }
    
    /**
     * Passa al round successivo, incrementando il livello e generando una nuova sequenza.
     */
    public void nextRound() {
        level++;
        playerSequence.clear();
        
        // Verifica se il giocatore ha raggiunto il livello massimo
        if (level > MAX_LEVEL) {
            return; // Il gioco è già stato vinto
        }
        
        // Genera una nuova sequenza completa per questo livello
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
        if (!isPlaying) return InputResult.GAME_OVER;
        playerSequence.add(colorId);
        int currentIndex = playerSequence.size() - 1;

        if (playerSequence.get(currentIndex).equals(sequence.get(currentIndex))) {
            if (playerSequence.size() == sequence.size()) {
                score++;
                if (level == MAX_LEVEL) {
                    isPlaying = false;
                    return InputResult.GAME_WIN;
                }
                return InputResult.NEXT_ROUND;
            }
            return InputResult.CORRECT;
        } else {
            isPlaying = false;
            return InputResult.GAME_OVER;
        }
    }
    
    /** @return la sequenza da riprodurre */
    public List<Colors> getSequence() { return sequence; }
    /** @return il livello corrente */
    public int getLevel() { return level; }
    /** @return il punteggio corrente */
    public int getScore() { return score; }
    /** @return true se la partita è in corso */
    public boolean isPlaying() { return isPlaying; }
    /** Imposta lo stato di gioco (usato dal controller) */
    public void setPlaying(boolean playing) { isPlaying = playing; }
    
}