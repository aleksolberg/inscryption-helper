package no.colfermentada.moves;

import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.deck.InvalidDeckException;
import no.colfermentada.game.Game;
import no.colfermentada.players.InvalidPlayerException;

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
            } catch (InvalidBoardException | InvalidPlayerException | InvalidMoveException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static int evaluateMoveSequenceSingleTurn(MoveSequence sequence, Game game) {
        Game gameCopy = new Game(game);
        try {
            sequence.executeMoveSequence(gameCopy);
        } catch (InvalidBoardException | InvalidMoveException | InvalidPlayerException e) {
            throw new RuntimeException(e);
        }
        gameCopy.executeTurn();
        return gameCopy.getScore();
    }

    public static MoveSequence findBestMoveSequenceSingleTurn(Game game) {
        ArrayList<MoveSequence> validSequences= findValidMoveSequences(game, 100);
        int maxScore = Integer.MIN_VALUE;
        MoveSequence bestMoveSequence = null;
        int bestNumCardsUsed = Integer.MAX_VALUE;
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
        MoveSequence[] bestSequences = new MoveSequence[2];
        int bestScore = Integer.MIN_VALUE;
        ArrayList<MoveSequence> validFirstSequences = findValidMoveSequences(game, 100);

        for (MoveSequence firstSeq : validFirstSequences) {
            Game gameAfterFirstSeq = applyMoveSequence(new Game(game), firstSeq);
            gameAfterFirstSeq.executeTurn();

            if (gameAfterFirstSeq.gameLost()) continue;

            // Draw squirrel
            Game squirrelGame = new Game(gameAfterFirstSeq);
            try {
                squirrelGame.playerDrawsSquirrel();
            } catch (InvalidBoardException e) {
                throw new RuntimeException(e);
            }
            MoveSequence bestSecondSeqForSquirrel = findBestSecondSequence(squirrelGame);
            int squirrelScore = (bestSecondSeqForSquirrel != null) ? bestSecondSeqForSquirrel.getOutcome() : Integer.MIN_VALUE;

            // Draw from deck
            int meanDeckScore = computeMeanScoreForDeckDraw(gameAfterFirstSeq);

            // Compare and select the best
            if (squirrelScore > meanDeckScore && squirrelScore > bestScore) {
                bestScore = squirrelScore;
                bestSequences[0] = firstSeq;
                bestSequences[1] = bestSecondSeqForSquirrel;
            } else if (meanDeckScore >= squirrelScore && meanDeckScore >= bestScore) {
                bestScore = meanDeckScore;
                bestSequences[0] = firstSeq;
            }
            //System.out.println("SquirrelScore: " + squirrelScore);
        }
        //System.out.println("Best Score: " + bestScore);
        return bestSequences;
    }

    private static Game applyMoveSequence(Game game, MoveSequence sequence) {
        try {
            sequence.executeMoveSequence(game);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return game;
    }

    private static MoveSequence findBestSecondSequence(Game game) {
        ArrayList<MoveSequence> validSecondSequences = findValidMoveSequences(game, 100);
        int bestScore = Integer.MIN_VALUE;
        MoveSequence bestSeq = null;

        for (MoveSequence seq : validSecondSequences) {
            Game gameCopy = applyMoveSequence(new Game(game), seq);
            gameCopy.playerAttacks();
            int score = gameCopy.getScore();
            if (score > bestScore) {
                bestScore = score;
                bestSeq = seq;
                bestSeq.setOutcome(score);
            }
        }

        return bestSeq;
    }

    private static int computeMeanScoreForDeckDraw(Game game) {
        int totalScore = 0;
        int currentDeckSize = game.getPlayer().getDeck().currentSize();
        for (int i = 0; i < currentDeckSize; i++) {
            Game gameCopy = new Game(game);
            try {
                gameCopy.playerDrawsFromDeckByIndex(i);
            } catch (InvalidDeckException e) {
                throw new RuntimeException(e);
            }
            MoveSequence bestSecondSeqForCard = findBestSecondSequence(gameCopy);
            totalScore += bestSecondSeqForCard.getOutcome();
        }
        return totalScore / currentDeckSize;
    }
}
