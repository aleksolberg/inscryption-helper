package no.colfermentada.utils;

import no.colfermentada.board.Board;
import no.colfermentada.cards.Card;
import no.colfermentada.cards.CostType;
import no.colfermentada.game.Game;
import no.colfermentada.game.InvalidMoveException;
import no.colfermentada.moves.Move;

public class MoveValidator {
    public static boolean isValidMove(Move move, Game game) {
        Card card = move.getCard();
        int slot = move.getSlot();
        Board board = game.getBoard();
        int[] sacrifices = move.getSacrifices();

        try {
            if (card.getCostType() == CostType.Blood && sacrifices != null) {
                return Rules.validateMoveBloodCostType(board, card, slot, sacrifices);
            } else if (move.getCard().getCostType() == CostType.Bones) {
                return Rules.validateMoveBonesCostType(board, card, slot, game.getBones());
            } else {
                return Rules.validateMoveFreeCost(board, card, slot);
            }
        } catch (InvalidMoveException e) {
            return false;
        }
    }
}
