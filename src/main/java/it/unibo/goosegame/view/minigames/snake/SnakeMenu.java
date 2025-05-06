package it.unibo.goosegame.view.minigames.snake;

import java.awt.event.ActionListener;

import it.unibo.goosegame.view.general.MinigameMenuAbstract;

//import it.unibo.goosegame.model.minigames.snake.impl.SnakeModelImpl;
//import it.unibo.goosegame.view.MinigamesMenu.AbstractMinigameMenu;

public class SnakeMenu extends MinigameMenuAbstract {

    public SnakeMenu(final String imgPath, final String title, final String infoMsg, final ActionListener al) {
        super(imgPath, title, infoMsg, al);
        /*
        SnakeModelImpl sm = new SnakeModelImpl();
        super(imgPath, sm.getName(), infoMsg, al);

        --Hypotetical main for Snake:

         ActionListener al = e -> {
            SnakeController.startGame();
        };
        new SnakeMenu("resources/backgroundSnake.png", "Snake Game", "Welcome to Snake Game! Here's some instruction to play:\\n" + //
                        "-Move the snake using < > ^ v buttons\\n" + //
                        "-eat 15 apples and you win\\n" + //
                        "-hit the walls and you lose", al);
         */
    }

}
