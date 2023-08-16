package no.colfermentada.utils;

import no.colfermentada.cards.Card;

import java.util.ArrayList;
import java.util.function.Function;

public class CardDisplayer {
    private String buildHeaderOrFooterRow(int cardCount) {
        StringBuilder builder = new StringBuilder();
        builder.append("+");
        builder.append("--------+".repeat(Math.max(0, cardCount)));
        return builder.toString();
    }

    private String displayCardAttribute(Card card, Function<Card, String> attributeFunction) {
        return card == null ? "        " : String.format("%1$" + 8 + "s", attributeFunction.apply(card));
    }

    public String buildDisplayString(Card[] cards) {
        StringBuilder builder = new StringBuilder();

        // Header
        builder.append(buildHeaderOrFooterRow(cards.length)).append("\n|");

        // Card Names
        for (Card card : cards) {
            builder.append(displayCardAttribute(card, Card::getShortName)).append("|");
        }
        builder.append("\n|");

        // Card Tribe and Cost
        for (Card card : cards) {
            String tribe = displayCardAttribute(card, Card::getShortTribe).trim();
            String cost = displayCardAttribute(card, Card::getCostString).trim();
            builder.append(String.format("%1$" + 4 + "s" + "%2$" + 4 + "s", tribe, cost)).append("|");
        }
        builder.append("\n|");

        // Card Power and Health
        for (Card card : cards) {
            String power = displayCardAttribute(card, c -> String.valueOf(c.getPower())).trim();
            String health = displayCardAttribute(card, c -> String.valueOf(c.getCurrentHealth())).trim();
            builder.append(String.format("%1$" + 4 + "s" + "%2$" + 4 + "s", power, health)).append("|");
        }

        // Footer
        builder.append("\n").append(buildHeaderOrFooterRow(cards.length)).append("\n");

        return builder.toString();
    }

    public String displayCard(Card card) {
        return buildDisplayString(new Card[]{card});
    }

    public String displayCards(Iterable<Card> cards) {
        // Convert iterable to an array (for simplicity and consistency)
        Card[] cardArray = toArray(cards);
        return buildDisplayString(cardArray);
    }

    private Card[] toArray(Iterable<Card> cards) {
        ArrayList<Card> cardList = new ArrayList<>();
        for (Card card : cards) {
            cardList.add(card);
        }
        return cardList.toArray(new Card[0]);
    }
}
