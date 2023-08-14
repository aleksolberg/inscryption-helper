package no.colfermentada.game;

import no.colfermentada.board.Board;
import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.cards.Card;
import no.colfermentada.cards.CostType;
import no.colfermentada.cards.Sigil;

import java.util.ArrayList;
import java.util.Objects;

public class Game {
    protected Board board;
    protected int score;
    protected int bones;

    public void Game(Board board) {
        this.board = board;
        score = 0;
        bones = 0;
    }

    public String displayBoard() {
        return "Approaching: \n" +
                board.displayCards(board.getApproachingCards()) +
                "Opposing: \n" +
                board.displayCards(board.getOpposingCards()) +
                "Played: \n" +
                board.displayCards(board.getPlayedCards()) +
                "Hand: \n" +
                board.displayCards(board.getHand()) +
                "Score: " + score +
                "\tBones: " + bones;
    }

    // Play card method for free or bones CostTypes
    public void playCard(int cardIndex, int slot) throws InvalidMoveException {
        Card card = board.getHand().get(cardIndex);
        CostType costType = card.getCostType();
        int cost = card.getCost();

        if (costType == CostType.Blood) {
            throw new InvalidMoveException("You must sacrifice");
        }

        if (costType == CostType.Bones && cost > bones) {
            throw new InvalidMoveException("Not enough bones");
        }

        try {
            board.placePlayerCard(card, slot);
        } catch (InvalidBoardException e) {
            throw new InvalidMoveException(e.getMessage());
        }
        bones -= cost;
    }

    // Play card method for blood CostType with one sacrifice
    public void playCard(int cardIndex, int slot, int sacrifice) throws InvalidMoveException {
        Card card = board.getHand().get(cardIndex);

        if ((board.getPlayedCards()[slot] != null) || sacrifice != slot) {
            throw new InvalidMoveException("Slot not empty");
        }

        if (card.getCostType() == CostType.Blood) {
            int requiredSacrifice = card.getCost();
            if (requiredSacrifice > 1) {
                throw new InvalidMoveException("Not enough sacrifice");
            } else {
                board.getPlayedCards()[sacrifice] = null;
            }
        } else {
            System.out.println("Sacrifice not needed. Making move without sacrificing.");
        }

        try {
            board.placePlayerCard(card, slot);
        } catch (InvalidBoardException e) {
            throw new InvalidMoveException(e.getMessage());
        }
    }

    // Play card method for blood CostType with more than one sacrifice
    public void playCard(int cardIndex, int slot, int[] sacrifices) throws InvalidMoveException {
        Card card = board.getHand().get(cardIndex);

        boolean sacrificesPlayedSlot = false;
        for (int sacrificedSlot :sacrifices) {
            if (sacrificedSlot == slot) {
                sacrificesPlayedSlot = true;
                break;
            }
        }

        if (board.getPlayedCards()[slot] != null || !sacrificesPlayedSlot) {
            throw new InvalidMoveException("Slot not empty");
        }

        if (card.getCostType() == CostType.Blood) {
            int requiredSacrifice = card.getCost();
            if (requiredSacrifice > sacrifices.length) {
                throw new InvalidMoveException("Not enough sacrifice");
            } else if (requiredSacrifice < sacrifices.length) {
                throw new InvalidMoveException("Too much sacrifice");
            } else {
                for (int sacrificedSlot : sacrifices) {
                    board.getPlayedCards()[sacrificedSlot] = null;
                }
            }
        } else {
            System.out.println("Sacrifice not needed. Making move without sacrificing.");
        }

        try {
            board.placePlayerCard(card, slot);
        } catch (InvalidBoardException e) {
            throw new InvalidMoveException(e.getMessage());
        }
    }

    public ArrayList<Integer> getAttackedSlots(Card card, int slot) {
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

    public boolean attacksScore(Card attackingCard, Card attackedCard) {
        if (attackedCard == null || attackedCard.getSigils().contains(Sigil.WaterBorne)) {
            return true;
        }
        if (attackingCard.getSigils().contains(Sigil.Airborne)) {
            return !attackedCard.getSigils().contains(Sigil.MightyLeap);
        } else {
            return false;
        }
    }

    public void playerAttacksScore(Card attackingCard) {
        score += attackingCard.getPower();
    }

    public void attackCard(Card attackingCard, Card attackedCard) {
        attackedCard.takeDamage(attackingCard.getPower());
    }

    public void playerAttacks() {
        for (int slot = 0; slot < 4; slot++) {
            Card attackingCard = board.getPlayedCards()[slot];
            if (attackingCard != null) {
                // There is a card at slot i
                ArrayList<Integer> attackedSlots = getAttackedSlots(attackingCard, slot);
                for (Integer j : attackedSlots) {
                    Card attackedCard = board.getOpposingCards()[j];
                    if (attacksScore(attackingCard, attackedCard)) {
                        playerAttacksScore(attackingCard);
                    } else {
                        attackCard(attackingCard, attackedCard);
                        if (attackedCard.isDead()) {
                            board.getOpposingCards()[j] = null;
                        }
                    }
                }
            }
        }
    }
}
