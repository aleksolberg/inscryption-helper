package no.colfermentada.board;

import no.colfermentada.cards.Card;
import no.colfermentada.cards.Sigil;
import no.colfermentada.cards.Tribe;
import no.colfermentada.deck.Deck;
import no.colfermentada.cards.CostType;

import java.util.ArrayList;

public class Board {
    protected Card[] playedCards;
    protected Card[] opposingCards;
    protected Card[] approachingCards;
    protected ArrayList<Card> hand;
    protected ArrayList<Card> currentDeck;
    protected ArrayList<Card> squirrelDeck;
    protected ArrayList<Card> discardedPile;


    public Board(Deck deck) {
        playedCards = new Card[4];
        opposingCards = new Card[4];
        approachingCards = new Card[4];

        hand = new ArrayList<Card>();
        currentDeck = deck.getCards();

        Card squirrel = new Card("Squirrel", 1, 0, CostType.None, 0, Tribe.Squirrel);
        squirrelDeck = new ArrayList<Card>();
        for (int i = 0; i <= 10; i++) {
            squirrelDeck.add(new Card(squirrel));
        }

        discardedPile = new ArrayList<Card>();
    }

    public Card[] getPlayedCards() {
        return playedCards;
    }

    public Card[] getOpposingCards() {
        return opposingCards;
    }

    public Card[] getApproachingCards() {
        return approachingCards;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void drawFromDeck(int card) {
        if (!currentDeck.isEmpty()) {
            hand.add(currentDeck.remove(card));
        }
    }

    public void drawSquirrel() {
        if (!squirrelDeck.isEmpty()) {
            hand.add(squirrelDeck.remove(0));
        }
    }

    public void placePlayerCard(Card card, int slot) throws InvalidBoardException {
        if (playedCards[slot] == null) {
            playedCards[slot] = card;
        } else {
            throw new InvalidBoardException("Slot not empty");
        }
    }

    public void placeOpponentCard(Card card, int slot) throws InvalidBoardException {
        if (approachingCards[slot] == null) {
            approachingCards[slot] = card;
        } else {
            throw new InvalidBoardException("Slot not empty");
        }
    }

    public void opponentCardsApproaches() {
        for (int i = 0; i < 4; i++) {
            if (approachingCards[i] != null && opposingCards[i] == null) {
                opposingCards[i] = approachingCards[i];
                approachingCards[i] = null;
            }
        }
    }

    public String displayCards(ArrayList<Card> cards) {
        StringBuilder builder = new StringBuilder();
        builder.append("+");
        for (Card card : cards) {
            builder.append("--------+");
        }
        builder.append("\n|");
        for (Card card : cards) {
            builder.append(String.format("%1$" + 8 + "s", card.getShortName())).append("|");
        }
        builder.append("\n|");
        for (Card card : cards) {
            builder.append(String.format("%1$" + 4 + "s" + "%2$" + 4 + "s", card.getShortTribe(), card.getCostString())).append("|");
        }
        // TODO: print sigils when implemented
        builder.append("\n|");
        for (Card card : cards) {
            builder.append(String.format("%1$" + 4 + "s" + "%2$" + 4 + "s", card.getPower(), card.getCurrentHealth())).append("|");
        }
        builder.append("\n+");
        for (Card card : cards) {
            builder.append("--------+");
        }
        builder.append("\n");

        return builder.toString();
    }

    public String displayCards(Card[] cards) {
        StringBuilder builder = new StringBuilder();
        builder.append("+");
        for (Card card : cards) {
            builder.append("--------+");
        }
        builder.append("\n|");
        for (Card card : cards) {
            if (card == null){
                builder.append("        |");
            } else {
                builder.append(String.format("%1$" + 8 + "s", card.getShortName())).append("|");
            }
        }
        builder.append("\n|");
        for (Card card : cards) {
            if (card == null) {
                builder.append("        |");
            } else {
                builder.append(String.format("%1$" + 4 + "s" + "%2$" + 4 + "s", card.getShortTribe(), card.getCostString())).append("|");
            }
        }
        // TODO: print sigils when implemented
        builder.append("\n|");
        for (Card card : cards) {
            if (card == null) {
                builder.append("        |");
            } else {
                builder.append(String.format("%1$" + 4 + "s" + "%2$" + 4 + "s", card.getPower(), card.getCurrentHealth())).append("|");
            }
        }
        builder.append("\n+");
        for (Card card : cards) {
            builder.append("--------+");
        }
        builder.append("\n");

        return builder.toString();
    }
}
