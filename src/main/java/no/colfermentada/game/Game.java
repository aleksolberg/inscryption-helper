package no.colfermentada.game;

import no.colfermentada.board.Board;
import no.colfermentada.board.InvalidBoardException;
import no.colfermentada.cards.Card;
import no.colfermentada.cards.InvalidCardException;
import no.colfermentada.cards.Sigil;
import no.colfermentada.deck.Deck;
import no.colfermentada.deck.InvalidDeckException;
import no.colfermentada.players.Player;
import no.colfermentada.utils.Rules;

import java.util.ArrayList;

public class Game implements Cloneable{
    private Player player;
    private Board board;
    private int score;
    private int bones;


    public Game(Deck deck) {
        player = new Player(deck);
        board = new Board();
        score = 0;
        bones = 0;
    }

    public Game(Board board) {
        this(new Deck());
    }

    public Game() throws InvalidCardException {
        this(new Board());
    }

    public Game(Game other) {
        this.player = new Player(other.player);
        this.board = new Board(other.board);
        this.score = other.score;
        this.bones = other.bones;
    }

    public Player getPlayer() {
        return player;
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
        return board.displayBoard() + player.displayHand() + "Score: " + score + "\nBones: " + bones + "\n";
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

    public void playerDrawsSquirrel() {
        try {
            player.receiveCardInHand(board.drawSquirrel());
        } catch (InvalidBoardException e) {
            System.out.println(e.getMessage());
        }
    }

    public void playerDrawsFromDeckByIndex(int index) {
        try {
            player.drawSpecificCardFromDeckByIndex(index);
        } catch (InvalidDeckException e) {
            throw new RuntimeException(e.getMessage());
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
                    if (Rules.isScoreAttacked(attackingCard, attackedCard)) {
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
                    if (Rules.isScoreAttacked(attackingCard, attackedCard)) {
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
