package it.unibo.goosegame.view.minigames.snake;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

import it.unibo.goosegame.view.general.impl.MinigameMenuAbstract;

//import it.unibo.goosegame.model.minigames.snake.impl.SnakeModelImpl;
//import it.unibo.goosegame.view.MinigamesMenu.AbstractMinigameMenu;

/**
 * The SnakeMenu class represents the menu for the snake game.
 * It extends the MinigameMenuAbstract class and provides a specific implementation for the snake game.
 */
public class SnakeMenu extends MinigameMenuAbstract {

    private static final long serialVersionUID = 1L;
    /**
     * Constructor.
     * @param imgPath the path of the image background
     * @param title the title of the frame
     * @param infoMsg the message to show in the instructions
     * @param gamePanel the panel representing the game.
     * @param al the action listener for the start button
     */
    public SnakeMenu(final String imgPath, final String title, final String infoMsg, final JPanel gamePanel, final ActionListener al) {
        super(imgPath, title, infoMsg, gamePanel, al);
        /*
        SnakeModelImpl sm = new SnakeModelImpl();
        super(imgPath, sm.getName(), infoMsg, al);

        --Hypotetical main for Snake:

         ActionListener al = e -> {
            SnakeController.startGame();
        };
        new SnakeMenu("resources/backgroundSnake.png", "Snake Game", "Welcome to Snake Game!\\n" + 
                        "Here's some instruction to play:\\n" + //
                        "-Move the snake using < > ^ v buttons\\n" + //
                        "-eat 15 apples and you win\\n" + //
                        "-hit the walls and you lose", al);
         */
    }

}
