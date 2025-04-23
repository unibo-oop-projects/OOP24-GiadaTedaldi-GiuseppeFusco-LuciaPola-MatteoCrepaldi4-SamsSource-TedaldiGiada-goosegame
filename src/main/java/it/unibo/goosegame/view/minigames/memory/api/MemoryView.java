package it.unibo.goosegame.view.minigames.memory.api;

import java.util.Map;

import javax.swing.JButton;

import it.unibo.goosegame.utilities.Position;

public interface MemoryView {

    Map<JButton, Position> returnCells();

    String[] returnSymbols();
}
