package it.unibo.goosegame.view.gamemenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * The class implements the game information window.
 */
public class GameInfo extends JPanel {
    private static final int BUTTON_SIZE = 35;
    private static final int FONT_SIZE = 14;
    private static final int WINDOW_HEIGHT = 400;
    private static final int WINDOW_WIDTH = 500;
    private static final int MARGIN_TOP = 100;
    private static final int MARGIN_BOTTOM = 30;
    private static final int BOTTOM_MARGIN = 40;
    private static final int MARGIN_LEFT = 50;
    private static final int MARGIN_RIGHT = 50;
    private static final int COLOR = 240;
    private static final int COLOR_WHITE = 255;
    private static final long serialVersionUID = 1L;

    @SuppressFBWarnings("SE_TRANSIENT_FIELD_NOT_RESTORED")
    private final transient GameMenu menuView;
    private final transient Image background;
    private final ImageIcon imageButton;

    private JButton backButton;
    private JTextArea infoArea;
    private JPanel textPanel;
    /**
     * @param menu The reference to the main menu to switch views.
     */
    @SuppressFBWarnings("EI2")
    public GameInfo(final GameMenu menu) {
        this.menuView = Objects.requireNonNull(menu);
        this.background = new ImageIcon(GameInfo.class.getResource("/ImmagineMenu.png")).getImage();
        this.imageButton = new ImageIcon(GameInfo.class.getResource("/torna.png"));
        super.setLayout(new BorderLayout());
    }

    /**
     * Initializes and sets up the components of the GameInfo view.
     */
    public void initializeView() {
        infoArea = new JTextArea(""" 
        RULES OF THE GOOSE GAME

        1.  During their turn, the player must roll two dice (by pressing two buttons).
            The number of spaces moved forward will depend on the sum of the dice.

        2.  Depending on the cell where the player lands, one of three events may occur:
                STATIONARY: The player remains on the same cell.

                MALUS: The player receives a penalty:
                    - Moves back by n cells
                    - Skips n turns
                    - Returns to the starting cell

                MINIGAME: The player must complete a minigame. If they win, they may receive one of two types of "Bonus Cards":
                    - SKIP MALUS: Allows the player to avoid the next malus
                    - ADVANCE SPACES: Allows the player to move forward by n cells

        3. The first player to reach the last square with the exact number of moves WINS.
        """);
        infoArea.setWrapStyleWord(true);
        infoArea.setLineWrap(true);
        infoArea.setEditable(false);
        infoArea.setCaretPosition(0);
        infoArea.setBackground(new Color(COLOR, COLOR, COLOR, COLOR_WHITE));
        infoArea.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        infoArea.setFont(new Font("Verdana", Font.BOLD, FONT_SIZE));

        final JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setBackground(new Color(COLOR, COLOR, COLOR, 90));

        textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);
        textPanel.setBorder(BorderFactory.createEmptyBorder(MARGIN_TOP, MARGIN_LEFT, MARGIN_BOTTOM, MARGIN_RIGHT));
        textPanel.add(scrollPane, BorderLayout.CENTER);

        final JPanel bottomPanel = new JPanel();
        backButton = menuView.createButtonIcon(imageButton, BUTTON_SIZE, BUTTON_SIZE);
        backButton.addActionListener((ActionEvent e) -> {
            menuView.showMenu();
        });
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, BOTTOM_MARGIN, 10));
        bottomPanel.setOpaque(false);
        bottomPanel.add(backButton, BorderLayout.WEST);

        add(textPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                scaleComponents();
            }
        });
    }

    /**
     * @param g the graphics context used for rendering.
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    private void scaleComponents() {
        final double scaleX = (double) getWidth() / WINDOW_WIDTH;
        final double scaleY = (double) getHeight() / WINDOW_HEIGHT;
        final double scale = Math.min(scaleX, scaleY);
        final double newButtonSize = BUTTON_SIZE * scale;

        if (scale < 1) {
            infoArea.setFont(new Font("Verdana", 
                Font.BOLD, (int) (FONT_SIZE * scale)));
        }
        textPanel.setBorder(BorderFactory.createEmptyBorder((int) (MARGIN_TOP * scale), 
            (int) (MARGIN_LEFT * scale), (int) (MARGIN_BOTTOM * scale), (int) (MARGIN_RIGHT * scale)));
        final Dimension d = new Dimension((int) newButtonSize, (int) newButtonSize);
        backButton.setPreferredSize(d);
        final Image scaledImage = imageButton.getImage().getScaledInstance((int) newButtonSize, 
            (int) newButtonSize, Image.SCALE_SMOOTH);
        backButton.setIcon(new ImageIcon(scaledImage));

        revalidate();
        repaint();
    }
}
