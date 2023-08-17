package no.colfermentada.game;

import no.colfermentada.board.Board;
import no.colfermentada.cards.Card;
import no.colfermentada.cards.InvalidCardException;
import no.colfermentada.cards.Sigil;
import no.colfermentada.utils.Rules;

import java.util.ArrayList;

public class Game {// Question: The game should have players, not the other way around??
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

    public boolean gameWon() {
        return score >= 5;
    }

    public boolean gameLost() {
        return score <= -5;
    }

    public String displayGame() {
        return board.displayBoard() + "Score: " + score + "\nBones: " + bones + "\n";
    }

    protected void increaseScore(int amount) {
        score += amount;
    }

    protected void decreaseScore(int amount) {
        score -= amount;
    }

    public void increaseBones(int amount) {
        bones += amount;
    }

    public void decreaseBones(int amount) {
        bones -= amount;
    }

    public boolean isScoreAttacked(Card attackingCard, Card attackedCard) {
        if (attackedCard == null || attackedCard.getSigils().contains(Sigil.WaterBorne)) {
            return true;
        }
        if (attackingCard.getSigils().contains(Sigil.Airborne)) {
            return !attackedCard.getSigils().contains(Sigil.MightyLeap);
        } else {
            return false;
        }
    }

    public void attackScore(Card attackingCard, boolean isPlayer) {
        int power = attackingCard.getPower();
        if (!isPlayer) {
            power = -power;
        }
        score += power;
    }

    public void attackCard(Card attackingCard, Card attackedCard) {
        attackedCard.takeDamage(attackingCard.getPower());
    }

    public void playerAttacks() {
        for (int slot = 0; slot < Board.NUM_SLOTS; slot++) {
            Card attackingCard = board.getPlayedCards()[slot];
            if (attackingCard != null) {
                // There is a card at slot
                ArrayList<Integer> attackedSlots = Rules.getAttackedSlots(attackingCard, slot);
                for (Integer i : attackedSlots) {
                    Card attackedCard = board.getOpposingCards()[i];
                    if (isScoreAttacked(attackingCard, attackedCard)) {
                        attackScore(attackingCard, true);
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
        for (int slot = 0; slot < Board.NUM_SLOTS; slot++) {
            Card attackingCard = board.getOpposingCards()[slot];
            if (attackingCard != null) {
                // There is a card at slot
                ArrayList<Integer> attackedSlots = Rules.getAttackedSlots(attackingCard, slot);
                for (Integer i : attackedSlots) {
                    Card attackedCard = board.getPlayedCards()[i];
                    if (isScoreAttacked(attackingCard, attackedCard)) {
                        attackScore(attackingCard, false);
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

    public void executeTurn() {
        playerAttacks();
        board.opponentCardsApproaches();
        opponentAttacks();
    }
}
