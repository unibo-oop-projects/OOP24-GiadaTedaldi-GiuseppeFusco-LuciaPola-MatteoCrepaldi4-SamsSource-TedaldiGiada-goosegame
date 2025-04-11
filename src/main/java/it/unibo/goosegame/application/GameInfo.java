package it.unibo.goosegame.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GameInfo extends JPanel{
    private final JButton backButton;
    private final GameMenu menuView;
    private final Image background;
    private final ImageIcon imageButton;
    private final JTextArea infoArea;
    private final JPanel textPanel;

    public GameInfo(GameMenu menu) {
        this.menuView = menu;
        this.background = new ImageIcon(getClass().getResource("/ImmagineMenu.png")).getImage();
        this.imageButton = new ImageIcon(getClass().getResource("/torna.png"));
        setLayout(new BorderLayout());

        infoArea = new JTextArea("""

                                            REGOLE GIOCO DELL'OCA

                                           1. Durante il proprio turno il giocatore dovrà lanciare due dadi (premere due tasti).
                                              L'avanzamento di casella dipenderà dalla somma di quest'ultimi.
                                           
                                           2. A seconda della cella in cui il giocatore si ferma, si possono verificare tre eventi:
                                                
                                              STAZIONAMENTO: si rimane fermi.

                                              MALUS: Il giocatore subisce una penalità:
                                                        - Retrocede di n celle
                                                        - Salta n turni
                                                        - Torna alla casella iniziale

                                              MINIGIOCO: Il giocatore dovrà affrontare un minigioco, in caso di vittoria può ottenere due tipi di "Carte Bonus":
                                                            - SALTA MALUS: permette di evitare il prossimo malus
                                                            - AVANZA CASELLA: permette di far avanzare il giocatore di n caselle

                                           3. Il primo giocatore a raggiungere l'ultima casella con il numero esatto di avanzamenti, VINCE.
                                            """);
        infoArea.setWrapStyleWord(true);
        infoArea.setLineWrap(true);
        infoArea.setEditable(false);
        infoArea.setCaretPosition(0);
        infoArea.setBackground(new Color(240, 240, 240, 255));
        infoArea.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        infoArea.setFont(new Font("Verdana", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setBackground(new Color(240, 240, 240, 90));

        textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);
        textPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 30, 50));
        textPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        backButton = menuView.createButtonIcon(imageButton, 35, 35);
        backButton.addActionListener((ActionEvent e) -> {
            menuView.showMenu();
        });
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 40,10));
        bottomPanel.setOpaque(false);
        bottomPanel.add(backButton, BorderLayout.WEST);

        add(textPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleComponents();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    private void scaleComponents() {
        double scaleX = (double) getWidth()/500;
        double scaleY = (double) getHeight()/400;
        double scale = Math.min(scaleX, scaleY);

        if(scale < 1) infoArea.setFont(new Font("Verdana", Font.BOLD, (int)(14*scale)));
        textPanel.setBorder(BorderFactory.createEmptyBorder((int)(100*scale), (int)(50*scale), (int)(30*scale), (int)(50*scale)));

        backButton.setPreferredSize(new Dimension((int)(35*scale), (int)(35*scale)));
        Image scaledImage = imageButton.getImage().getScaledInstance((int)(35*scale), (int)(35*scale), Image.SCALE_SMOOTH);
        backButton.setIcon(new ImageIcon(scaledImage));

        revalidate();
        repaint();
    }
}

