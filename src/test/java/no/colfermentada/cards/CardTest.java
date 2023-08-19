package no.colfermentada.cards;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    @Test
    public void createCard_allAttributes_shouldReturnCorrectName() {
        // Arrange
        String name = "Bloodhound";
        String shortName = "Bloodhnd";
        int health = 3;
        int power = 2;
        CostType costType = CostType.Blood;
        int cost = 2;
        Tribe tribe = Tribe.Canine;
        Sigil sigil = Sigil.Guardian;

        String expected = new String("Bloodhound");
        // Act
        Card card = CardTemplate.createBloodhound();
        String actual = card.getName();
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void createCard_moreThanOneSigil_shouldReturnCorrectSigils() {
        // Arrange
        String name = "Mole Man";
        int health = 6;
        int power = 0;
        CostType costType = CostType.Blood;
        int cost = 1;
        Tribe tribe = Tribe.None;
        Sigil sigil1 = Sigil.MightyLeap;
        Sigil sigil2 = Sigil.Guardian;

        EnumSet<Sigil> expected = EnumSet.of(Sigil.MightyLeap, Sigil.Guardian);
        // Act
        Card card = null;
        try {
            card = new Card.Builder()
                    .withName(name)
                    .withHealth(health)
                    .withPower(power)
                    .withCostType(costType)
                    .withCost(cost)
                    .withTribe(tribe)
                    .withSigil(sigil1)
                    .withSigil(sigil2)
                    .build();
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }
        EnumSet<Sigil> actual = card.getSigils();
        // Assert
        assertEquals(expected, actual);
    }

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
        Card card = null;
        try {
            card = new Card.Builder()
                    .withName(name)
                    .withHealth(health)
                    .withPower(power)
                    .withCostType(costType)
                    .withCost(cost)
                    .withTribe(tribe)
                    .build();
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }
        String actual = card.getShortName();
        // Assert
        assertEquals(expected, actual);
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
        Card card = null;
        try {
            card = new Card.Builder()
                    .withName(name)
                    .withHealth(health)
                    .withPower(power)
                    .withCostType(costType)
                    .withCost(cost)
                    .withTribe(tribe)
                    .build();
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }
        String actual = card.getShortName();
        // Assert
        assertTrue(expected.equals(actual));
    }

    @Test
    public void cloneCard_validCard_shouldReturnSameCard() {
        // Arrange
        String name = "Bloodhound";
        int health = 3;
        int power = 2;
        CostType costType = CostType.Blood;
        int cost = 2;
        Tribe tribe = Tribe.Canine;
        Sigil sigil = Sigil.Guardian;
        Card card = null;
        try {
            card = new Card.Builder()
                    .withName(name)
                    .withHealth(health)
                    .withPower(power)
                    .withCostType(costType)
                    .withCost(cost)
                    .withTribe(tribe)
                    .withSigil(sigil)
                    .build();
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }

        Card expected = null;
        try {
            expected = new Card.Builder()
                    .withName(name)
                    .withHealth(health)
                    .withPower(power)
                    .withCostType(costType)
                    .withCost(cost)
                    .withTribe(tribe)
                    .withSigil(sigil)
                    .build();
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }
        // Act
        Card actual = new Card(card);
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void displayCard_validCard_shouldReturnCorrectString() {
        // Arrange
        String name = "Bloodhound";
        int health = 3;
        int power = 2;
        CostType costType = CostType.Blood;
        int cost = 2;
        Tribe tribe = Tribe.Canine;
        Sigil sigil = Sigil.Guardian;
        Card card = null;
        try {
            card = new Card.Builder()
                    .withName(name)
                    .withHealth(health)
                    .withPower(power)
                    .withCostType(costType)
                    .withCost(cost)
                    .withTribe(tribe)
                    .withSigil(sigil)
                    .build();
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }
        String expected = "+--------+\n" +
                "|Bloodhnd|\n" +
                "|  Ca 2Bl|\n" +
                "|   2   3|\n" +
                "+--------+\n";
        // Act
        String actual = card.displayCard();
        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void resetCard_validCard_shouldReturnCorrectCard() {
        // Arrange
        String name = "Bloodhound";
        int health = 3;
        int power = 2;
        CostType costType = CostType.Blood;
        int cost = 2;
        Tribe tribe = Tribe.Canine;
        Sigil sigil = Sigil.Guardian;
        Card expected = null;
        try {
            expected = new Card.Builder()
                    .withName(name)
                    .withHealth(health)
                    .withPower(power)
                    .withCostType(costType)
                    .withCost(cost)
                    .withTribe(tribe)
                    .withSigil(sigil)
                    .build();
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }
        // Act
        Card actual = null;
        try {
            actual = new Card.Builder()
                    .withName(name)
                    .withHealth(health)
                    .withPower(power)
                    .withCostType(costType)
                    .withCost(cost)
                    .withTribe(tribe)
                    .withSigil(sigil)
                    .build();
        } catch (InvalidCardException e) {
            throw new RuntimeException(e);
        }
        actual.takeDamage(2);
        actual.resetHealth();
        // Assert
        assertEquals(expected, actual);
    }
}