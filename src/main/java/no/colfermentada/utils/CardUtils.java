package no.colfermentada.utils;

import no.colfermentada.cards.Card;

import java.util.ArrayList;
import java.util.List;

public final class CardUtils {
    public static String generateShortName(String name) {
        if (name == null || name.length() <= 8) {
            return name;
        }

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

        // If after removing vowels the name is still too long, truncate it.
        if (result.length() > 8) {
            return result.substring(0, 8);
        } else {
            return result.toString();
        }
    }

    public static Card[] copyCardArray(Card[] original) {
        if (original == null) {
            return null;
        }
        Card[] copy = new Card[original.length];
        for (int i = 0; i < original.length; i++) {
            copy[i] = (original[i] != null) ? new Card(original[i]) : null;
        }
        return copy;
    }

    public static List<Card> copyCardList(List<Card> original) {
        List<Card> copy = new ArrayList<>();
        for (Card card : original) {
            copy.add(new Card(card));
        }
        return copy;
    }
}
