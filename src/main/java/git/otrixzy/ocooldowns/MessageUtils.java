package git.otrixzy.ocooldowns;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtils {

    private static final Map<String, String> COLOR_MAP = Map.ofEntries(
            Map.entry("&0", "<black>"),
            Map.entry("&1", "<dark_blue>"),
            Map.entry("&2", "<dark_green>"),
            Map.entry("&3", "<dark_aqua>"),
            Map.entry("&4", "<dark_red>"),
            Map.entry("&5", "<dark_purple>"),
            Map.entry("&6", "<gold>"),
            Map.entry("&7", "<gray>"),
            Map.entry("&8", "<dark_gray>"),
            Map.entry("&9", "<blue>"),

            Map.entry("&a", "<green>"),
            Map.entry("&b", "<aqua>"),
            Map.entry("&c", "<red>"),
            Map.entry("&d", "<light_purple>"),
            Map.entry("&e", "<yellow>"),
            Map.entry("&f", "<white>"),

            Map.entry("&k", "<obfuscated>"),
            Map.entry("&l", "<bold>"),
            Map.entry("&m", "<strikethrough>"),
            Map.entry("&n", "<underlined>"),
            Map.entry("&o", "<italic>"),
            Map.entry("&r", "<reset>"),

            Map.entry("&A", "<green>"),
            Map.entry("&B", "<aqua>"),
            Map.entry("&C", "<red>"),
            Map.entry("&D", "<light_purple>"),
            Map.entry("&E", "<yellow>"),
            Map.entry("&F", "<white>"),

            Map.entry("&K", "<obfuscated>"),
            Map.entry("&L", "<bold>"),
            Map.entry("&M", "<strikethrough>"),
            Map.entry("&N", "<underlined>"),
            Map.entry("&O", "<italic>"),
            Map.entry("&R", "<reset>")
    );

    private static final Pattern HEX_PATTERN =
            Pattern.compile("&#([a-fA-F0-9]{6})");

    /**
     * Converts legacy Bukkit color codes (&) and hex codes to MiniMessage format.
     */
    public static String convertLegacyToMiniMessage(String text) {
        if (text == null) return "";

        for (Map.Entry<String, String> entry : COLOR_MAP.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue());
        }

        Matcher matcher = HEX_PATTERN.matcher(text);
        StringBuilder buffer = new StringBuilder();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, "<#" + matcher.group(1) + ">");
        }

        matcher.appendTail(buffer);

        return buffer.toString();
    }

}