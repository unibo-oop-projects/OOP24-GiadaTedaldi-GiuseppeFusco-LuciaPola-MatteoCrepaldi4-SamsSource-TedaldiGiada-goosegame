package it.unibo.goosegame.controller.minigames.memory;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import it.unibo.goosegame.model.minigames.memory.api.MemoryModel;
import it.unibo.goosegame.model.minigames.memory.impl.MemoryModelImpl;
import it.unibo.goosegame.view.minigames.memory.impl.MemoryViewImpl;


public class MemoryController extends JFrame {
    private MemoryModel model;
    private MemoryViewImpl view;
    
    public MemoryController() {
        this.model = new MemoryModelImpl();
        this.view = new MemoryViewImpl();
        this.add(view);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(model.getName());
        this.setPreferredSize(new Dimension(600, 400));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(true); 
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            this.model.hit(view.returnCells().get(jb));
            this.redraw();
            if (this.model.isOver()){
                JOptionPane.showMessageDialog(null, model.getResult() == 1 ? "You Win!" : "You Lose...");
                dispose();
            }
        };
        view.returnCells().entrySet().stream().forEach(e -> e.getKey().addActionListener(al));
    }

    public static MemoryController startGame() {
        return new MemoryController();
    }

    private void redraw() {
        for (var entry: view.returnCells().entrySet()){
            entry.getKey().setText(
                this.model.temporary(entry.getValue()).map(n -> view.returnSymbols().get(n)).orElse(" "));
            this.model.found(entry.getValue()).ifPresent(n -> {
                entry.getKey().setText(view.returnSymbols().get(n));
                entry.getKey().setEnabled(false);
            });
        }
    }
}
