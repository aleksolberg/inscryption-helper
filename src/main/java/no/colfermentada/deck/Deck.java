package no.colfermentada.deck;

import no.colfermentada.cards.Card;
import no.colfermentada.cards.CostType;
import no.colfermentada.cards.Tribe;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    protected ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();
    }
    public Deck(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void populateStandardDeck() {
        Card stoat = new Card("Stoat", "Stoat", 3, 1, CostType.Blood, 1, Tribe.None);
        Card stuntedWolf = new Card("Stunted Wolf", "Stnd Wlf", 2, 2, CostType.Blood, 1, Tribe.Canine);
        Card stinkBug = new Card("Stinkbug", "Stinkbug", 2, 1, CostType.Bones, 2, Tribe.Insect);
        Card bullfrog = new Card("Bullfrog", "Bullfrog", 2, 1, CostType.Blood, 1, Tribe.Reptile);

        Collections.addAll(cards, stoat, stuntedWolf, stinkBug, bullfrog);
    }
}
