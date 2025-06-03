package it.unibo.goosegame.application;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.view.general.api.MinigameMenu;
import it.unibo.goosegame.view.minigames.honkmand.HonkMandMenu;
//import it.unibo.goosegame.view.minigames.memory.MemoryMenu;
//import it.unibo.goosegame.view.minigames.snake.SnakeMenu;
/**
 * Main.
 */
public final class Main {

  private static Timer checkState;

  private Main() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Checks the game state of the given Menu instance.
   * @param s the Menu instance to check
   */
  public static void check(final MinigameMenu s) {
    checkState = new Timer(100, new ActionListener() {
      private GameState g = GameState.NOT_STARTED;
      @Override
      public void actionPerformed(final ActionEvent e) {
        g = s.getGameState();
        if (g == GameState.WON || g == GameState.LOST || g == GameState.TIE) {
          System.out.println("Game State: " + g);
          s.dispose();
          checkState.stop();
        }
      }
    });
    checkState.start();
  }

  /**
   * 
   * @param args
   */
  public static void main(final String[] args) {
    /*
    SwingUtilities.invokeLater(() -> {
      final GameMenu menu = new GameMenu();
      menu.setVisible(true);
    });*/
    final HonkMandMenu m = new HonkMandMenu();
    m.initializeView();
    check(m);
    //SnakeMenu s = new SnakeMenu();
    //s.initializeView();
    //check(s);
  }
}
