package no.colfermentada.players;

import no.colfermentada.cards.Card;
import no.colfermentada.deck.Deck;
import no.colfermentada.deck.InvalidDeckException;
import no.colfermentada.utils.CardDisplayer;

import java.util.ArrayList;

public class Player {
    private Deck deck;
    private ArrayList<Card> hand;
    private CardDisplayer displayer;

    public Player(Deck deck) {
        this.deck = deck;
        hand = new ArrayList<>();
        displayer = new CardDisplayer();
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

    public String displayHand() {
        return "Hand:\n" + displayer.displayCards(hand);
    }

    public void drawSpecificCardFromDeckByIndex(int index) throws InvalidDeckException {
        if (index >= deck.currentSize()) {
            throw new InvalidDeckException("Index of deck out of bounds");
        } else {
            hand.add(deck.drawSpecificCardByIndex(index));
        }
    }

    public void receiveCardInHand(Card card) {
        hand.add(card);
    }
}
