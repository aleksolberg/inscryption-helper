package no.colfermentada.players;

import no.colfermentada.cards.Card;
import no.colfermentada.deck.Deck;
import no.colfermentada.deck.InvalidDeckException;
import no.colfermentada.utils.CardUtils;

import java.util.ArrayList;

public class Player {
    private Deck deck;
    private ArrayList<Card> hand;

    public Player(Deck deck) {
        this.deck = deck;
        hand = new ArrayList<>();
    }

    public Player(Player other) {
        this.deck = new Deck(other.deck);
        this.hand = (ArrayList<Card>) CardUtils.copyCardList(other.hand);
    }

    public Player() {
        this(new Deck());
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Deck getDeck() {
        return deck;
    }

    public void receiveCardInDeck(Card card) {
        deck.addCard(card);
    }

    public void receiveCardInHand(Card card) {
        hand.add(card);
    }

    public void drawSpecificCardFromDeck(int index) throws InvalidDeckException {
        if (index >= deck.currentSize()) {
            throw new InvalidDeckException("Index of deck out of bounds");
        } else {
            hand.add(deck.drawSpecificCard(index));
        }
    }

    public void drawSpecificCardFromDeck(Card card) throws InvalidDeckException {
        if (deck.containsCard(card)) {
            hand.add(deck.drawSpecificCard(card));
        } else {
            throw new InvalidDeckException("Card not in deck");
        }
    }

    public void drawTopCardFromDeck() throws InvalidDeckException {
        if (deck.currentSize() <= 0) {
            throw new InvalidDeckException("Deck is empty");
        } else {
            hand.add(deck.drawTopCard());
        }
    }

    public Card popCardFromHand(Card card) throws InvalidPlayerException {
        if (!hand.contains(card)) {
            throw new InvalidPlayerException("Card not in hand");
        } else {
            hand.remove(card);
            return card;
        }
    }

    public Card popCardFromHand(int index) throws InvalidPlayerException {
        if (index >= hand.size()) {
            throw new InvalidPlayerException("Index of hand out of bounds");
        } else {
            return hand.remove(index);
        }
    }

    public Card findCardInHand(Card card) throws InvalidPlayerException {
        for (Card cardInHand : hand) {
            if (cardInHand.equals(card)) {
                return cardInHand;
            }
        }
        throw new InvalidPlayerException("Card not in hand");
    }
}
