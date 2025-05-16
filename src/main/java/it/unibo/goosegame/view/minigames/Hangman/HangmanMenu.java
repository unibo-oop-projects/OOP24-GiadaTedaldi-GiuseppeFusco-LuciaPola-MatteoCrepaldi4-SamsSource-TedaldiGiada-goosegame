package it.unibo.goosegame.view.minigames.hangman;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import it.unibo.goosegame.controller.minigames.hangman.impl.HangmanControllerImpl;
import it.unibo.goosegame.model.minigames.hangman.impl.HangmanModelImpl;
import it.unibo.goosegame.view.general.MinigameMenuAbstract;
import it.unibo.goosegame.view.minigames.hangman.impl.HangmanViewImpl;

/**
 * Class representing the Hangman game menu.
 * Extends the abstract class MinigameMenuAbstract.
 */
public class HangmanMenu extends MinigameMenuAbstract {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(HangmanMenu.class.getName());
    /**
     * @param words The list of words that the game will use for the matches. 
     */
    public HangmanMenu(final List<String> words) { 
        super(
            "/hangmanImage1.png", 
            "Hangman", 
            """
            The game involves guessing a randomly selected word. 
            Initially, the word is represented by a series of symbols: 
                + for vowels
                - for consonants.

            The player has a maximum of 5 incorrect guesses(lives):
            If the player exhausts all their lives without guessing the word correctly, they lose.
            If the player correctly identifies all the letters in the word before running out of lives, they win.
            """, 
            createView(words));
    }

    /**
     * @param words The list of words for the game.
     * @return view The view for the Hangman game.
     */
    private static HangmanViewImpl createView(final List<String> words) {
        final HangmanViewImpl view = new HangmanViewImpl();
        view.initializeView();
        final HangmanModelImpl model = new HangmanModelImpl(words.toArray(String[]::new));
        new HangmanControllerImpl(view, model);
        return view;
    }

    /**
     * @param args The command line arguments.
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                final List<String> words = loadWords();
                final HangmanMenu menu = new HangmanMenu(words);
                menu.initializeView();
            } catch (final IOException e) {
                LOGGER.log(Level.SEVERE, "Error loading words: " + e.getMessage(), e);
            }
        });
    }

    /**
     * @return lines A list of words.
     * @throws IOException If an error occurs while loading the file.
     */
    private static List<String> loadWords() throws  IOException {
        final List<String> lines = new ArrayList<>();
        try (InputStream inputStream = HangmanViewImpl.class.getClassLoader().getResourceAsStream("parole.txt")) {
            if (inputStream == null) {
                JOptionPane.showMessageDialog(null, "Unabvle to find the words file.", "Error", JOptionPane.ERROR_MESSAGE);
                return lines;
            }
            try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
                while (scanner.hasNextLine()) {
                    final String word = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
                    if (!word.isEmpty()) {
                        lines.add(word);
                    }
                }
            }
        }
        return lines;
    }
}
