package  it.unibo.goosegame.controller.minigames.hangman.impl;

import java.util.Objects;

import javax.swing.JOptionPane;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.goosegame.controller.minigames.hangman.api.HangmanController;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.model.minigames.hangman.api.HangmanModel;
import it.unibo.goosegame.view.minigames.hangman.api.HangmanView;
/**
 * This class manages the interaction between the Hangman model and view.
 * It handles user input, updates the view based on game state,
 * and controls the game flow, including win/lose conditions.
 */
public class HangmanControllerImpl implements HangmanController {
     @SuppressFBWarnings(value = "EI2", justification = "Reference to view is safe and immutable in MVC pattern")
    private final transient HangmanView view;
    private final transient HangmanModel model;

    /**
     * @param view the game view
     * @param model the game model
     */
    @SuppressFBWarnings(
        value = "EI2",
        justification = "The menu is intentionally shared for interaction between view and controller."
    )
    public HangmanControllerImpl(final HangmanView view, final HangmanModel model) {
        this.model = Objects.requireNonNull(model);
        this.view = Objects.requireNonNull(view);
        init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLetterPressed(final char letter) {
        if (this.model.isOver()) {
            return;
        }

        this.model.guess(letter);
        this.view.updateWord(model.getHiddenWord());
        this.view.updateImage(model.getAttempts());

        if (this.model.isOver()) {
            this.view.disableAllButton();
            if (this.model.isWon()) {
                JOptionPane.showMessageDialog(null, "YOU WIN!");
            } else {
                JOptionPane.showMessageDialog(null, """
                                                    YOU LOSE...
                                                    The word is \t""" + model.getSelectedWord());
            }
            this.view.dispose();
        }
    }
    /**
     * {@inheritDoc}
     */
     @Override
    public final void startGame() {
        this.model.resetGame();
        this.view.enableAllButton();
        this.view.updateImage(this.model.getAttempts());
        this.view.updateWord(this.model.getHiddenWord());
    }

    /**
     * {@inheritDoc}
     */
     @Override
    public GameState getGameState() {
        return this.model.getGameState();
    }

    private void init() {
        this.view.updateImage(this.model.getAttempts());
        this.view.updateWord(this.model.getHiddenWord());
    }
}
