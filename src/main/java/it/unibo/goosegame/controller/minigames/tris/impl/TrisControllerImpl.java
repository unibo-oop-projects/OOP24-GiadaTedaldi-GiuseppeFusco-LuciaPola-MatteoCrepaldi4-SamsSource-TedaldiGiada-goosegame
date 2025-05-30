package it.unibo.goosegame.controller.minigames.tris.impl;

import javax.swing.Timer;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.goosegame.controller.minigames.tris.api.TrisController;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.model.minigames.tris.api.TrisModel;
import it.unibo.goosegame.utilities.Position;
import it.unibo.goosegame.view.minigames.tris.api.TrisView;

/**
 * Implementation of the {@link TrisController} interface.
 * It manages the communication between the model and the view of a Tris(Tic-Tac-Toe) minigame.
 */
public class TrisControllerImpl implements TrisController {
    private static final int MAX_CHAR = 50;
    private static final int TIMER_DELAY = 1000;
    private static final int MAX_ROUNDS = 3;
    private static final int MAX_WINS = 2;
    private final TrisModel model;
    private final TrisView view;
    private boolean roundOver;
    private int humanWins;
    private int pcWins;
    private int rounds;

    /**
     * Constructs a new instance of {@link TrisControllerImpl}.
     * 
     * @param model TrisModel
     * @param view TrisView
     */
    @SuppressFBWarnings(value = "EI2", justification = "model and view are used internally and not exposed")
    public TrisControllerImpl(final TrisModel model, final TrisView view) {
        this.model = model;
        this.view = view;
        this.humanWins = 0;
        this.pcWins = 0;
        this.rounds = 0;
        this.roundOver = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startGame() {
        this.view.setController(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void makeMove(final Position position) {
        if (this.roundOver) {
            return;
        }
        if (!this.model.makeHumanMove(position)) {
            return;
        }
        this.view.updateButton(position, "X");
        this.checkRoundState();
        if (this.roundOver) {
            return;
        }
        this.model.makePcMove();
        this.updateView();
        this.checkRoundState();
    }

    /**
     * Updates the view.
     */
    private void updateView() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final Position pos = new Position(i, j);
                if (this.model.isPc(pos)) {
                    this.view.updateButton(pos, "O");
                }
            }
        }
    }

    /**
     * Checks whether the current round is over.
     */
    private void checkRoundState() {
        if (!this.model.isOver()) {
            return;
        }
        final GameState result = this.model.getGameState();
        if (result == GameState.ONGOING) {
            return;
        }
        this.roundOver = true;
        this.recordResult(result);
        switch (result) {
            case WON : this.view.setStatus("You win this round");
                break;
            case LOST : this.view.setStatus("PC wins this round");
                break;
            case TIE : this.view.setStatus("It's a draw!");
                break;
            default : break;
        }
        this.view.disableButtons();
        if (this.isMatchOver()) {
            this.view.endGame(this.finalMessage());
        } else {
            final Timer timer = new Timer(TIMER_DELAY, e -> {
                this.model.resetGame();
                this.view.resetGrid();
                this.view.setStatus("Round: " + (this.rounds + 1) + " Your turn!");
                this.roundOver = false;
            });
            timer.setRepeats(false);
            timer.start();
        }

    }

    /**
     * Records the current round result.
     * 
     * @param result the result of the current round
     */
    private void recordResult(final GameState result) {
        this.rounds++;
        if (result == GameState.WON) {
            this.humanWins++;
        } else if (result == GameState.LOST) {
            this.pcWins++;
        }
    }

    /**
     * @return the final message to be displayed at the end of the match.
     */
    private String finalMessage() {
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
        return finalMsg.toString();
    }

    /**
     * Checks whether the match is over.
     * 
     * @return true if the match is over, false otherwise
     */
    private boolean isMatchOver() {
        return this.humanWins == MAX_WINS || this.pcWins == MAX_WINS || this.rounds == MAX_ROUNDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {
        if (this.isMatchOver()) {
            if (this.humanWins > this.pcWins) {
                return GameState.WON;
            } else if (this.pcWins > this.humanWins) {
                return GameState.LOST;
            } else if (this.humanWins == this.pcWins) {
                return GameState.TIE;
            }
        }
        return GameState.ONGOING;
    }

}
