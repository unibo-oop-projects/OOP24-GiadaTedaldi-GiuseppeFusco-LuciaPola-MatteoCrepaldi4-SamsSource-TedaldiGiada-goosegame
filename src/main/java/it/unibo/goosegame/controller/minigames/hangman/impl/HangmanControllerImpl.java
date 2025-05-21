package  it.unibo.goosegame.controller.minigames.hangman.impl;

import java.awt.Window;
import java.util.Objects;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.goosegame.controller.minigames.hangman.api.HangmanController;
import it.unibo.goosegame.model.minigames.hangman.impl.HangmanModelImpl;
import it.unibo.goosegame.view.minigames.hangman.impl.HangmanViewImpl;
/**
 * This class manages the interaction between the Hangman model and view.
 * It handles user input, updates the view based on game state,
 * and controls the game flow, including win/lose conditions.
 */
public class HangmanControllerImpl implements HangmanController {
     @SuppressFBWarnings(value = "EI2", justification = "Reference to view is safe and immutable in MVC pattern")
    private final transient HangmanViewImpl view;
    private final transient HangmanModelImpl model;

    /**
     * @param view the game view
     * @param model the game model
     */
    public HangmanControllerImpl(final HangmanViewImpl view, final HangmanModelImpl model) {
        this.model = Objects.requireNonNull(model);
        this.view = Objects.requireNonNull(view);
        init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLetterPressed(final char letter) {
        if (model.isOver()) {
            return;
        }

        model.guess(letter);
        view.updateWord(model.getHiddenWord());
        view.updateImage(model.getAttempts());

        if (model.isOver()) {
            view.disableAllButton();
            if (model.isWon()) {
                JOptionPane.showMessageDialog(view, "YOU WIN!");
            } else {
                JOptionPane.showMessageDialog(view, """
                                                    YOU LOSE...
                                                    The word is \t""" + model.getSelectedWord());
            }
            final Window window = SwingUtilities.getWindowAncestor(view);
            if (window != null) {
                window.dispose();
            }
        }
    }
    /**
     * {@inheritDoc}
     */
     @Override
    public final void startGame() {
        model.resetGame();
        view.enableAllButton();
        view.updateImage(model.getAttempts());
        view.updateWord(model.getHiddenWord());
    }

    private void init() {
        view.setController(this);
        view.updateImage(model.getAttempts());
        view.updateWord(model.getHiddenWord());
    }
}
