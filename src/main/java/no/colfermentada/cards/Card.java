package no.colfermentada.cards;

import java.util.ArrayList;

public class Card {
    protected String name;
    protected String shortName;
    protected int health;
    protected int power;
    protected int currentHealth;
    protected CostType costType;
    protected int cost;
    protected Tribe tribe;
    protected ArrayList<Sigil> sigils;

    public Card(String name, String shortName, int health, int power, CostType costType, int cost, Tribe tribe, ArrayList<Sigil> sigils)
            throws InvalidCardException {
        if (shortName.length() > 8) {
            throw new InvalidCardException("shortName too long. Use constructor without shotName to generate automatically");
        }
        this.name = name;
        this.shortName = shortName;
        this.health = health;
        currentHealth = health;
        this.power = power;
        this.costType = costType;
        this.cost = cost;
        this.tribe = tribe;
        this.sigils = sigils;
    }

    public Card(String name, String shortName, int health, int power, CostType costType, int cost, Tribe tribe, Sigil sigil)
            throws InvalidCardException {
        if (shortName.length() > 8) {
            throw new InvalidCardException("shortName too long. Use constructor without shotName to generate automatically");
        }
        this.name = name;
        this.shortName = shortName; // TODO: throw error if shortName to long
        this.health = health;
        currentHealth = health;
        this.power = power;
        this.costType = costType;
        this.cost = cost;
        this.tribe = tribe;
        this.sigils = new ArrayList<Sigil>() {{
            add(sigil);
        }};
    }

    public Card(String name, String shortName, int health, int power, CostType costType, int cost, Tribe tribe)
            throws InvalidCardException {
        if (shortName.length() > 8) {
            throw new InvalidCardException("shortName too long. Use constructor without shotName to generate automatically");
        }
        this.name = name;
        this.shortName = shortName;
        this.health = health;
        currentHealth = health;
        this.power = power;
        this.costType = costType;
        this.cost = cost;
        this.tribe = tribe;
        this.sigils = new ArrayList<Sigil>();
    }

    public Card(String name, int health, int power, CostType costType, int cost, Tribe tribe) {
        this.name = name;
        this.shortName = generateShortName();
        this.health = health;
        currentHealth = health;
        this.power = power;
        this.costType = costType;
        this.cost = cost;
        this.tribe = tribe;
        this.sigils = new ArrayList<Sigil>();
    }

    public Card(String name, int health, int power, CostType costType, int cost, Tribe tribe, Sigil sigil) {
        this.name = name;
        this.shortName = generateShortName();
        this.health = health;
        currentHealth = health;
        this.power = power;
        this.costType = costType;
        this.cost = cost;
        this.tribe = tribe;
        this.sigils = new ArrayList<Sigil>() {{
            add(sigil);
        }};
    }

    public Card(String name, int health, int power, CostType costType, int cost, Tribe tribe, ArrayList<Sigil> sigils) {
        this.name = name;
        this.shortName = generateShortName();
        this.health = health;
        currentHealth = health;
        this.power = power;
        this.costType = costType;
        this.cost = cost;
        this.tribe = tribe;
        this.sigils = sigils;
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
        sigils = card.getSigils();
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

    public String generateShortName() {
        String vowels = "aeiou";
        StringBuilder result = new StringBuilder(name);

        for (int i = name.length() - 1; i >= 0; i--) {
            if (vowels.indexOf(name.charAt(i)) != -1) {
                result.deleteCharAt(i);
                if (result.length() <= 8) {
                    break;
                }
            }
        }
        if (result.length() > 8) {
            return result.substring(0, 8);
        }
        return result.toString();
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

    public ArrayList<Sigil> getSigils() {
        return sigils;
    }

    public boolean isDead() {
        return currentHealth >= 0;
    }
}
