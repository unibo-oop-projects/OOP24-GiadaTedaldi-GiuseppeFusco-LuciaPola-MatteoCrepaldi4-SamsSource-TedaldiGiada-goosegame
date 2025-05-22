package it.unibo.goosegame.view.minigames.memory.impl;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.model.minigames.memory.api.MemoryModel;
import it.unibo.goosegame.utilities.Position;
import it.unibo.goosegame.view.minigames.memory.api.MemoryView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

/**
 * The MemoryViewImpl class implements the MemoryView interface.
 * It is responsible for displaying the memory game to the user.
 */
public class MemoryViewImpl implements MemoryView {

    /**
     * The size of the buttons.
     */
    private static final int BTN_SIZE = 36;
    /**
     * The Blue color of the buttons (RGB).
     */
    private static final int B_COLOR = 90;
    /**
     * The Green color of the buttons (RGB).
     */
    private static final int G_COLOR = 120;
    /**
     * The Red color of the buttons (RGB).
     */
    private static final int R_COLOR = 240;
    /**
     * The size of the grid.
     */
    private static final int SIZE = 4;
    private final List<String> symbols = new LinkedList<>(Arrays.asList("★", "♣", "☀", "⚽", "♫", "❤", "☂", "✈"));
    private final Map<JButton, Position> cells = new HashMap<>();
    private final MemoryModel model;
    private final JFrame frame = new JFrame();

    /**
     * Constructor.
     * @param model the model
     */
    public MemoryViewImpl(final MemoryModel model) {
        this.model = model;
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setTitle(model.getName());
        this.frame.setLayout(new GridLayout(SIZE, SIZE));
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        this.frame.setSize(sw / 2, sh / 2);
        this.frame.setMinimumSize(new Dimension(sw / 3, sh / 3));
        this.frame.setResizable(true); 
        this.frame.setLocationRelativeTo(null);
        final ActionListener al = e -> {
            final var jb = (JButton) e.getSource();
            this.model.hit(this.cells.get(jb));
            this.redraw();
            if (this.model.isOver()) {
                JOptionPane.showMessageDialog(null, model.getGameState() == GameState.WON ? "You Win!" : "You Lose...");
                this.frame.dispose();
            }
        };

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                final JButton jb = new JButton();
                jb.setBackground(new Color(R_COLOR, G_COLOR, B_COLOR));
                jb.setFont(new Font("SansSerif", Font.PLAIN, BTN_SIZE));
                this.cells.put(jb, new Position(j, i));
                this.frame.add(jb);
            }
        }
        this.cells.entrySet().stream().forEach(e -> e.getKey().addActionListener(al));
    }

    private void redraw() {
        for (final var entry: this.cells.entrySet()) {
            entry.getKey().setText(
                this.model.temporary(entry.getValue()).map(this.symbols::get).orElse(" "));
            this.model.found(entry.getValue()).ifPresent(n -> {
                entry.getKey().setText(this.symbols.get(n));
                entry.getKey().setEnabled(false);
            });
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        this.frame.setVisible(true);
    }
}
