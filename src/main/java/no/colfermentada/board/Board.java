package no.colfermentada.board;

import no.colfermentada.cards.Card;
import no.colfermentada.cards.Tribe;
import no.colfermentada.deck.Deck;
import no.colfermentada.cards.CostType;

import java.util.ArrayList;

public class Board {
    protected Card[] played;
    protected Card[] opposing;
    protected Card[] approaching;
    protected ArrayList<Card> hand;
    protected ArrayList<Card> currentDeck;
    protected ArrayList<Card> squirrelDeck;
    protected ArrayList<Card> discardedPile;
    protected int bones;
    protected int score;


    public Board(Deck deck) {
        played = new Card[4];
        opposing = new Card[4];
        approaching = new Card[4];
        hand = new ArrayList<Card>();
        currentDeck = deck.getCards();
        Card squirrel = new Card("Squirrel", "Squirrel", 1, 0, CostType.None, 0, Tribe.Squirrel);
        squirrelDeck = new ArrayList<Card>();
        for (int i = 0; i <= 10; i++) {
            squirrelDeck.add(new Card(squirrel));
        }
        discardedPile = new ArrayList<Card>();
        bones = 0;
        score = 0;
    }

    public Card[] getPlayed() {
        return played;
    }

    public Card[] getOpposing() {
        return opposing;
    }

    public Card[] getApproaching() {
        return approaching;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getBones() {
        return bones;
    }

    public int getScore() {
        return score;
    }

    public void drawFromDeck(int card) {
        if (!currentDeck.isEmpty()) {
            hand.add(currentDeck.remove(card));
        }
    }

    public void drawSquirrel() {
        if (!squirrelDeck.isEmpty()) {
            hand.add(squirrelDeck.remove(0));
        }
    }

    // Play card method for free or bones CostTypes
    // TODO: create errorclass and throw errors
    public void playCard(int cardIndex, int slot) {
        Card card = hand.get(cardIndex);
        CostType costType = card.getCostType();
        int cost = card.getCost();

        if (costType == CostType.Blood) {
            System.out.println("You must sacrifice");
            return;
        }

        if (costType == CostType.Bones && cost > bones) {
            System.out.println("Not enough bones");
            return;
        }

        if (played[slot] != null) {
            System.out.println("Slot is not empty");
            return;
        }


        bones -= cost;
        played[slot] = hand.remove(cardIndex);
    }

    // Play card method for blood CostType
    public void playCard(int cardIndex, int slot, int[] sacrifice) {
        Card card = hand.get(cardIndex);

        if (card.getCostType() == CostType.Blood) {
            int requiredSacrifice = card.getCost();
            if (requiredSacrifice < sacrifice.length) {
                System.out.println("Not enough sacrifice");
                return;
            } else if (requiredSacrifice > sacrifice.length) {
                System.out.println("Too much sacrifice");
                return;
            }
            for (int i : sacrifice) {
                discardedPile.add(played[i]);
                played[i] = null;
            }
        } else {
            System.out.println("Sacrifice not needed");
        }

        if (played[slot] != null) {
            System.out.println("Slot is not empty");
            return;
        }

        played[slot] = hand.remove(cardIndex);
    }

    public void opponentPlaysCard(Card card, int slot) {
        if (approaching[slot] == null) {
            approaching[slot] = card;
        } else {
            System.out.println("Slot is not empty");
        }
    }

    public void playerAttacks() {
        for (int i = 0; i < 4; i++) {
            if (played[i] != null) {
                if (opposing[i] != null) {
                    opposing[i].takeDamage(played[i].getPower());
                    if (opposing[i].getCurrentHealth() <= 0) {
                        opposing[i] = null;
                    }
                } else {
                    score += played[i].getPower();
                }
            }
        }
    }

    public void opponentAttacks() {
        for (int i = 0; i < 4; i++) {
            if (opposing[i] != null) {
                if (played[i] != null) {
                    played[i].takeDamage(opposing[i].getPower());
                    if (played[i].getCurrentHealth() <= 0) {
                        discardedPile.add(played[i]);
                        played[i] = null;
                    }
                } else {
                    score -= opposing[i].getPower();
                }
            }
        }
    }

    public void opponentApproaches() {
        for (int i = 0; i < 4; i++) {
            if (approaching[i] != null && opposing[i] == null) {
                opposing[i] = approaching[i];
                approaching[i] = null;
            }
        }
    }

    public String displayCards(ArrayList<Card> cards) {
        StringBuilder builder = new StringBuilder();
        builder.append("+");
        for (Card card : cards) {
            builder.append("--------+");
        }
        builder.append("\n|");
        for (Card card : cards) {
            builder.append(String.format("%1$" + 8 + "s", card.getShortName())).append("|");
        }
        builder.append("\n|");
        for (Card card : cards) {
            builder.append(String.format("%1$" + 4 + "s" + "%2$" + 4 + "s", card.getShortTribe(), card.getCostString())).append("|");
        }
        // TODO: print sigils when implemented
        builder.append("\n|");
        for (Card card : cards) {
            builder.append(String.format("%1$" + 4 + "s" + "%2$" + 4 + "s", card.getPower(), card.getCurrentHealth())).append("|");
        }
        builder.append("\n+");
        for (Card card : cards) {
            builder.append("--------+");
        }
        builder.append("\n");

        return builder.toString();
    }

    public String displayCards(Card[] cards) {
        StringBuilder builder = new StringBuilder();
        builder.append("+");
        for (Card card : cards) {
            builder.append("--------+");
        }
        builder.append("\n|");
        for (Card card : cards) {
            if (card == null){
                builder.append("        |");
            } else {
                builder.append(String.format("%1$" + 8 + "s", card.getShortName())).append("|");
            }
        }
        builder.append("\n|");
        for (Card card : cards) {
            if (card == null) {
                builder.append("        |");
            } else {
                builder.append(String.format("%1$" + 4 + "s" + "%2$" + 4 + "s", card.getShortTribe(), card.getCostString())).append("|");
            }
        }
        // TODO: print sigils when implemented
        builder.append("\n|");
        for (Card card : cards) {
            if (card == null) {
                builder.append("        |");
            } else {
                builder.append(String.format("%1$" + 4 + "s" + "%2$" + 4 + "s", card.getPower(), card.getCurrentHealth())).append("|");
            }
        }
        builder.append("\n+");
        for (Card card : cards) {
            builder.append("--------+");
        }
        builder.append("\n");

        return builder.toString();
    }

    public String displayBoard() {

        return "Approaching: \n" +
                displayCards(approaching) +
                "Opposing: \n" +
                displayCards(opposing) +
                "Played: \n" +
                displayCards(played) +
                "Hand: \n" +
                displayCards(hand) +
                "Score: " + score;
    }
}
