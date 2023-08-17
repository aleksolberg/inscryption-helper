package no.colfermentada.board;

import no.colfermentada.cards.Card;
import no.colfermentada.cards.InvalidCardException;
import no.colfermentada.cards.Tribe;
import no.colfermentada.deck.Deck;
import no.colfermentada.deck.InvalidDeckException;
import no.colfermentada.utils.CardDisplayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Board {
    public static final int NUM_SLOTS = 4;
    private Card[] playedCards;
    private Card[] opposingCards;
    private Card[] approachingCards;
    private ArrayList<Card> squirrelDeck;
    private ArrayList<Card> discardedPile;
    private CardDisplayer displayer;

    public Board() throws InvalidCardException {
        playedCards = new Card[NUM_SLOTS];
        opposingCards = new Card[NUM_SLOTS];
        approachingCards = new Card[NUM_SLOTS];

        Card squirrel = new Card.Builder()
                .withName("Squirrel")
                .withHealth(1)
                .withPower(0)
                .withTribe(Tribe.Squirrel)
                .withCost(0)
                .build();
        squirrelDeck = new ArrayList<Card>();
        for (int i = 0; i <= 10; i++) {
            squirrelDeck.add(squirrel.clone());
        }

        discardedPile = new ArrayList<Card>();
        displayer = new CardDisplayer();
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

    public Card drawSquirrel() throws InvalidBoardException {
        if (!squirrelDeck.isEmpty()) {
            return (squirrelDeck.remove(0));
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

    public void discardCardInSlot(int slot) {
        discardedPile.add(playedCards[slot]);
        playedCards[slot] = null;
    }

    public void removeOpposingCardInSlot(int slot) {
        opposingCards[slot] = null;
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
        return displayer.displayCards(cards);
    }

    public String displayCards(Card[] cards) {
        return displayer.displayCards(Arrays.asList(cards));
    }

    public String displayBoard() {
        return "Approaching: \n" +
                displayer.displayCards(Arrays.asList(approachingCards)) +
                "Opposing: \n" +
                displayer.displayCards(Arrays.asList(opposingCards)) +
                "Played: \n" +
                displayer.displayCards(Arrays.asList(playedCards));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Arrays.equals(playedCards, board.playedCards) && Arrays.equals(opposingCards, board.opposingCards) && Arrays.equals(approachingCards, board.approachingCards) && Objects.equals(squirrelDeck, board.squirrelDeck) && Objects.equals(discardedPile, board.discardedPile);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(squirrelDeck, discardedPile, displayer);
        result = 31 * result + Arrays.hashCode(playedCards);
        result = 31 * result + Arrays.hashCode(opposingCards);
        result = 31 * result + Arrays.hashCode(approachingCards);
        return result;
    }
}
