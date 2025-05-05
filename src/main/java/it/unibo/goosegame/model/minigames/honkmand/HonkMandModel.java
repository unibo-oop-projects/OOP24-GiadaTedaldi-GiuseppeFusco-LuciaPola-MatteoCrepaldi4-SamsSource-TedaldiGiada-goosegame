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
    
    private List<String> sequence;
    private List<String> playerSequence;
    private int level;
    private int score;
    private int highScore;
    private boolean isPlaying;
    private boolean strictMode;
    private Random random;
    private String[] colors;
    
    // Risultati possibili dopo un input del giocatore
    public enum InputResult {
        CORRECT,
        NEXT_ROUND,
        RETRY,
        GAME_OVER
    }
    
    public HonkMandModel() {
        sequence = new ArrayList<>();
        playerSequence = new ArrayList<>();
        level = 0;
        score = 0;
        highScore = loadHighScore();
        isPlaying = false;
        strictMode = false;
        random = new Random();
        colors = new String[] {GREEN, RED, YELLOW, BLUE};
    }
    
    /**
     * Carica il punteggio più alto da un file o database
     */
    private int loadHighScore() {
        // In una implementazione reale, questo caricherebbe da un file o database
        return 0;
    }
    
    /**
     * Salva il punteggio più alto
     */
    private void saveHighScore() {
        // In una implementazione reale, questo salverebbe su un file o database
    }
    
    /**
     * Inizia una nuova partita
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
     * Passa al round successivo
     */
    public void nextRound() {
        level++;
        playerSequence.clear();
        addRandomColor();
    }
    
    /**
     * Aggiunge un colore casuale alla sequenza
     */
    private void addRandomColor() {
        int randomIndex = random.nextInt(4);
        String randomColor = colors[randomIndex];
        sequence.add(randomColor);
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
                if (score > highScore) {
                    highScore = score;
                    saveHighScore();
                }
                return InputResult.NEXT_ROUND;
            }
            return InputResult.CORRECT;
        } else {
            // Input errato
            if (strictMode) {
                return InputResult.GAME_OVER;
            }
            return InputResult.RETRY;
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
    
    public int getHighScore() {
        return highScore;
    }
    
    public boolean isPlaying() {
        return isPlaying;
    }
    
    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
    
    public boolean isStrictMode() {
        return strictMode;
    }
    
    public void setStrictMode(boolean strictMode) {
        this.strictMode = strictMode;
    }
}