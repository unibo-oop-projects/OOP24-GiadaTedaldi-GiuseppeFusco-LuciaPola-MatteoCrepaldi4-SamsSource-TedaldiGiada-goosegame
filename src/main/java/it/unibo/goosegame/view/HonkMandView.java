package it.unibo.goosegame.view;

import javax.swing.*;

import it.unibo.goosegame.model.minigames.honkmand.HonkMandModel;
import it.unibo.goosegame.utilities.Colors;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Vista grafica del minigioco HonkMand (Simon Game).
 * Gestisce la UI e le animazioni dei pulsanti.
 */
public class HonkMandView extends JFrame {
    private Map<Colors, JButton> buttons;
    private JButton startButton;
    private JLabel levelLabel;
    private JLabel scoreLabel;
    private JLabel messageLabel;
    private JPanel gamePanel;
    private JDialog victoryDialog;
    private JDialog gameOverDialog;
    
    /**
     * Costruttore. Inizializza la finestra e i componenti grafici.
     */
    public HonkMandView() {
        // Configurazione della finestra
        setTitle(it.unibo.goosegame.utilities.HonkMandMessages.TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        
        // Inizializzazione dei componenti
        initComponents();
        
        // Layout
        layoutComponents();
        
        // Inizializza i dialoghi
        initDialogs();
        
        setVisible(true);
    }
    
    /**
     * Inizializza i componenti grafici della vista.
     */
    private void initComponents() {
        buttons = new HashMap<>();
        
        // Creazione dei pulsanti colorati
        JButton greenButton = new JButton();
        greenButton.setBackground(Color.GREEN);
        buttons.put(Colors.GREEN, greenButton);
        
        JButton redButton = new JButton();
        redButton.setBackground(Color.RED);
        buttons.put(Colors.RED, redButton);
        
        JButton yellowButton = new JButton();
        yellowButton.setBackground(Color.YELLOW);
        buttons.put(Colors.YELLOW, yellowButton);
        
        JButton blueButton = new JButton();
        blueButton.setBackground(Colors.BLUE.getAwtColor());
        buttons.put(Colors.BLUE, blueButton);
        
        // Altri componenti
        startButton = new JButton(it.unibo.goosegame.utilities.HonkMandMessages.START_BUTTON);
        levelLabel = new JLabel(it.unibo.goosegame.utilities.HonkMandMessages.LEVEL_LABEL + "0");
        scoreLabel = new JLabel(it.unibo.goosegame.utilities.HonkMandMessages.SCORE_LABEL + "0");
        messageLabel = new JLabel("");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setPreferredSize(new Dimension(300, 24));
        messageLabel.setMinimumSize(new Dimension(300, 24)); 
        gamePanel = new JPanel();
    }
    
    /**
     * Inizializza i dialoghi di vittoria e game over.
     */
    private void initDialogs() {
        // Dialogo di vittoria
        victoryDialog = new JDialog(this, it.unibo.goosegame.utilities.HonkMandMessages.VICTORY_TITLE, true);
        victoryDialog.setSize(300, 200);
        victoryDialog.setLocationRelativeTo(this);
        
        JPanel victoryPanel = new JPanel();
        victoryPanel.setLayout(new BorderLayout());
        
        JLabel victoryLabel = new JLabel(it.unibo.goosegame.utilities.HonkMandMessages.VICTORY_MESSAGE, SwingConstants.CENTER);
        victoryLabel.setFont(new Font("Arial", Font.BOLD, 18));
        victoryPanel.add(victoryLabel, BorderLayout.CENTER);
        
        JButton closeVictoryButton = new JButton(it.unibo.goosegame.utilities.HonkMandMessages.CLOSE_GAME);
        closeVictoryButton.addActionListener(e -> System.exit(0));
        
        JPanel victoryButtonPanel = new JPanel();
        victoryButtonPanel.add(closeVictoryButton);
        victoryPanel.add(victoryButtonPanel, BorderLayout.SOUTH);
        
        victoryDialog.add(victoryPanel);
        
        // Dialogo di game over
        gameOverDialog = new JDialog(this, "Game Over", true);
        gameOverDialog.setSize(300, 200);
        gameOverDialog.setLocationRelativeTo(this);
        
        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new BorderLayout());
        
        JLabel gameOverLabel = new JLabel("Game Over!", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gameOverPanel.add(gameOverLabel, BorderLayout.CENTER);
        
        JButton closeGameOverButton = new JButton("Chiudi Gioco");
        closeGameOverButton.addActionListener(e -> System.exit(0));
        
        JPanel gameOverButtonPanel = new JPanel();
        gameOverButtonPanel.add(closeGameOverButton);
        gameOverPanel.add(gameOverButtonPanel, BorderLayout.SOUTH);
        
        gameOverDialog.add(gameOverPanel);
    }
    
    /**
     * Dispone i componenti nel layout della finestra.
     */
    private void layoutComponents() {
        // Layout principale
        setLayout(new BorderLayout());
        
        // Pannello superiore per titolo e livello
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Honkmand"));
        topPanel.add(levelLabel);
        add(topPanel, BorderLayout.NORTH);
        
        // Pannello centrale per i pulsanti colorati
        gamePanel.setLayout(new GridLayout(2, 2, 10, 10));
        gamePanel.add(buttons.get(Colors.GREEN));
        gamePanel.add(buttons.get(Colors.RED));
        gamePanel.add(buttons.get(Colors.YELLOW));
        gamePanel.add(buttons.get(Colors.BLUE));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(gamePanel, BorderLayout.CENTER);
        
        // Pannello inferiore per controlli e punteggio
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(startButton);
        
        JPanel scorePanel = new JPanel(new FlowLayout());
        scorePanel.add(scoreLabel);
        scorePanel.add(new JLabel("Livello massimo: " + HonkMandModel.MAX_LEVEL));

        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(messageLabel, BorderLayout.CENTER);
        messagePanel.setPreferredSize(new Dimension(400,30));
        
        bottomPanel.add(controlPanel);
        bottomPanel.add(scorePanel);
        bottomPanel.add(messagePanel);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Aggiorna il livello visualizzato
     */
    /**
     * Aggiorna il livello visualizzato.
     * @param level livello corrente
     */
    public void updateLevel(int level) {
        levelLabel.setText("Livello: " + level);
    }
    
    /**
     * Aggiorna il punteggio visualizzato
     */
    /**
     * Aggiorna il punteggio visualizzato.
     * @param score punteggio corrente
     */
    public void updateScore(int score) {
        scoreLabel.setText("Punteggio: " + score);
    }
    
    /**
     * Mostra un messaggio all'utente.
     * @param message testo del messaggio
     * @param isError true se il messaggio è di errore
     */
    public void showMessage(String message, boolean isError) {
        messageLabel.setText(message);
        messageLabel.setForeground(isError ? Color.RED : Color.BLACK);
    }

    /**
     * Pulisce il messaggio visualizzato.
     */
    public void clearMessage() {
        messageLabel.setText("");
    }

    /**
     * Mostra il dialogo di vittoria.
     */
    public void showVictoryDialog() {
        victoryDialog.setVisible(true);
    }

    /**
     * Mostra il dialogo di game over.
     */
    public void showGameOverDialog() {
        gameOverDialog.setVisible(true);
    }
    
    /**
     * Illumina un pulsante per la durata specificata
     */
    /**
     * Illumina un pulsante per la durata specificata.
     * @param colorId colore del pulsante
     * @param duration durata in millisecondi
     */
    public void lightUpButton(Colors colorId, int duration) {
        JButton button = buttons.get(colorId);
        Color originalColor = button.getBackground();
        
        // Illumina il pulsante
        button.setBackground(brightenColor(originalColor));
        
        // Timer per ripristinare il colore originale
        Timer lightTimer = new Timer(duration, e -> {
            button.setBackground(originalColor);
            ((Timer)e.getSource()).stop(); // Ferma esplicitamente il timer
        });
        lightTimer.setRepeats(false); // Assicurati che il timer non si ripeta

        // Illumina il pulsante
        button.setBackground(brightenColor(originalColor));
        // Avvia il timer
        lightTimer.start();
        

    }
    
    /**
     * Rende un colore più luminoso
     */
    /**
     * Rende un colore più luminoso.
     * @param color colore originale
     * @return colore schiarito
     */
    private Color brightenColor(Color color) {
        int r = Math.min(255, color.getRed() + 100);
        int g = Math.min(255, color.getGreen() + 100);
        int b = Math.min(255, color.getBlue() + 100);
        return new Color(r, g, b);
    }
    
    /**
     * Abilita o disabilita i pulsanti colorati
     */
    /**
     * Abilita o disabilita i pulsanti colorati.
     * @param enabled true per abilitare, false per disabilitare
     */
    public void setButtonsEnabled(boolean enabled) {
        for (JButton button : buttons.values()) {
            button.setEnabled(enabled);
        }
    }
    
    /**
     * Aggiorna lo stato del pulsante di avvio
     */
    /**
     * Aggiorna lo stato del pulsante di avvio.
     * @param active true se il gioco è attivo
     */
    public void setGameActive(boolean active) {
        if (active) {
            startButton.setText("Restart");
        } else {
            startButton.setText("Inizia Gioco");
        }
    }

    /**
     * Imposta il testo del pulsante di avvio
     */
    /**
     * Imposta il testo del pulsante di avvio.
     * @param text testo da visualizzare
     */
    public void setStartButtonText(String text) {
        startButton.setText(text);
    }
    
    /**
     * Esegue un'animazione di game over
     */
    /**
     * Esegue un'animazione di game over lampeggiando i pulsanti.
     */
    public void gameOverAnimation() {
        // Variabili per il numero di lampeggi e la durata del lampeggio
        final int flashCount = 3;
        final int flashDelay = 200; // Durata in millisecondi per ogni lampeggio
        
        // Azioni da eseguire al lampeggio (accendi/spegni i pulsanti)
        ActionListener flashAction = new ActionListener() {
            private int currentFlash = 0; // Conta i lampeggi
    
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFlash < flashCount) {
                    // Alterna lo stato di accensione/spegnimento dei pulsanti
                    boolean isLightOn = currentFlash % 2 == 0;
                    
                    for (Map.Entry<Colors, JButton> entry : buttons.entrySet()) {
                        if (isLightOn) {
                            entry.getValue().setBackground(brightenColor(entry.getValue().getBackground()));
                        } else {
                            // Ripristina il colore originale
                            entry.getValue().setBackground(entry.getKey().getAwtColor());
                        }
                    }
                    
                    currentFlash++; // Incrementa il contatore dei lampeggi
                } else {
                    ((Timer) e.getSource()).stop(); // Fermiamo il timer quando abbiamo completato i lampeggi
                }
            }
        };
    
        // Crea un timer per eseguire l'azione di lampeggio ogni 200 ms
        Timer timer = new Timer(flashDelay, flashAction);
        timer.start();
    }
       
    // Metodi per aggiungere listener agli eventi
    /**
     * Aggiunge un listener al pulsante di avvio.
     * @param listener listener da aggiungere
     */
    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }
    
    /**
     * Aggiunge un listener a un pulsante colorato.
     * @param colorId colore del pulsante
     * @param listener listener da aggiungere
     */
    public void addColorButtonListener(Colors colorId, ActionListener listener) {
        buttons.get(colorId).addActionListener(listener);
    }
}