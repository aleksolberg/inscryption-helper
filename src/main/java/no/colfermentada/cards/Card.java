package no.colfermentada.cards;

import no.colfermentada.cards.Tribe;
import no.colfermentada.cards.CostType;

public class Card {
    protected String name;
    protected String shortName;
    protected int health;
    protected int power;
    protected int currentHealth;
    protected CostType costType;
    protected int cost;
    protected Tribe tribe;
    // TODO: implement sigils

    public Card(String name, String shortName, int health, int power, CostType costType, int cost, Tribe tribe) {
        this.name = name;
        this.shortName = shortName; // TODO: throw error if shortName to long
        this.health = health;
        currentHealth = health;
        this.power = power;
        this.costType = costType;
        this.cost = cost;
        this.tribe = tribe;

    }

    public Card(Card card) {
        name = card.getName();
        shortName = card.getShortName();
        health = card.getHealth();
        currentHealth = health;
        power = card.getPower();
        costType = card.getCostType();
        cost = card.getCost();
        tribe = card.getTribe();
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

    public void resetCard() {
        currentHealth = health;
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
}
