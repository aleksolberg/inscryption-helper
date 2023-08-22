package no.colfermentada.moves;

import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.game.Game;

import java.util.ArrayList;

public class MoveSequenceEvaluator {

    public static ArrayList<MoveSequence> findValidMoveSequences(Game game, int maxDepth) {
        ArrayList<MoveSequence> sequences = new ArrayList<>();
        // Adding null move sequence
        sequences.add(new MoveSequence());
        findMoveSequencesRecursively(game, new MoveSequence(), sequences, maxDepth);
        return sequences;
    }
    public static void findMoveSequencesRecursively(Game game, MoveSequence currentSequence, ArrayList<MoveSequence> sequences, int depth) {
        if (depth == 0) {
            return;
        }

        ArrayList<Move> validSingleMoves = MoveEvaluator.findValidMoves(game);
        for (Move move : validSingleMoves) {
            Game gameCopy = new Game(game);
            try {
                move.executeMove(gameCopy);
                MoveSequence newSequence = new MoveSequence(currentSequence);
                newSequence.addMove(move);
                sequences.add(newSequence);
                findMoveSequencesRecursively(gameCopy, newSequence, sequences, depth - 1);
            } catch (InvalidBoardException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static int evaluateMoveSequenceSingleTurn(MoveSequence sequence, Game game) {
        Game gameCopy = new Game(game);
        try {
            sequence.executeMoveSequence(gameCopy);
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }
        gameCopy.executeTurn();
        return gameCopy.getScore();
    }

    public static MoveSequence findBestMoveSequenceSingleTurn(Game game) {
        ArrayList<MoveSequence> validSequences= findValidMoveSequences(game, 100);
        int maxScore = -9999;
        MoveSequence bestMoveSequence = null;
        int bestNumCardsUsed = 9999;
        int score;
        int numCardsUsed;
        for (MoveSequence sequence : validSequences) {
            score = evaluateMoveSequenceSingleTurn(sequence, game);
            numCardsUsed = sequence.getSequence().size();
            if (score > maxScore || (score == maxScore && numCardsUsed < bestNumCardsUsed)) { // Might need better logic here when looking at two turns
                maxScore = score;
                bestNumCardsUsed = numCardsUsed;
                bestMoveSequence = sequence;
            }
        }
        return bestMoveSequence;
    }

    public static MoveSequence[] findBestMoveSequenceTwoTurns(Game game) {
        ArrayList<MoveSequence> validFirstSequences = findValidMoveSequences(game, 100);
        int maxScore = -9999;
        MoveSequence[] bestMoveSequences = null;
        int bestNumCardsUsed = 9999;
        int score;
        int numCardsUsed;
        for (MoveSequence sequence1 : validFirstSequences) { // Problem: sequence is executed on game it is not calculated on.
            Game gameCopy = new Game(game);
            try {
                sequence1.executeMoveSequence(gameCopy);
                gameCopy.executeTurn();
                if (gameCopy.gameLost()) {
                    continue;
                }
                gameCopy.playerDrawsSquirrel();
                ArrayList<MoveSequence> validSecondSequences = findValidMoveSequences(gameCopy, 100);
                for (MoveSequence sequence2 : validSecondSequences) {
                    Game gameCopy2 = new Game(gameCopy);
                    try {
                        sequence2.executeMoveSequence(gameCopy2);
                    } catch (InvalidBoardException e) {
                        throw new RuntimeException(e);
                    }

                    gameCopy2.playerAttacks();

                    score = gameCopy2.getScore();
                    numCardsUsed = sequence1.getSequence().size() + sequence2.getSequence().size();
                    if (score > maxScore || (score == maxScore && numCardsUsed < bestNumCardsUsed)) {
                        maxScore = score;
                        sequence2.setOutcome(score);
                        bestNumCardsUsed = numCardsUsed;
                        bestMoveSequences = new MoveSequence[]{sequence1, sequence2};
                    }
                }
            } catch (InvalidBoardException e) {
                throw new RuntimeException(e);
            }
        }
        return bestMoveSequences;
    }
}
