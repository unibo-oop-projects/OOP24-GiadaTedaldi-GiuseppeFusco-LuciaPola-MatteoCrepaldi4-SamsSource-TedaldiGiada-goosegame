package it.unibo.goosegame.view.minigames.rockpaperscissors.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.goosegame.view.minigames.rockpaperscissors.api.RockPaperScissorsView;

/**
 * This class represents the view for the Rock-Paper-Scissors game.
 */
public class RockPaperScissorsViewImpl extends JFrame implements RockPaperScissorsView {

    private static final int RGB_WHITE = 255;
    private static final int IMAGE_SIZE = 100;
    private static final int FONT_SIZE = 14;
    private static final String FONT_STYLE = "Verdana";
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;
    private static final int BTNW = 120;
    private static final int BTNH = 40;
    private static final long serialVersionUID = 1L;

    private final JLabel text = new JLabel("Make your choice: ", SwingConstants.CENTER);
    private final JLabel playerLabel = new JLabel("PLAYER: 0", SwingConstants.CENTER);
    private final JLabel computerLabel = new JLabel("COMPUTER: 0", SwingConstants.CENTER);
    private final JLabel result = new JLabel("", SwingConstants.CENTER);
    private final JButton rock = new JButton("ROCK");
    private final JButton paper = new JButton("PAPER");
    private final JButton scissors = new JButton("SCISSORS");
    private final JPanel mainPanel;

    private ImageIcon currentPlayerIcon;
    private ImageIcon currentComputerIcon;
    private JButton playerChoice = new JButton();
    private JButton computerChoice = new JButton();

    /**
     * Create game's view.
     */
    public RockPaperScissorsViewImpl() {
        super("Rock Paper Scissors");
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(RGB_WHITE, RGB_WHITE, 180));
        super.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                scaleComponents();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeView() {
        currentPlayerIcon = new ImageIcon(RockPaperScissorsViewImpl.class.getResource("/scelta.png"));
        currentComputerIcon = currentPlayerIcon;

        playerChoice = createButtonIcon(currentPlayerIcon, IMAGE_SIZE, IMAGE_SIZE);
        playerChoice.setContentAreaFilled(false);
        playerChoice.setBorderPainted(false);
        playerChoice.setFocusPainted(false);

        computerChoice = createButtonIcon(currentComputerIcon, IMAGE_SIZE, IMAGE_SIZE);
        computerChoice.setContentAreaFilled(false);
        computerChoice.setBorderPainted(false);
        computerChoice.setFocusPainted(false);

        mainPanel.add(createTopPanel());
        mainPanel.add(createScorePanel());
        mainPanel.add(createChoicePanel());
        mainPanel.add(createResultPanel());

        setContentPane(mainPanel);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);

        scaleComponents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updatePlayerScore(final int score) {
        playerLabel.setText("PLAYER: " + score);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updateComputerScore(final int score) {
        computerLabel.setText("COMPUTER: " + score);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updateComputerChoice(final ImageIcon icon) {
        currentComputerIcon = icon == null ? null : new ImageIcon(icon.getImage());
        scaleComponents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updatePlayerChoice(final ImageIcon icon) {
        currentPlayerIcon = icon == null ? null : new ImageIcon(icon.getImage());
        scaleComponents();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updateResult(final String string) {
        result.setText(string);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addRockListener(final ActionListener l) {
        rock.addActionListener(l);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPaperListener(final ActionListener l) {
        paper.addActionListener(l);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addScissorsListener(final ActionListener l) {
        scissors.addActionListener(l);
    }
    /**
     * Enabled all the buttons.
     */
    @Override
    public void enableAllButtons() {
        rock.setEnabled(true);
        paper.setEnabled(true);
        scissors.setEnabled(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(
        value = "EI2", 
        justification = "Safe usage within UI context; no subclass is expected to override this behavior.")
    public void dispose() {
        super.dispose();
    }

    private JButton createButtonIcon(final ImageIcon image, final int w, final int h) {
        final JButton button = new JButton();
        button.setPreferredSize(new Dimension(w, h));
        button.setIcon(image);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        final Image scaledImage = image.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
        return button;
    }

    private JPanel createScorePanel() {
        final JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        namePanel.setOpaque(false);
        playerLabel.setFont(new Font(FONT_STYLE, Font.BOLD, FONT_SIZE));
        computerLabel.setFont(new Font(FONT_STYLE, Font.BOLD, FONT_SIZE));
        namePanel.add(playerLabel);
        namePanel.add(computerLabel);
        return namePanel;
    }

    private JPanel createTopPanel() {
        final JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setOpaque(false);
        text.setFont(new Font(FONT_STYLE, Font.PLAIN, FONT_SIZE));
        topPanel.add(text);
        topPanel.add(rock);
        topPanel.add(paper);
        topPanel.add(scissors);
        return topPanel;
    }

    private JPanel createResultPanel() {
        final JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        resultPanel.setOpaque(false);
        result.setFont(new Font(FONT_STYLE, Font.PLAIN, FONT_SIZE));
        resultPanel.add(result);
        return resultPanel;
    }

    private JPanel createChoicePanel() {
        final JPanel choicePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        choicePanel.setOpaque(false);
        choicePanel.add(playerChoice);
        choicePanel.add(computerChoice);
        return choicePanel;
    }

    private void scaleComponents() {
        final double scaleX = (double) getWidth() / WINDOW_WIDTH;
        final double scaleY = (double) getHeight() / WINDOW_HEIGHT;
        final double scale = Math.min(scaleX, scaleY);

        final int fontSize = Math.max(5, (int) (FONT_SIZE * scale));
        final int btnW = Math.max(65, (int) (BTNW * scale));
        final int btnH = Math.max(15, (int) (BTNH * scale));
        final int imageSize = Math.max(20, (int) (IMAGE_SIZE * scale));

        text.setFont(new Font(FONT_STYLE, Font.PLAIN, fontSize));
        playerLabel.setFont(new Font(FONT_STYLE, Font.BOLD, fontSize));
        computerLabel.setFont(new Font(FONT_STYLE, Font.BOLD, fontSize));
        result.setFont(new Font(FONT_STYLE, Font.PLAIN, fontSize));

        rock.setFont(new Font(FONT_STYLE, Font.PLAIN, fontSize));
        paper.setFont(new Font(FONT_STYLE, Font.PLAIN, fontSize));
        scissors.setFont(new Font(FONT_STYLE, Font.PLAIN, fontSize));

        rock.setPreferredSize(new Dimension(btnW, btnH / 2));
        paper.setPreferredSize(new Dimension(btnW, btnH / 2));
        scissors.setPreferredSize(new Dimension(btnW, btnH / 2));

        playerChoice.setPreferredSize(new Dimension(imageSize, imageSize));
        computerChoice.setPreferredSize(new Dimension(imageSize, imageSize));

        if (currentPlayerIcon != null) {
            final Image scaled = currentPlayerIcon.getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
            playerChoice.setIcon(new ImageIcon(scaled));
        }
        if (currentComputerIcon != null) {
            final Image scaled = currentComputerIcon.getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
            computerChoice.setIcon(new ImageIcon(scaled));
        }

        revalidate();
        repaint();
    }
}
