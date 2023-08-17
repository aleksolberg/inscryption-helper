package no.colfermentada.utils;

import no.colfermentada.board.Board;
import no.colfermentada.cards.Card;
import no.colfermentada.cards.CostType;
import no.colfermentada.cards.Sigil;
import no.colfermentada.game.InvalidMoveException;

import java.util.ArrayList;

public final class Rules {
    public static boolean validateMoveBloodCostType(Board board, Card card, int slot, int[] sacrifices) throws InvalidMoveException {
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
            for (int sacrificeSlot : sacrifices) {
                if (sacrificeSlot != slot) {
                    throw new InvalidMoveException("Slot not empty");
                }
            }
        }

        // Check if there are cards in sacrifice slots
        for (int sacrificedSlot : sacrifices) {
            if (board.getPlayedCards()[sacrificedSlot] == null) {
                throw new InvalidMoveException("No card to sacrifice in slot " + sacrificedSlot);
            }
        }
        return true;
    }

    public static boolean validateMoveBonesCostType(Board board, Card card, int slot, int bones) throws InvalidMoveException {
        // Check if this is the right method
        if (card.getCostType() != CostType.Bones) {
            throw new InvalidMoveException("This method should only be used for cards with Bones cost type.");
        }

        int requiredBones = card.getCost();
        // Check if enough bones
        if (requiredBones < bones) {
            throw new InvalidMoveException("Not enough bones");
        }

        // Check if card is being placed in valid slot
        if (board.getPlayedCards()[slot] != null) {
            throw new InvalidMoveException("Slot not empty");
        }
        return true;
    }

    public static boolean validateMoveFreeCost(Board board, Card card, int slot) throws InvalidMoveException {
        // Check if this is the right method
        if (card.getCostType() != CostType.None) {
            throw new InvalidMoveException("This method should only be used for cards with None cost type.");
        }

        // Check if card is being placed in valid slot
        if (board.getPlayedCards()[slot] != null) {
            throw new InvalidMoveException("Slot not empty");
        }
        return true;
    }

    public static ArrayList<Integer> getAttackedSlots(Card card, int slot) {
        ArrayList<Integer> attackedSlots = new ArrayList<>();

        if (card.getSigils().contains(Sigil.BifurcatedStrike)) {
            for (int offset : new int[]{-1, 1}) {
                int targetSlot = slot + offset;
                if (targetSlot >= 0 && targetSlot < 4) {
                    attackedSlots.add(targetSlot);
                }
            }
        } else if (card.getSigils().contains(Sigil.TrifurcatedStrike)) {
            for (int offset : new int[]{-1, 0, 1}) {
                int targetSlot = slot + offset;
                if (targetSlot >= 0 && targetSlot < 4) {
                    attackedSlots.add(targetSlot);
                }
            }
        } else {
            attackedSlots.add(slot);
        }
        return attackedSlots;
    }
}
