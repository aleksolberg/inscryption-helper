package no.colfermentada.deck;

import no.colfermentada.cards.*;
import no.colfermentada.utils.CardUtils;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> currentCards;
    private ArrayList<Card> permanentCards;

    public Deck() {
        currentCards = new ArrayList<Card>();
        permanentCards = new ArrayList<Card>();
    }

    public Deck(ArrayList<Card> cards) {
        this.currentCards = new ArrayList<>(cards);
        this.permanentCards = new ArrayList<>(cards);
    }

    public Deck(Deck other) {
        this.currentCards = (ArrayList<Card>) CardUtils.copyCardList(other.currentCards);
        this.permanentCards = (ArrayList<Card>) CardUtils.copyCardList(other.permanentCards);
    }

    public ArrayList<Card> getCurrentCards() {
        return new ArrayList<>(currentCards);
    }

    public ArrayList<Card> getPermanentCards() {
        return new ArrayList<>(permanentCards);
    }

    public void populateStandardDeck() {
        Card stoat = CardTemplate.createStoat();
        Card stuntedWolf = CardTemplate.createStuntedWolf();
        Card stinkbug = CardTemplate.createStinkbug();
        Card bullfrog = CardTemplate.createBullfrog();

        Collections.addAll(currentCards, stoat, stuntedWolf, stinkbug, bullfrog);
        Collections.addAll(permanentCards, stoat, stuntedWolf, stinkbug, bullfrog);
    }

    public void addCard(Card card) {
        currentCards.add(card);
        permanentCards.add(card);
    }

    public void returnCardToCurrent(Card card) throws InvalidDeckException {
        if (permanentCards.contains(card)) {
            currentCards.add(card);
        } else {
            throw new InvalidDeckException("Tried to add card to current deck that is not in permanent deck.");
        }

    }

    public void removeCardPermanently(Card card) {
        currentCards.remove(card);
        permanentCards.remove(card);
    }

    public boolean containsCard(Card card) {
        for (Card cardInDeck : currentCards) {
            if (card == cardInDeck) {
                return true;
            }
        }
        return false;
    }

    public int currentSize() {
        return currentCards.size();
    }

    public int permanentSize() {
        return permanentCards.size();
    }

    public void shuffle() {
        Collections.shuffle(currentCards);
    }

    public Card drawTopCard() throws InvalidDeckException {
        if (currentCards.isEmpty()) {
            throw new InvalidDeckException("Cannot draw card from empty deck");
        }
        return currentCards.remove(0);
    }

    public Card drawSpecificCard(int index) throws InvalidDeckException {
        if (currentCards.isEmpty()) {
            throw new InvalidDeckException("Cannot draw card from empty deck");
        }
        return currentCards.remove(index);
    }

    public  Card drawSpecificCard(Card card) throws InvalidDeckException {
        if (containsCard(card)) {
            currentCards.remove(card);
            return card;
        } else {
            throw new InvalidDeckException("Card not in deck");
        }
    }

    public void resetDeck () {
        currentCards.clear();
        currentCards.addAll(permanentCards);
    }
}
