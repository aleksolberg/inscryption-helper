package no.colfermentada.deck;

import no.colfermentada.cards.*;

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

    public ArrayList<Card> getCurrentCards() {
        return new ArrayList<>(currentCards);
    }

    public ArrayList<Card> getPermanentCards() {
        return permanentCards;
    }

    public void populateStandardDeck() throws InvalidCardException {
        Card stoat = new Card.Builder()
                .withName("Stoat")
                .withHealth(3)
                .withPower(1)
                .withCostType(CostType.Blood)
                .withCost(1)
                .withTribe(Tribe.None)
                .build();
        Card stuntedWolf = new Card.Builder()
                .withName("Stunted Wolf")
                .withHealth(2)
                .withPower(2)
                .withCostType(CostType.Blood)
                .withCost(1)
                .withTribe(Tribe.Canine)
                .build();
        Card stinkbug = new Card.Builder()
                .withName("Stinkbug")
                .withHealth(2)
                .withPower(1)
                .withCostType(CostType.Bones)
                .withCost(2)
                .withTribe(Tribe.Insect)
                .withSigil(Sigil.Stinky)
                .build();
        Card bullfrog = new Card.Builder()
                .withName("Bullfrog")
                .withHealth(2)
                .withPower(1)
                .withCostType(CostType.Blood)
                .withCost(1)
                .withTribe(Tribe.Reptile)
                .withSigil(Sigil.MightyLeap)
                .build();

        Collections.addAll(currentCards, stoat, stuntedWolf, stinkbug, bullfrog);
    }

    public void addCard(Card card) {
        currentCards.add(card);
        permanentCards.add(card);
    }

    public void returnCardToCurrent(Card card) {
        currentCards.add(card);
    }

    public void removeCardPermanently(Card card) {
        currentCards.remove(card);
        permanentCards.remove(card);
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

    public Card drawSpecificCard(int index) throws InvalidDeckException {
        if (currentCards.isEmpty()) {
            throw new InvalidDeckException("Cannot draw card from empty deck");
        }
        return currentCards.remove(index);
    }

    public void reset () {
        currentCards.clear();
        currentCards.addAll(permanentCards);
    }
}
