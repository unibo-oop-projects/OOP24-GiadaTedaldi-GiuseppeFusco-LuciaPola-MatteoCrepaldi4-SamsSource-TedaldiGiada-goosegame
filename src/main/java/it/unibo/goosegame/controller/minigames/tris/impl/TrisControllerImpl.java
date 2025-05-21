package it.unibo.goosegame.controller.minigames.tris.impl;

import javax.swing.Timer;

import it.unibo.goosegame.controller.minigames.tris.api.TrisController;
import it.unibo.goosegame.model.minigames.tris.api.TrisModel;
import it.unibo.goosegame.utilities.Position;
import it.unibo.goosegame.view.minigames.tris.api.TrisView;
import it.unibo.goosegame.view.minigames.tris.impl.TrisViewImpl;

/**
 * Implementation of the {@link TrisController} interface.
 * It manages the communication between the model and the view of a Tris(Tic-Tac-Toe) minigame.
 */
public class TrisControllerImpl implements TrisController {
    private static final int MAX_CHAR = 50;
    private final TrisModel model;
    private final TrisView view;
    private boolean gameOver;
    private int humanWins;
    private int pcWins;
    private int rounds;

    /**
     * Constructs a new instance of {@link TrisControllerImpl}.
     * 
     * @param model the {@link TrisModel} representing the game logic
     */
    public TrisControllerImpl(final TrisModel model) {
        this.model = model;
        this.view = new TrisViewImpl(this);
        this.humanWins = 0;
        this.pcWins = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void movesMaker(final Position position) {
        if (this.gameOver) {
            return;
        }

        if (this.model.makeHumanMove(position)) {
            this.view.updateButton(position, "X");
            if (this.model.isOver()) {
                endGame();
                return;
            }
            this.model.makePcMove();
            updateView();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateView() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final Position pos = new Position(i, j);
                if (this.model.isPc(pos)) {
                    this.view.updateButton(pos, "O");
                }
            }
        }
        if (this.model.isOver()) {
            endGame();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endGame() {
        this.gameOver = true;
        final int result = this.model.getResult();
        switch (result) {
            case 1:
                this.humanWins++;
                this.view.setStatus("You win this round!"); 
                break;
            case -1:
                this.pcWins++;
                this.view.setStatus("PC wins this round!"); 
                break;
            case 3: 
                this.view.setStatus("Draw!"); 
                break;
            case 0: 
                this.view.setStatus("Still playing..."); 
                return;
            default: 
                break;
        }
        this.rounds++;
        if (this.humanWins == 2 || this.pcWins == 2 || this.rounds == 3) {
            final StringBuilder finalMsg = new StringBuilder(MAX_CHAR);
            finalMsg.append("FINAL SCORE - You: ")
                .append(this.humanWins)
                .append(" PC: ")
                .append(this.pcWins)
                .append('\n');
            if (this.humanWins > this.pcWins) {
                finalMsg.append("YOU WIN!");
            } else if (this.pcWins > this.humanWins) {
                finalMsg.append("PC WINS!");
            } else {
                finalMsg.append("IT'S A DRAW!");
            }
            this.view.disableButtons();
            this.view.closeGame(finalMsg.toString());
        } else {
            this.view.disableButtons();
            final Timer timer = new Timer(1000, e -> {
                this.model.resetGame();
                this.view.setStatus("Round: " + (this.rounds + 1) + " Your turn!");
                this.view.resetGrid();
                this.gameOver = false;
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

}
