package no.colfermentada.moves;

import no.colfermentada.board.Board;
import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.cards.Card;
import no.colfermentada.cards.CostType;
import no.colfermentada.game.Game;
import no.colfermentada.utils.MoveValidator;

import java.util.ArrayList;
import java.util.List;

public class MoveEvaluator {
    public static ArrayList<Move> findValidMoves(Game game) {
        ArrayList<Move> validMoves = new ArrayList<>();
        ArrayList<Card> hand = game.getPlayer().getHand();
        for (Card card : hand) {
            for (int slot = 0; slot < Board.NUM_SLOTS; slot++) {
                if (card.getCostType() == CostType.None || card.getCostType() == CostType.Bones) {
                    Move move = new Move(card, slot);
                    if (MoveValidator.isValidMove(move, game)) {
                        validMoves.add(move);
                    }
                } else if (card.getCostType() == CostType.Blood) {
                    int cost = card.getCost();
                    ArrayList<Integer> playedCardSlots = game.getBoard().playerSlotsWithCards();
                    List<List<Integer>> sacrificeCombinations = generateCombinations(playedCardSlots, cost);
                    for (List<Integer> sacrificeCombination : sacrificeCombinations) {
                        Move move = new Move(card, slot, sacrificeCombination.stream().mapToInt(Integer::intValue).toArray());
                        if (MoveValidator.isValidMove(move,game)) {
                            validMoves.add(move);
                        }
                    }
                }
            }
        }
        return validMoves;
    }

    public static <T> List<List<T>> generateCombinations(List<T> elements, int k) {
        List<List<T>> result = new ArrayList<>();
        if (k == 0) {
            result.add(new ArrayList<>());
            return result;
        }
        if (elements.isEmpty()) {
            return result;
        }
        List<T> currentElementSubset = new ArrayList<>(elements);
        T x = currentElementSubset.remove(0);
        for (List<T> subset : generateCombinations(currentElementSubset, k - 1)) {
            subset.add(0, x);
            result.add(subset);
        }
        result.addAll(generateCombinations(currentElementSubset, k));
        return result;
    }

    public static int evaluateMove(Move move, Game game) {
        Game gameClone = null;
        try {
            gameClone = game.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        try {
            move.executeMove(gameClone);
        } catch (InvalidBoardException e) {
            throw new RuntimeException(e);
        }
        gameClone.executeTurn();
        return gameClone.getScore();
    }
}
