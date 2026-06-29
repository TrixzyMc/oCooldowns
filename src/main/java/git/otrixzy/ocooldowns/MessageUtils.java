package git.otrixzy.ocooldowns;

public class MessageUtils {

    /**
     * Converts legacy Bukkit color codes (&) and hex codes to MiniMessage format.
     */
    public static String convertLegacyToMiniMessage(String text) {
        if (text == null) return "";

        text = text.replace("&0", "<black>").replace("&1", "<dark_blue>")
                .replace("&2", "<dark_green>").replace("&3", "<dark_aqua>")
                .replace("&4", "<dark_red>").replace("&5", "<dark_purple>")
                .replace("&6", "<gold>").replace("&7", "<gray>")
                .replace("&8", "<dark_gray>").replace("&9", "<blue>")
                .replace("&a", "<green>").replace("&b", "<aqua>")
                .replace("&c", "<red>").replace("&d", "<light_purple>")
                .replace("&e", "<yellow>").replace("&f", "<white>")
                .replace("&k", "<obfuscated>").replace("&l", "<bold>")
                .replace("&m", "<strikethrough>").replace("&n", "<underlined>")
                .replace("&o", "<italic>").replace("&r", "<reset>")
                .replace("&A", "<green>").replace("&B", "<aqua>")
                .replace("&C", "<red>").replace("&D", "<light_purple>")
                .replace("&E", "<yellow>").replace("&F", "<white>")
                .replace("&K", "<obfuscated>").replace("&L", "<bold>")
                .replace("&M", "<strikethrough>").replace("&N", "<underlined>")
                .replace("&O", "<italic>").replace("&R", "<reset>");

        text = text.replaceAll("&#([a-fA-F0-9]{6})", "<#$1>");

        return text;
    }
}