package no.colfermentada.moves;

import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.game.Game;
import no.colfermentada.players.InvalidPlayerException;

import java.util.ArrayList;

public class MoveSequence {
    private ArrayList<Move> sequence;
    private int outcome; // Is set in MoveSequenceEvaluator.

    public MoveSequence() {
        this.sequence = new ArrayList<>();
        this.outcome = Integer.MIN_VALUE;
    }

    public MoveSequence(ArrayList<Move> sequence) {
        this.sequence = sequence;
        this.outcome = Integer.MIN_VALUE;
    }

    public MoveSequence(MoveSequence other) {
        this.sequence = new ArrayList<>();
        for (Move move : other.sequence) {
            this.sequence.add(new Move(move));
        }
        this.outcome = other.outcome;
    }

    public void addMove(Move move) {
        sequence.add(move);
    }

    public void executeMoveSequence(Game game) throws InvalidBoardException, InvalidPlayerException, InvalidMoveException {
        for (Move move : sequence) {
            move.executeMove(game);
        }
    }

    public ArrayList<Move> getSequence() {
        return sequence;
    }

    public int getOutcome() {
        return outcome;
    }

    public void setOutcome(int outcome) {
        this.outcome = outcome;
    }
}
