package git.otrixzy.ocooldowns;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class GeneralUtils {

    private static final MiniMessage mm = MiniMessage.miniMessage();

    public static void sendCooldownMessage(OCooldowns plugin, Player player, String command, int time) {

        String prefix = plugin.getConfig().getString("messages.general.prefix", "");

        ConfigurationSection cooldownSec = plugin.getConfig().getConfigurationSection("messages.cooldown");

        if (cooldownSec == null) return;

        String chat = cooldownSec.getString("chat");
        String actionbar = cooldownSec.getString("actionbar");

        chat = format(prefix, chat, command, time);
        actionbar = format(prefix, actionbar, command, time);

        if (plugin.getConfig().getBoolean("notifications.chat", true)) {
            send(player, chat);
        }

        if (plugin.getConfig().getBoolean("notifications.actionbar", true)) {
            sendActionBar(player, actionbar);
        }

        if (plugin.getConfig().getBoolean("notifications.title", false)) {
            ConfigurationSection titleSec = cooldownSec.getConfigurationSection("title");
            if (titleSec != null) {
                String title = format(prefix, titleSec.getString("title"), command, time);
                String subtitle = format(prefix, titleSec.getString("subtitle"), command, time);

                sendTitle(player, title, subtitle);
            }
        }

        playSound(plugin, player, "sounds.cooldown");
    }

    private static String format(String prefix, String msg, String command, int time) {
        if (msg == null) return "";

        return msg.replace("<prefix>", prefix)
                .replace("<time>", String.valueOf(time))
                .replace("<command>", command);
    }

    public static void send(Player player, String msg) {
        if (msg == null) return;
        Component comp = mm.deserialize(MessageUtils.convertLegacyToMiniMessage(msg));
        player.sendMessage(comp);
    }

    public static void sendActionBar(Player player, String msg) {
        if (msg == null) return;
        Component comp = mm.deserialize(MessageUtils.convertLegacyToMiniMessage(msg));
        player.sendActionBar(comp);
    }
    public static void sendTitle(Player player, String title, String subtitle) {
        Component t = mm.deserialize(MessageUtils.convertLegacyToMiniMessage(title));
        Component s = mm.deserialize(MessageUtils.convertLegacyToMiniMessage(subtitle));

        player.showTitle(net.kyori.adventure.title.Title.title(t, s));
    }

    public static void playSound(OCooldowns plugin, Player player, String path) {

        if (!plugin.getConfig().getBoolean(path + ".disabled", false)) {
            String soundName = plugin.getConfig().getString(path + ".sound");
            float volume = (float) plugin.getConfig().getDouble(path + ".volume", 1);
            float pitch = (float) plugin.getConfig().getDouble(path + ".pitch", 1);

            if (soundName == null) return;

            try {
                Sound sound = Registry.SOUNDS.get(NamespacedKey.fromString(soundName));

                if (sound != null) {
                    player.playSound(player.getLocation(), sound, volume, pitch);
                } else {
                    plugin.getLogger().warning("[oCooldowns] Invalid sound: " + soundName);
                }
            } catch (IllegalArgumentException ignored) {
                plugin.getLogger().warning("[oCooldowns] Invalid sound: " + soundName);
            }
        }
    }
}