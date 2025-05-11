package it.unibo.goosegame.model.general;

/**
 * Interfaccia base per i modelli dei minigiochi.
 * Definisce i metodi comuni per la gestione dello stato di gioco.
 */
public interface MinigamesModel {

    /**
     * Stati generali del minigioco.
     */
    public enum GameState {
        ONGOING,
        WON,
        LOST
    }

    /**
     * Reimposta lo stato del gioco all'inizio.
     */
    void resetGame();

    /**
     * Indica se il gioco è terminato.
     * @return true se il gioco è finito, false altrimenti
     */
    boolean isOver();
}
