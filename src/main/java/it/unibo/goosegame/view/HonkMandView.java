package it.unibo.goosegame.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HonkMandView {
    private JFrame frame;
    private JButton[] buttons = new JButton[4];
    private Color[] baseColors = {
        Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE
    };
    private Color[] brightColors = {
        baseColors[0].brighter(), baseColors[1].brighter(),
        baseColors[2].brighter(), baseColors[3].brighter()
    };

    public HonkMandView() {
        frame = new JFrame("HonkMand - Simon Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(2, 2));

        for (int i = 0; i < 4; i++) {
            JButton btn = new JButton();
            btn.setBackground(baseColors[i]);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            btn.setActionCommand(String.valueOf(i));
            buttons[i] = btn;
            frame.add(btn);
        }

        frame.setVisible(true);
    }

    public void setButtonListener(ActionListener listener) {
        for (JButton btn : buttons) {
            btn.addActionListener(listener);
        }
    }

    public void enableButtons(boolean enable) {
        for (JButton btn : buttons) {
            btn.setEnabled(enable);
        }
    }

    public void flashButton(int index) {
        JButton btn = buttons[index];
        Color original = baseColors[index];
        Color highlight = brightColors[index];

        btn.setBackground(highlight);
        Timer timer = new Timer(300, e -> btn.setBackground(original));
        timer.setRepeats(false);
        timer.start();
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public void playSound(int index) {
        Toolkit.getDefaultToolkit().beep();
    }
}
