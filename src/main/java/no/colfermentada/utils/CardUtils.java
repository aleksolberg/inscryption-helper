package no.colfermentada.utils;

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
}
