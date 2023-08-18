package no.colfermentada.cards;

public class CardTemplate {
    public static Card createSquirrel() {
        try {
            return new Card.Builder()
                    .withName("Squirrel")
                    .withHealth(1)
                    .withPower(0)
                    .withTribe(Tribe.Squirrel)
                    .withCost(0)
                    .build();
        } catch (InvalidCardException e) {
            System.out.println("Could not create Squirrel. " +  e.getMessage());
        }
        return null;
    }

    public static Card createStoat() {
        try {
            return new Card.Builder()
                    .withName("Stoat")
                    .withHealth(3)
                    .withPower(1)
                    .withCostType(CostType.Blood)
                    .withCost(1)
                    .withTribe(Tribe.None)
                    .build();
        } catch (InvalidCardException e) {
            System.out.println("Could not create Stoat. " +  e.getMessage());
        }
        return null;
    }

    public static Card createStuntedWolf() {
        try {
            return new Card.Builder()
                    .withName("Stunted Wolf")
                    .withHealth(2)
                    .withPower(2)
                    .withCostType(CostType.Blood)
                    .withCost(1)
                    .withTribe(Tribe.Canine)
                    .build();
        } catch (InvalidCardException e) {
            System.out.println("Could not create Stunted Wolf. " + e.getMessage());
        }
        return null;
    }

    public static Card createStinkbug() {
        try {
            return new Card.Builder()
                    .withName("Stinkbug")
                    .withHealth(2)
                    .withPower(1)
                    .withCostType(CostType.Bones)
                    .withCost(2)
                    .withTribe(Tribe.Insect)
                    .withSigil(Sigil.Stinky)
                    .build();
        } catch (InvalidCardException e) {
            System.out.println("Could not create Stinkbug. " + e.getMessage());
        }
        return null;
    }

    public static Card createBullfrog() {
        try {
            return new Card.Builder()
                    .withName("Bullfrog")
                    .withHealth(2)
                    .withPower(1)
                    .withCostType(CostType.Blood)
                    .withCost(1)
                    .withTribe(Tribe.Reptile)
                    .withSigil(Sigil.MightyLeap)
                    .build();
        } catch (InvalidCardException e) {
            System.out.println("Could not create Bullfrog. " + e.getMessage());
        }
        return null;
    }

    public static Card createBloodhound() {
        try {
            return new Card.Builder()
                    .withName("Bloodhound")
                    .withHealth(3)
                    .withPower(2)
                    .withCostType(CostType.Blood)
                    .withCost(2)
                    .withTribe(Tribe.Canine)
                    .withSigil(Sigil.Guardian)
                    .build();
        } catch (InvalidCardException e) {
            System.out.println("Could not create Stoat. " +  e.getMessage());
        }
        return null;
    }
}
