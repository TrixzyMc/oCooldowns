package git.otrixzy.ocooldowns.api;

import git.otrixzy.ocooldowns.OCooldowns;
import org.bukkit.entity.Player;

public final class OCooldownsAPI {

    private static OCooldowns plugin;

    private OCooldownsAPI() {}

    public static void init(OCooldowns instance) {
        plugin = instance;
    }

    public static boolean isOnCooldown(Player player, String command) {
        return plugin.getCooldownManager().isOnCooldown(player, command);
    }

    public static int getRemaining(Player player, String command) {
        return plugin.getCooldownManager().getRemainingCooldown(player, command);
    }

    public static void setCooldown(Player player, String command, int seconds) {
        plugin.getCooldownManager().setCooldown(plugin, player, command, seconds);
    }

    public static void removeCooldown(Player player, String command) {
        plugin.getCooldownManager().removeCooldown(player, command);
    }

    public static void clear(Player player) {
        plugin.getCooldownManager().clearCooldowns(player);
    }
}