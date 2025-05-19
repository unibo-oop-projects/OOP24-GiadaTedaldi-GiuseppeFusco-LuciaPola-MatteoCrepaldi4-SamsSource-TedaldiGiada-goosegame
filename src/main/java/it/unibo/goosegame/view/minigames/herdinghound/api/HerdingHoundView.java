package it.unibo.goosegame.view.minigames.herdinghound.api;

import javax.swing.JFrame;

/**
 * API for the Herding Hound minigame view.
 */
public interface HerdingHoundView {
    /**
     * Avvia il conto alla rovescia iniziale, poi esegue la callback.
     * @param onFinish callback da eseguire al termine del countdown
     */
    void startCountdown(Runnable onFinish);

    /**
     * Ritorna true se il countdown è attivo.
     * @return true se il countdown è attivo, false altrimenti
     */
    boolean isCountdownActive();

    /**
     * Avvia l'animazione di lampeggio di fine partita.
     * @param frame JFrame su cui mostrare il pannello finale
     * @param hasWon true se il giocatore ha vinto
     */
    void startBlinking(JFrame frame, boolean hasWon);

    /**
     * Aggiorna la vista (richiama repaint).
     */
    void updateView();

    /**
     * Mostra il pannello di fine partita.
     * @param frame JFrame su cui mostrare il pannello
     * @param hasWon true se il giocatore ha vinto
     */
    void showGameOverPanel(JFrame frame, boolean hasWon);
}
