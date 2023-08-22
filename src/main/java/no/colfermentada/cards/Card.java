package no.colfermentada.cards;

import no.colfermentada.utils.CardDisplayer;
import no.colfermentada.utils.CardUtils;

import java.util.EnumSet;
import java.util.Objects;

public class Card {
    private String name;
    private String shortName;
    private int health;
    private int currentHealth;
    private int power;
    private CostType costType;
    private int cost;
    private Tribe tribe;
    private EnumSet<Sigil> sigils;
    private CardDisplayer displayer;

    private Card(Builder builder) {
        this.name = builder.name;
        this.shortName = builder.shortName;
        this.health = builder.health;
        this.currentHealth = builder.health;
        this.power = builder.power;
        this.costType = builder.costType;
        this.cost = builder.cost;
        this.tribe = builder.tribe;
        this.sigils = builder.sigils;
        displayer = new CardDisplayer();
    }

    public Card(Card other) {
        this.name = other.name;
        this.shortName = other.shortName;
        this.health = other.health;
        this.currentHealth = other.currentHealth;
        this.power = other.power;
        this.costType = other.costType;
        this.cost = other.cost;
        this.tribe = other.tribe;
        this.sigils = other.sigils;
        displayer = new CardDisplayer();
    }

    public static class Builder {
        private String name;
        private String shortName;
        private int health;
        private int power;
        private int currentHealth = health;
        private CostType costType;
        private int cost;
        private Tribe tribe;
        private EnumSet<Sigil> sigils = EnumSet.noneOf(Sigil.class);
        ;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withShortName(String shortName) {
            this.shortName = shortName;
            return this;
        }

        public Builder withHealth(int health) {
            this.health = health;
            return this;
        }

        public Builder withPower(int power) {
            this.power = power;
            return this;
        }

        public Builder withCostType(CostType costType) {
            this.costType = costType;
            return this;
        }

        public Builder withCost(int cost) {
            this.cost = cost;
            return this;
        }

        public Builder withTribe(Tribe tribe) {
            this.tribe = tribe;
            return this;
        }

        public Builder withSigil(Sigil sigil) {
            this.sigils.add(sigil);
            return this;
        }

        public Card build() throws InvalidCardException {
            if (name == null) {
                throw new InvalidCardException("Card must have a name");
            }
            if (shortName != null && shortName.length() > 8) {
                throw new InvalidCardException("shortName too long.");
            }
            if (health <= 0) {
                throw new InvalidCardException("Card can not have health lower than one");
            }
            if (power < 0) {
                throw new InvalidCardException("Card can not have negative power");
            }
            if (cost < 0) {
                throw new InvalidCardException("Invalid cost");
            }
            if (costType == null) {
                costType = CostType.None;
            }
            if (tribe == null) {
                tribe = Tribe.None;
            }
            if (shortName == null && name.length() <= 8) {
                shortName = name;
            } else {
                shortName = CardUtils.generateShortName(name);
            }

            return new Card(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return health == card.health && power == card.power && currentHealth == card.currentHealth && cost == card.cost && Objects.equals(name, card.name) && Objects.equals(shortName, card.shortName) && costType == card.costType && tribe == card.tribe && Objects.equals(sigils, card.sigils);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, shortName, health, power, currentHealth, costType, cost, tribe, sigils);
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getPower() {
        return power;
    }

    public CostType getCostType() {
        return costType;
    }

    public int getCost() {
        return cost;
    }

    public Tribe getTribe() {
        return tribe;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void takeDamage(int damage) {
        currentHealth -= damage;
    }

    public String getShortName() {
        return shortName;
    }

    public String getShortTribe() {
        return tribe.name().substring(0, 2);
    }

    public String getCostString() {
        return cost + costType.name().substring(0, 2);
    }

    public EnumSet<Sigil> getSigils() {
        return EnumSet.copyOf(sigils);
    }

    public boolean isDead() {
        return currentHealth <= 0;
    }

    public void resetHealth() {
        currentHealth = health;
    }

    public String displayCard() {
        return displayer.displayCard(this);
    }

    public void upgradeHealth(int amount) {
        health += amount;
    }

    public void upgradePower(int amount) {
        power += amount;
    }
}