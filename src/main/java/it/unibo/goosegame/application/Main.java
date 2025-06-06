package it.unibo.goosegame.application;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (final ClassNotFoundException | InstantiationException 
             | IllegalAccessException | UnsupportedLookAndFeelException e) {
      JOptionPane.showMessageDialog(null, 
          "Unable to set system look and feel. Default look and feel will be used.", 
          "Error", 
          JOptionPane.ERROR_MESSAGE);
    }
    SwingUtilities.invokeLater(() -> {
      final GameMenu menu = new GameMenu();
      menu.setVisible(true);
    });
  }
}
