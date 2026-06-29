package git.otrixzy.ocooldowns;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private final OCooldowns plugin;
    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

    public CooldownManager(OCooldowns plugin) {
        this.plugin = plugin;
    }

    public void setCooldown(Player player, String command, int seconds) {
        long expiry = System.currentTimeMillis() + (seconds * 1000L);
        cooldowns.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(command.toLowerCase(), expiry);
    }

    public boolean isOnCooldown(Player player, String command) {
        Map<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());
        if (playerCooldowns != null && playerCooldowns.containsKey(command.toLowerCase())) {
            return playerCooldowns.get(command.toLowerCase()) > System.currentTimeMillis();
        }
        return false;
    }

    public int getRemainingCooldown(Player player, String command) {
        Map<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());
        if (playerCooldowns != null && playerCooldowns.containsKey(command.toLowerCase())) {
            long remainingMillis = playerCooldowns.get(command.toLowerCase()) - System.currentTimeMillis();
            return (int) (remainingMillis / 1000);
        }
        return 0;
    }
}