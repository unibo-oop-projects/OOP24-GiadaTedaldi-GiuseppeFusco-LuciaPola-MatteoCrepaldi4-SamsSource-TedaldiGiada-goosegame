package it.unibo.goosegame.view.minigames.memory.impl;

import javax.swing.*;

import it.unibo.goosegame.utilities.Position;
import it.unibo.goosegame.view.minigames.memory.api.MemoryView;

import java.util.*;
import java.util.List;
import java.awt.*;

public class MemoryViewImpl extends JPanel implements MemoryView {
    
    private static int size = 4;
    private final List<String> symbols = new LinkedList<>(Arrays.asList("★", "♣", "☀", "⚽", "♫", "❤", "☂", "✈"));
    private final Map<JButton, Position> cells = new HashMap<>();
    
    public MemoryViewImpl() {
        this.setSize(100*size, 100*size);
        this.setLayout(new GridLayout(size,size));            
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	final JButton jb = new JButton();
                jb.setBackground(new Color(240, 120, 90));
                jb.setFont(new Font("SansSerif", Font.PLAIN, 36));
                this.cells.put(jb, new Position(j,i));
                this.add(jb);
            }
        }
    }

    public List<String> returnSymbols() {
        return List.copyOf(symbols);
    }

    @Override
    public Map<JButton, Position> returnCells() {
        return Map.copyOf(this.cells);
    }
    
}
