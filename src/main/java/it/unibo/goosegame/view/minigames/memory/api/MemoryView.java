package it.unibo.goosegame.view.minigames.memory.api;

import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import it.unibo.goosegame.utilities.Position;

public interface MemoryView {

    Map<JButton, Position> returnCells();

    List<String> returnSymbols();
}
