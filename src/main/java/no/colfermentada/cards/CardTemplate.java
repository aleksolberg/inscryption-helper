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
            System.out.println("Could not create Bloodhound. " +  e.getMessage());
        }
        return null;
    }

    public static Card createMagpie() {
        try {
            return new Card.Builder()
                    .withName("Magpie")
                    .withHealth(1)
                    .withPower(1)
                    .withCostType(CostType.Blood)
                    .withCost(2)
                    .withTribe(Tribe.Avian)
                    .withSigil(Sigil.Airborne)
                    .withSigil(Sigil.Hoarder)
                    .build();
        } catch (InvalidCardException e) {
            System.out.println("Could not create Magpie. " +  e.getMessage());
        }
        return null;
    }

    public static Card createWolfCub() {
        try {
            return new Card.Builder()
                    .withName("Wolf Cub")
                    .withHealth(1)
                    .withPower(1)
                    .withCostType(CostType.Blood)
                    .withCost(1)
                    .withTribe(Tribe.Canine)
                    .withSigil(Sigil.Fledgling)
                    .build();
        } catch (InvalidCardException e) {
            System.out.println("Could not create Wolf Cub. " +  e.getMessage());
        }
        return null;
    }

    public static Card createAlpha() {
        try {
            return new Card.Builder()
                    .withName("Alpha")
                    .withHealth(2)
                    .withPower(1)
                    .withCostType(CostType.Bones)
                    .withCost(4)
                    .withTribe(Tribe.Canine)
                    .withSigil(Sigil.Leader)
                    .build();
        } catch (InvalidCardException e) {
            System.out.println("Could not create Alpha. " +  e.getMessage());
        }
        return null;
    }
}