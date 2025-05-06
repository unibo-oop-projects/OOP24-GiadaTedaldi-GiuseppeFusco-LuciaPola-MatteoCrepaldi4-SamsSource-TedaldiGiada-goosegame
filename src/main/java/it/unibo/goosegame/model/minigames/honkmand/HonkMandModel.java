package it.unibo.goosegame.model.minigames.honkmand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Modello del Simon Game che gestisce la logica di gioco
 */
public class HonkMandModel {
    // Costanti per i colori
    public static final String GREEN = "green";
    public static final String RED = "red";
    public static final String YELLOW = "yellow";
    public static final String BLUE = "blue";
    
    // Livello massimo per vincere il gioco
    public static final int MAX_LEVEL = 10;
    
    private List<String> sequence;
    private List<String> playerSequence;
    private int level;
    private int score;
    private boolean isPlaying;
    private Random random;
    private String[] colors;
    
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
        colors = new String[] {GREEN, RED, YELLOW, BLUE};
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
            int randomIndex = random.nextInt(4);
            String randomColor = colors[randomIndex];
            sequence.add(randomColor);
        }
    }
    
    /**
     * Verifica l'input del giocatore
     */
    public InputResult checkPlayerInput(String colorId) {
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
    public List<String> getSequence() {
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