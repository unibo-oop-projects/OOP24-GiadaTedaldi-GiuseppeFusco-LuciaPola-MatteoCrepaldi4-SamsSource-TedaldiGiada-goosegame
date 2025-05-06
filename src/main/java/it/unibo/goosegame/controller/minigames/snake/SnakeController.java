package it.unibo.goosegame.controller.minigames.snake;

import javax.swing.*;

import it.unibo.goosegame.model.minigames.snake.api.SnakeModel;
import it.unibo.goosegame.model.minigames.snake.impl.SnakeModelImpl;
import it.unibo.goosegame.utilities.Direction;
import it.unibo.goosegame.view.minigames.snake.SnakeView;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeController extends JFrame {
    private SnakeModel model;
    private SnakeView view;
    private Timer timer;

    public SnakeController() {
        model = new SnakeModelImpl();
        view = new SnakeView(model);

        add(view);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(model.getName());
        view.setPreferredSize(new Dimension(600, 400));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false); 

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> model.changeDirection(Direction.UP);
                    case KeyEvent.VK_DOWN -> model.changeDirection(Direction.DOWN);
                    case KeyEvent.VK_LEFT -> model.changeDirection(Direction.LEFT);
                    case KeyEvent.VK_RIGHT -> model.changeDirection(Direction.RIGHT);
                }
            }
        });

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.move();
                view.repaint();
                if (model.isOver() || model.checkWin()) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, model.getResult() == 1 ? "You win!" : "You lose...");
                    dispose();
                }
            }
        });
        timer.start();
    }

    public static SnakeController startGame() {
        return new SnakeController();
    }
}

