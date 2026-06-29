package git.otrixzy.ocooldowns.internal;

import git.otrixzy.ocooldowns.MessageUtils;
import git.otrixzy.ocooldowns.OCooldowns;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

    public void setCooldown(OCooldowns plugin, Player player, String command, int seconds) {
        long expiry = System.currentTimeMillis() + (seconds * 1000L);
        String cmd = normalize(command);

        cooldowns
                .computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                .put(cmd, expiry);

        if (plugin.getConfig().getBoolean("messages.completion.enabled", true)) {

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (!player.isOnline()) return;

                Map<String, Long> map = cooldowns.get(player.getUniqueId());
                if (map == null) return;

                Long currentExpiry = map.get(cmd);

                if (currentExpiry == null || currentExpiry != expiry) return;

                String prefix = plugin.getConfig().getString("messages.general.prefix", "&8[&c&lCOOLDOWN&8]&r &8»&f");
                String msg = plugin.getConfig().getString("messages.completion.message", "<prefix> You can now use &6<command>&f again.");

                msg = msg.replace("<prefix>", prefix).replace("<command>", command);

                String formatted = MessageUtils.convertLegacyToMiniMessage(msg);
                player.sendMessage(MiniMessage.miniMessage().deserialize(formatted));

                map.remove(cmd);

            }, seconds * 20L);
        }
    }

    public boolean isOnCooldown(Player player, String command) {
        cleanIfExpired(player, command);

        Map<String, Long> map = cooldowns.get(player.getUniqueId());
        if (map == null) return false;

        Long expiry = map.get(normalize(command));
        return expiry != null && expiry > System.currentTimeMillis();
    }

    public int getRemainingCooldown(Player player, String command) {
        Map<String, Long> map = cooldowns.get(player.getUniqueId());
        if (map == null) return 0;

        Long expiry = map.get(normalize(command));
        if (expiry == null) return 0;

        long remaining = expiry - System.currentTimeMillis();
        return remaining > 0 ? (int) (remaining / 1000) : 0;
    }

    public void removeCooldown(Player player, String command) {
        Map<String, Long> map = cooldowns.get(player.getUniqueId());
        if (map != null) {
            map.remove(normalize(command));
        }
    }

    public void clearCooldowns(Player player) {
        cooldowns.remove(player.getUniqueId());
    }

    private void cleanIfExpired(Player player, String command) {
        Map<String, Long> map = cooldowns.get(player.getUniqueId());
        if (map == null) return;

        String cmd = normalize(command);
        Long expiry = map.get(cmd);

        if (expiry != null && expiry <= System.currentTimeMillis()) {
            map.remove(cmd);
        }
    }

    private String normalize(String command) {
        return command.toLowerCase();
    }
}