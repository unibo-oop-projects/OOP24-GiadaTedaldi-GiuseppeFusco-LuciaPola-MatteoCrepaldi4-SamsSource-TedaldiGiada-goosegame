package  it.unibo.goosegame.model.minigames.Hangman;

import javax.swing.JOptionPane;

public class HangmanController {
    private final HangmanView view;
    private final HangmanModel model;

    public HangmanController(HangmanView view, HangmanModel model) {
        this.model = model;
        this.view = view;
        view.setController(this);
        view.updateImage(model.getAttempts());
        view.updateWord(model.getHiddenWord());
    }

    public void onLetterPressed(char letter) {
        if(model.isOver()) {
            return;
        }

        model.guess(letter);
        view.updateWord(model.getHiddenWord());
        view.updateImage(model.getAttempts());

        if(model.isOver()) {
            view.disableAllButton();
            if(model.isWon()) {
                JOptionPane.showMessageDialog(view, "YOU WIN!");
            } else {
                JOptionPane.showMessageDialog(view, """
                                                    YOU LOSE...
                                                    The word is \t""" + model.getSelectedWord());
            }
            view.dispose();
        }
    }
}