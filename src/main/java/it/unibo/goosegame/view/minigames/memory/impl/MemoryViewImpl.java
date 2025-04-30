package it.unibo.goosegame.view.minigames.memory.impl;
import javax.swing.*;

import it.unibo.goosegame.model.minigames.memory.api.MemoryModel;
import it.unibo.goosegame.utilities.Position;
import it.unibo.goosegame.view.minigames.memory.api.MemoryView;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;

public class MemoryViewImpl implements MemoryView {
    
    private static int size = 4;
    private final List<String> symbols = new LinkedList<>(Arrays.asList("★", "♣", "☀", "⚽", "♫", "❤", "☂", "✈"));
    private final Map<JButton, Position> cells = new HashMap<>();
    private MemoryModel model;
    private final JFrame frame = new JFrame();
    
    public MemoryViewImpl(MemoryModel model) {
        this.model = model;
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setTitle(model.getName());
        this.frame.setLayout(new GridLayout(size,size));  
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        this.frame.setSize(sw / 2, sh / 2);
        this.frame.setMinimumSize(new Dimension(sw / 3, sh / 3));
        this.frame.setResizable(true); 
        this.frame.setLocationRelativeTo(null);
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            this.model.hit(this.cells.get(jb));
            this.redraw();
            if (this.model.isOver()){
                JOptionPane.showMessageDialog(null, model.getResult() == 1 ? "You Win!" : "You Lose...");
                this.frame.dispose();
            }
        };
        
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	final JButton jb = new JButton();
                jb.setBackground(new Color(240, 120, 90));
                jb.setFont(new Font("SansSerif", Font.PLAIN, 36));
                this.cells.put(jb, new Position(j,i));
                this.frame.add(jb);
            }
        }
        this.cells.entrySet().stream().forEach(e -> e.getKey().addActionListener(al));  
    }

    private void redraw() {
        for (var entry: this.cells.entrySet()){
            entry.getKey().setText(
                this.model.temporary(entry.getValue()).map(n -> this.symbols.get(n)).orElse(" "));
            this.model.found(entry.getValue()).ifPresent(n -> {
                entry.getKey().setText(this.symbols.get(n));
                entry.getKey().setEnabled(false);
            });
        }
    } 

    @Override
    public void show() {
        this.frame.setVisible(true);
    }
}
