package it.unibo.goosegame.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import javax.swing.SwingUtilities;
import javax.swing.Timer;

import it.unibo.goosegame.model.general.MinigamesModel.GameState;
//import it.unibo.goosegame.view.gamemenu.impl.GameMenu;
import it.unibo.goosegame.view.minigames.memory.MemoryMenu;
//import it.unibo.goosegame.view.minigames.snake.SnakeMenu;
/**
 * Main.
 */
public final class Main {

  private static Timer checkState;
  private static GameState g = GameState.NOT_STARTED;

  private Main() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static void check(MemoryMenu s) {
    checkState = new Timer(100, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        g = s.getGameState();
        if(g == GameState.WON || g == GameState.LOST) {
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
    MemoryMenu m = new MemoryMenu();
    m.initializeView();
    check(m);
    //SnakeMenu s = new SnakeMenu();
    //s.initializeView();
    //check(s);
  }
}
