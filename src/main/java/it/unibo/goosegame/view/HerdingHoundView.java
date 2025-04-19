    package it.unibo.goosegame.view;

    import javax.swing.*;
    import java.awt.*;
    import java.util.List;

    import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
    import it.unibo.goosegame.model.minigames.herdinghound.api.Dog;
    import it.unibo.goosegame.model.minigames.herdinghound.impl.DogImpl;
    import it.unibo.goosegame.utilities.Pair;

    public class HerdingHoundView extends JPanel {

        private static final int CELL_SIZE = 40;
        private final HerdingHoundModel model;
        private final JFrame frame;

        public HerdingHoundView(HerdingHoundModel model) {
            this.model = model;
            this.frame = new JFrame("Herding Hound");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(this);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            setPreferredSize(new Dimension(model.getGrid() * CELL_SIZE, model.getGrid() * CELL_SIZE));
            setBackground(Color.WHITE);
            setFocusable(true);
            requestFocusInWindow();
        }

        public void initialize() {
            requestFocus();
        }

        public void updateView() {
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawGrid(g);
            drawBoxes(g);
            drawShadows(g);
            drawGoose(g);
            drawDog(g);
        }

        private void drawGrid(Graphics g) {
            for (int i = 0; i < model.getGrid(); i++) {
                for (int j = 0; j < model.getGrid(); j++) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        private void drawBoxes(Graphics g) {
            g.setColor(new Color(139, 69, 19));
            for (Pair<Integer, Integer> box : model.getBoxes()) {
                g.fillRect(box.getY() * CELL_SIZE, box.getX() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        private void drawShadows(Graphics g) {
            g.setColor(new Color(0, 0, 0, 100));
            for (Pair<Integer, Integer> shadow : model.getShadows()) {
                g.fillRect(shadow.getY() * CELL_SIZE, shadow.getX() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        private void drawGoose(Graphics g) {
            Pair<Integer, Integer> pos = model.getGoose().getCoord();
            g.setColor(Color.YELLOW);
            g.fillOval(pos.getY() * CELL_SIZE + 5, pos.getX() * CELL_SIZE + 5, CELL_SIZE - 10, CELL_SIZE - 10);
        }

        private void drawDog(Graphics g) {
            Pair<Integer, Integer> pos = model.getDog().getCoord();
            Dog.State state = model.getDog().getState();
            Color color;
            switch (state) {
                case ASLEEP -> color = Color.GRAY;
                case ALERT -> color = Color.ORANGE;
                case AWAKE -> color = Color.RED;
                default -> color = Color.BLACK;
            }
            g.setColor(color);
            g.fillOval(pos.getY() * CELL_SIZE + 5, pos.getX() * CELL_SIZE + 5, CELL_SIZE - 10, CELL_SIZE - 10);

            if (state != Dog.State.ASLEEP) {
                g.setColor(new Color(255, 0, 0, 100));
                for (Pair<Integer, Integer> visible : model.getVisible()) {
                    g.fillRect(visible.getY() * CELL_SIZE, visible.getX() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        public void showGameOver(it.unibo.goosegame.model.general.MinigamesModel.GameState state) {
            String message = switch (state) {
                case WON -> "Hai vinto!";
                case LOST -> "Sei stato visto! Hai perso.";
                default -> "Gioco terminato.";
            };
            JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }
