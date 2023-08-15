package no.colfermentada.board;

import no.colfermentada.cards.Card;
import no.colfermentada.cards.InvalidCardException;
import no.colfermentada.cards.Tribe;
import no.colfermentada.deck.Deck;
import no.colfermentada.deck.InvalidDeckException;

import java.util.ArrayList;

public class Board {
    // TODO: Actually handle exceptions earlier
    private static final int NUM_SLOTS = 4;
    private Card[] playedCards;
    private Card[] opposingCards;
    private Card[] approachingCards;
    private Deck deck;
    private ArrayList<Card> hand;
    private ArrayList<Card> squirrelDeck;
    private ArrayList<Card> discardedPile;
    private BoardDisplayer displayer = new BoardDisplayer();


    public Board(Deck deck) throws InvalidCardException {
        playedCards = new Card[NUM_SLOTS];
        opposingCards = new Card[NUM_SLOTS];
        approachingCards = new Card[NUM_SLOTS];

        hand = new ArrayList<Card>();
        this.deck = deck;

        Card squirrel = new Card.Builder().withName("Squirrel").withHealth(1).withPower(0).withTribe(Tribe.Squirrel).withCost(0).build();
        squirrelDeck = new ArrayList<Card>();
        for (int i = 0; i <= 10; i++) {
            squirrelDeck.add(squirrel.clone());
        }

        discardedPile = new ArrayList<Card>();
    }

    public Board() throws InvalidCardException {
        this(new Deck());
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

    public void drawSpecificCardFromDeck(int index) throws InvalidDeckException {
        if (index >= deck.currentSize()) {
            throw new InvalidDeckException("Index of deck out of bounds");
        } else {
            hand.add(deck.drawSpecificCard(index));
        }
    }

    public void drawSquirrel() throws InvalidBoardException {
        if (!squirrelDeck.isEmpty()) {
            hand.add(squirrelDeck.remove(0));
        } else {
            throw new InvalidBoardException("Cannot draw squirrel from empty sqirrel deck");
        }
    }

    public void placePlayerCard(Card card, int slot) throws InvalidBoardException {
        if (slot >= NUM_SLOTS) {
            throw new InvalidBoardException("Card placed out of bounds");
        }
        if (playedCards[slot] == null) {
            playedCards[slot] = card;
        } else {
            throw new InvalidBoardException("Slot not empty");
        }
    }

    public void placeOpponentCard(Card card, int slot) throws InvalidBoardException {
        if (slot >= NUM_SLOTS) {
            throw new InvalidBoardException("Card placed out of bounds");
        }
        if (approachingCards[slot] == null) {
            approachingCards[slot] = card;
        } else {
            throw new InvalidBoardException("Slot not empty");
        }
    }

    public void discardCard(int slot) {
        discardedPile.add(playedCards[slot]);
        playedCards[slot] = null;
    }

    public void opponentCardsApproaches() {
        for (int i = 0; i < NUM_SLOTS; i++) {
            if (approachingCards[i] != null && opposingCards[i] == null) {
                opposingCards[i] = approachingCards[i];
                approachingCards[i] = null;
            }
        }
    }

    public String displayCards(ArrayList<Card> cards) {
        return displayer.buildDisplayString(cards.toArray(new Card[0]));
    }

    public String displayCards(Card[] cards) {
        return displayer.buildDisplayString(cards);
    }

    /*public String displayCards(ArrayList<Card> cards) {
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
    }*/
}
