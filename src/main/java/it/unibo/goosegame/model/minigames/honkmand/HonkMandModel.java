package it.unibo.goosegame.model.minigames.honkmand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.goosegame.utilities.Colors;


/**
 * Modello del Simon Game che gestisce la logica di gioco
 */
public class HonkMandModel {
    
    // Livello massimo per vincere il gioco
    public static final int MAX_LEVEL = 10;
    
    private List<Colors> sequence;
    private List<Colors> playerSequence;
    private int level;
    private int score;
    private boolean isPlaying;
    private Random random;
    
    // Risultati possibili dopo un input del giocatore
    public enum InputResult {
        CORRECT,
        NEXT_ROUND,
        GAME_OVER,
        GAME_WIN
    }
    
    public HonkMandModel() {
        sequence = new ArrayList<>();
        playerSequence = new ArrayList<>();
        level = 0;
        score = 0;
        isPlaying = false;
        random = new Random();
    }
    
    
    public void startGame() {
        sequence.clear();
        playerSequence.clear();
        level = 0;
        score = 0;
        isPlaying = true;
        nextRound();
    }
    
    /**
     * Passa al round successivo
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
     * Genera una nuova sequenza completa per il livello corrente
     */
    private void generateNewSequence() {
        sequence.clear();
        for (int i = 0; i < level; i++) {
            int index = random.nextInt(Colors.values().length);
            sequence.add(Colors.values()[index]);
        }
    }
    
    /**
     * Verifica l'input del giocatore
     */
    public InputResult checkPlayerInput(Colors colorId) {
        playerSequence.add(colorId);
        int currentIndex = playerSequence.size() - 1;
        
        if (playerSequence.get(currentIndex).equals(sequence.get(currentIndex))) {
            // Input corretto
            if (playerSequence.size() == sequence.size()) {
                // Sequenza completata
                score += level;
                
                // Verifica se il giocatore ha vinto
                if (level == MAX_LEVEL) {
                    return InputResult.GAME_WIN;
                }
                
                return InputResult.NEXT_ROUND;
            }
            return InputResult.CORRECT;
        } else {
            // Input errato
                return InputResult.GAME_OVER;
        }
    }
    
    // Getters e Setters
    public List<Colors> getSequence() {
        return sequence;
    }
    
    public int getLevel() {
        return level;
    }
    
    public int getScore() {
        return score;
    }
    
    public boolean isPlaying() {
        return isPlaying;
    }
    
    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
    
}