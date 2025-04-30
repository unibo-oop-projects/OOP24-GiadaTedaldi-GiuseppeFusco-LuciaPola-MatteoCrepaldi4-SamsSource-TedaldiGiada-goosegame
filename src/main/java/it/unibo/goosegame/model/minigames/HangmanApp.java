package it.unibo.goosegame.model.minigames.Hangman;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.SwingUtilities;

public class HangmanApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<String> words = loadWords();
            HangmanModel model = new HangmanModel(words.toArray(String[]::new));
            HangmanView view = new HangmanView();
            @SuppressWarnings("unused")
            HangmanController controller = new HangmanController(view, model);
            view.setVisible(true);
        });
    }

    private static List<String> loadWords() {
        List<String> lines = new ArrayList<>();
        try(InputStream inputStream = HangmanView.class.getClassLoader().getResourceAsStream("parole.txt")) {
            if(inputStream == null) {
                System.err.println("Impossibile trovare il file.");
                return lines;
            }
            try (Scanner scanner = new Scanner(inputStream)) {
                while(scanner.hasNextLine()) {
                    String word = scanner.nextLine().trim().toUpperCase();
                    if(!word.isEmpty()) lines.add(word);
                }
            } 
        } catch(Exception e) {
            System.err.println("Errore nella lettura del file: " + e.getMessage());
        }
        return lines;
    }
}