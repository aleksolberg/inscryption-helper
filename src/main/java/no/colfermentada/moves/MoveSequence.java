package no.colfermentada.moves;

import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.game.Game;

import java.util.ArrayList;

public class MoveSequence {
    private ArrayList<Move> sequence;
    private int outcome;

    public MoveSequence() {
        this.sequence = new ArrayList<>();
        this.outcome = -9999;
    }

    public MoveSequence(ArrayList<Move> sequence) {
        this.sequence = sequence;
    }

    public MoveSequence(MoveSequence other) {
        this.sequence = (ArrayList<Move>) other.sequence.clone(); // TODO: clone properly
    }

    public void addMove(Move move) {
        sequence.add(move);
    }

    public void executeMoveSequence(Game game) throws InvalidBoardException {
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
