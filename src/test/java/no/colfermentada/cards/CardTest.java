package no.colfermentada.cards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    public void generateShortName_inputLongerThanEightChars_shouldReturnStringOfLengthEight () {
        // Arrange
        String name = "Bloodhound";
        int health = 3;
        int power = 2;
        CostType costType = CostType.Blood;
        int cost = 2;
        Tribe tribe = Tribe.Canine;

        String expected = "Bloodhnd";
        // Act
        Card card = new Card(name, health, power, costType, cost, tribe);
        String actual = card.getShortName();
        // Assert
        assertTrue(expected.equals(actual));
    }

    @Test
    public void generateShortName_inputWithMoreThanEightConsonants_shouldReturnStringOfLengthEight () {
        // Arrange
        String name = "Corpse Maggots";
        int health = 2;
        int power = 1;
        CostType costType = CostType.Bones;
        int cost = 5;
        Tribe tribe = Tribe.Insect;

        String expected = "Crps Mgg";
        // Act
        Card card = new Card(name, health, power, costType, cost, tribe);
        String actual = card.getShortName();
        // Assert
        assertTrue(expected.equals(actual));
    }
}