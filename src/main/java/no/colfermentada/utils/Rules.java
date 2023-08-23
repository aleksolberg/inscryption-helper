package no.colfermentada.utils;

import no.colfermentada.board.Board;
import no.colfermentada.cards.Card;
import no.colfermentada.cards.CostType;
import no.colfermentada.cards.Sigil;
import no.colfermentada.moves.InvalidMoveException;

import java.util.ArrayList;

public final class Rules {
    public static void validateMoveBloodCostType(Board board, Card card, int slot, int[] sacrifices) throws InvalidMoveException {
        // Check if this is the right method
        if (card.getCostType() != CostType.Blood) {
            throw new InvalidMoveException("This method should only be used for cards with Blood cost type.");
        }

        int requiredSacrifice = card.getCost();

        // Check if correct amount of sacrifice
        if (sacrifices.length != requiredSacrifice) {
            throw new InvalidMoveException("Invalid number of sacrifices.");
        }

        // Check if card is being placed in valid slot
        if (board.getPlayedCards()[slot] != null) {
            boolean validSlot = false;
            for (int sacrificeSlot : sacrifices) {
                if (sacrificeSlot == slot) {
                    validSlot = true;
                    break;
                }
            }
            if (!validSlot) {
                throw new InvalidMoveException("Slot not empty");
            }
        }

        // Check if there are cards in sacrifice slots
        for (int sacrificedSlot : sacrifices) {
            if (board.getPlayedCards()[sacrificedSlot] == null) {
                throw new InvalidMoveException("No card to sacrifice in slot " + sacrificedSlot);
            }
        }
    }

    public static void validateMoveBonesCostType(Board board, Card card, int slot, int bones) throws InvalidMoveException {
        // Check if this is the right method
        if (card.getCostType() != CostType.Bones) {
            throw new InvalidMoveException("This method should only be used for cards with Bones cost type.");
        }

        int requiredBones = card.getCost();
        // Check if enough bones
        if (requiredBones > bones) {
            throw new InvalidMoveException("Not enough bones");
        }

        // Check if card is being placed in valid slot
        if (board.getPlayedCards()[slot] != null) {
            throw new InvalidMoveException("Slot not empty");
        }
    }

    public static void validateMoveFreeCost(Board board, Card card, int slot) throws InvalidMoveException {
        // Check if this is the right method
        if (card.getCostType() != CostType.None) {
            throw new InvalidMoveException("This method should only be used for cards with None cost type.");
        }

        // Check if card is being placed in valid slot
        if (board.getPlayedCards()[slot] != null) {
            throw new InvalidMoveException("Slot not empty");
        }
    }

    public static ArrayList<Integer> getAttackedSlots(Card card, int slot) {
        ArrayList<Integer> attackedSlots = new ArrayList<>();

        if (card.getSigils().contains(Sigil.BifurcatedStrike)) {
            for (int offset : new int[]{-1, 1}) {
                int targetSlot = slot + offset;
                if (targetSlot >= 0 && targetSlot < Board.NUM_SLOTS) {
                    attackedSlots.add(targetSlot);
                }
            }
        } else if (card.getSigils().contains(Sigil.TrifurcatedStrike)) {
            for (int offset : new int[]{-1, 0, 1}) {
                int targetSlot = slot + offset;
                if (targetSlot >= 0 && targetSlot < Board.NUM_SLOTS) {
                    attackedSlots.add(targetSlot);
                }
            }
        } else {
            attackedSlots.add(slot);
        }
        return attackedSlots;
    }

    public static boolean isScoreAttacked(Card attackingCard, Card attackedCard) {
        if (attackedCard == null || attackedCard.getSigils().contains(Sigil.WaterBorne)) {
            return true;
        }
        if (attackingCard.getSigils().contains(Sigil.Airborne)) {
            return !attackedCard.getSigils().contains(Sigil.MightyLeap);
        } else {
            return false;
        }
    }
}
