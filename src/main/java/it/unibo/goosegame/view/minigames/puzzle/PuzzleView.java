package it.unibo.goosegame.view.minigames.puzzle;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.util.Map;

import javax.swing.*;

import it.unibo.goosegame.controller.minigames.puzzle.PuzzleController;
import it.unibo.goosegame.model.minigames.puzzle.api.PuzzleModel;
import it.unibo.goosegame.model.minigames.puzzle.impl.PuzzleModelImpl;
import it.unibo.goosegame.utilities.Position;

public class PuzzleView extends JFrame{
    private static int GRID_SIZE = 5;
    private JButton[][] buttons = new JButton[GRID_SIZE][GRID_SIZE];
    private PuzzleController controller;
    private final JLabel timerLabel = new JLabel("Time: 02:30");
    private final JButton startButton = new JButton("Start");
    private Timer gameTimer;
    private int timeLeft;

    public PuzzleView() {
        PuzzleModel model = new PuzzleModelImpl();
        this.controller = new PuzzleController(model, this);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 0, 0));

        for(int i=0; i<GRID_SIZE; i++) {
            for(int j=0; j<GRID_SIZE; j++) {
                JButton button = new JButton();
                button.setEnabled(false);
                button.setBorder(BorderFactory.createEmptyBorder());
                button.setMargin(new Insets(0, 0, 0,0 ));
                button.setContentAreaFilled(false);
                buttons[i][j] = button;
                final int r = i;
                final int c = j;
                button.addActionListener(e -> {
                    this.controller.clickHandler(new Position(r, c));
                });
                gridPanel.add(button);
            }
        }

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(timerLabel, BorderLayout.WEST);
        controlPanel.add(startButton, BorderLayout.EAST);

        startButton.addActionListener(e -> {
            this.controller.shufflePuzzle();
            this.enableButtons(true);
            this.updateView();
            this.startGameTimer();
            startButton.setEnabled(false);
        });

        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        addComponentListener(new ComponentAdapter() {
            
            @Override
            public void componentResized(ComponentEvent e) {
                updateView();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                updateView();
            }
        });
        setVisible(true);
    }

    private void startGameTimer() {
        timeLeft = 150;
        timerLabel.setText("Time: 02:30");
        if(gameTimer != null && gameTimer.isRunning()) {
            gameTimer.stop();
        }
        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            int min = timeLeft / 60;
            int sec = timeLeft % 60;
            timerLabel.setText(String.format("Time: %02d:%02d", min, sec));
            if(timeLeft <= 0) {
                gameTimer.stop();
                JOptionPane.showMessageDialog(this, "Time is over! You lost.");
                this.gameOver();
            }
        });
        gameTimer.start();
    }

    public void updateView() {
        int cellWidth = buttons[0][0].getWidth();
        int cellHeight = buttons[0][0].getHeight();
        Map<Position,Integer> grid = this.controller.getModel().getGrid();
        for(int i=0; i<GRID_SIZE; i++) {
            for(int j=0; j<GRID_SIZE; j++) {
                int tileVal = grid.get(new Position(i, j));
                buttons[i][j].setIcon(loadAndScale(tileVal, cellWidth, cellHeight));
            }
        }
        repaint();
    }

    private Icon loadAndScale(int tileVal, int cellWidth, int cellHeight) {
        String path = "/img/puzzle25_tiles/tile25_"+tileVal+".png";
        URL imageUrl = getClass().getResource(path);
        if(imageUrl == null) {
            System.out.println("Immagine non trovata: "+path);
            return null;
        }
        ImageIcon origIcon = new ImageIcon(imageUrl);
        Image scaledImg = origIcon.getImage().getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    private void enableButtons(boolean enable) {
        for(int i=0; i<GRID_SIZE; i++) {
            for(int j=0; j<GRID_SIZE; j++) {
                buttons[i][j].setEnabled(enable);
            }
        }
    }

    public void showMinMessage() {
        JOptionPane.showMessageDialog(this, "Puzzle completed! You won.");
        this.gameOver();
    }

    public void stopTimer() {
        if(gameTimer != null) {
            gameTimer.stop();
        }
    }

    private void gameOver() {
        startButton.setEnabled(false);
        this.enableButtons(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}


