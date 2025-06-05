package it.unibo.goosegame.application;

import javax.swing.SwingUtilities;
import it.unibo.goosegame.view.gamemenu.impl.GameMenu;
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
      final GameMenu menu = new GameMenu();
      menu.setVisible(true);
    });
  }
}
