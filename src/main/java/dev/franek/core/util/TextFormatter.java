package dev.franek.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;

/**
 * Formatowanie wiadomosci: kody `&`, kolory HEX (`#RRGGBB`) i laczenie argumentow.
 */
public final class TextFormatter {

    private static final Pattern HEX_COLOR = Pattern.compile("#[a-fA-F0-9]{6}");

    private TextFormatter() {
    }

    /** Wymienia kody `&` i HEX na kody Minecrafta. */
    public static String format(String message) {
        Matcher matcher = HEX_COLOR.matcher(message);
        StringBuilder converted = new StringBuilder();
        while (matcher.find()) {
            StringBuilder hexAsCodes = new StringBuilder("&x");
            for (char digit : matcher.group().substring(1).toCharArray()) {
                hexAsCodes.append('&').append(digit);
            }
            matcher.appendReplacement(converted, Matcher.quoteReplacement(hexAsCodes.toString()));
        }
        matcher.appendTail(converted);
        return ChatColor.translateAlternateColorCodes('&', converted.toString())
                .replace('&', '§');
    }

    /** Laczy argumenty od podanej pozycji w jedna wiadomosc. */
    public static String join(String[] args, int from) {
        StringBuilder text = new StringBuilder();
        for (int i = from; i < args.length; i++) {
            if (text.length() > 0) {
                text.append(' ');
            }
            text.append(args[i]);
        }
        return text.toString();
    }
}
