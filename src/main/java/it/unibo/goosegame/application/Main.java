package it.unibo.goosegame.application;

import javax.swing.SwingUtilities;

import it.unibo.goosegame.view.minigames.hangman.HangmanMenu;
/**
 * Main.
 */
public final class Main {
  private Main() {
    throw new UnsupportedOperationException("Utility class");
  }
  /**
   * 
   * @param args
   */
  public static void main(final String[] args) {
    SwingUtilities.invokeLater(() -> {
      final HangmanMenu menu = new HangmanMenu();
      menu.initializeView();
    });
  }
}
