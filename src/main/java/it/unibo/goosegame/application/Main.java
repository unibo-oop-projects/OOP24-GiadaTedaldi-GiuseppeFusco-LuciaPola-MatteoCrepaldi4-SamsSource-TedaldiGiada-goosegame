package it.unibo.goosegame.application;

<<<<<<< HEAD
import javax.swing.SwingUtilities;
/**
 * Entry point of the Goose Game application.
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
      final GameMenu menu = new GameMenu();
      menu.setVisible(true);
    });
  }
=======
/**
 * Main class for the Goose game application.
 */
public class Main {

>>>>>>> master
}
