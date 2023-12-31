package no.colfermentada.deck;

import no.colfermentada.cards.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    @Test
    public void populateStandardDeck_valid_shouldReturnDeckWithCorrectCards() {
        // Arrange
        Deck deck = new Deck();
        Card stoat = null;
        Card stuntedWolf = null;
        Card stinkbug = null;
        Card bullfrog = null;
        try {
            stoat = new Card.Builder()
                    .withName("Stoat")
                    .withHealth(3)
                    .withPower(1)
                    .withCostType(CostType.Blood)
                    .withCost(1)
                    .withTribe(Tribe.None)
                    .build();

            stuntedWolf = new Card.Builder()
                    .withName("Stunted Wolf")
                    .withHealth(2)
                    .withPower(2)
                    .withCostType(CostType.Blood)
                    .withCost(1)
                    .withTribe(Tribe.Canine)
                    .build();

            stinkbug = new Card.Builder()
                    .withName("Stinkbug")
                    .withHealth(2)
                    .withPower(1)
                    .withCostType(CostType.Bones)
                    .withCost(2)
                    .withTribe(Tribe.Insect)
                    .withSigil(Sigil.Stinky)
                    .build();

            bullfrog = new Card.Builder()
                    .withName("Bullfrog")
                    .withHealth(2)
                    .withPower(1)
                    .withCostType(CostType.Blood)
                    .withCost(1)
                    .withTribe(Tribe.Reptile)
                    .withSigil(Sigil.MightyLeap)
                    .build();
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }
        Card finalStoat = stoat;
        Card finalStuntedWolf = stuntedWolf;
        Card finalStinkbug = stinkbug;
        Card finalBullfrog = bullfrog;
        ArrayList<Card> expected = new ArrayList<Card>(){{
            add(finalStoat);
            add(finalStuntedWolf);
            add(finalStinkbug);
            add(finalBullfrog);
        }};
        // Act
        deck.populateStandardDeck();
        ArrayList<Card> actual = deck.getCurrentCards();
        // Assert
        assertEquals(expected, actual);
    }
}