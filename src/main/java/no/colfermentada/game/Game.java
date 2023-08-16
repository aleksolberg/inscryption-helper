package no.colfermentada.game;

import no.colfermentada.board.Board;
import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.cards.Card;
import no.colfermentada.cards.CostType;
import no.colfermentada.cards.InvalidCardException;
import no.colfermentada.cards.Sigil;
import no.colfermentada.deck.Deck;

import java.util.ArrayList;

public class Game {
    private Board board;
    private int score;
    private int bones;


    public Game(Board board) {
        this.board = board;
        score = 0;
        bones = 0;
    }

    public Game() throws InvalidCardException {
        this(new Board());
    }

    public Board getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    public int getBones() {
        return bones;
    }

    public String displayGame() {
        StringBuilder result = new StringBuilder(board.displayBoard());
        result.append("Score: ").append(score).append("\nBones: ").append(bones).append("\n");
        return result.toString();
    }

    protected void increaseScore(int amount) {
        score += amount;
    }

    protected void decreaseScore(int amount) {
        score -= amount;
    }

    public boolean gameWon() {
        return score >= 5;
    }

    public boolean gameLost() {
        return score <= -5;
    }

    public void increaseBones(int amount) {
        bones += amount;
    }

    public void decreaseBones(int amount) {
        bones -= amount;
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

    public void opponentAttacksScore(Card attackingCard) {
        score -= attackingCard.getPower();
    }

    public void attackCard(Card attackingCard, Card attackedCard) {
        attackedCard.takeDamage(attackingCard.getPower());
    }

    public void playerAttacks() {
        for (int slot = 0; slot < 4; slot++) {
            Card attackingCard = board.getPlayedCards()[slot];
            if (attackingCard != null) {
                // There is a card at slot
                ArrayList<Integer> attackedSlots = getAttackedSlots(attackingCard, slot);
                for (Integer i : attackedSlots) {
                    Card attackedCard = board.getOpposingCards()[i];
                    if (attacksScore(attackingCard, attackedCard)) {
                        playerAttacksScore(attackingCard);
                    } else {
                        attackCard(attackingCard, attackedCard);
                        if (attackedCard.isDead()) {
                            board.getOpposingCards()[i] = null;
                        }
                    }
                }
            }
        }
    }

    public void opponentAttacks() {
        for (int slot = 0; slot < 4; slot++) {
            Card attackingCard = board.getOpposingCards()[slot];
            if (attackingCard != null) {
                // There is a card at slot
                ArrayList<Integer> attackedSlots = getAttackedSlots(attackingCard, slot);
                for (Integer i : attackedSlots) {
                    Card attackedCard = board.getPlayedCards()[i];
                    if (attacksScore(attackingCard, attackedCard)) {
                        opponentAttacksScore(attackingCard);
                    } else {
                        attackCard(attackingCard, attackedCard);
                        if (attackedCard.isDead()) {
                            board.discardCardInSlot(i);
                        }
                    }
                }
            }
        }
    }
}
