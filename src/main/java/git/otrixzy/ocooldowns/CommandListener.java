package git.otrixzy.ocooldowns;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    private final OCooldowns plugin;

    public CommandListener(OCooldowns plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        String message = event.getMessage().substring(1).toLowerCase();
        String command = message.split(" ")[0];

        int cooldownTime = -1;

        if (plugin.getConfig().contains("cooldowns." + command)) {
            cooldownTime = plugin.getConfig().getInt("cooldowns." + command);
        } else if (plugin.getConfig().contains("cooldowns.*")) {
            cooldownTime = plugin.getConfig().getInt("cooldowns.*");
        }

        if (cooldownTime > 0) {

            boolean useBypass = plugin.getConfig().getBoolean("extra.disable_bypass_permissions", false);
            if (!useBypass) {
                if (player.hasPermission("ocooldowns.bypass." + command) || player.hasPermission("ocooldowns.bypass.*")) {
                    return;
                }
            }

            if (plugin.getCooldownManager().isOnCooldown(player, command)) {
                event.setCancelled(true);
                int timeRemaining = plugin.getCooldownManager().getRemainingCooldown(player, command);

                String prefix = plugin.getConfig().getString("messages.prefix", "");
                String cooldownMsg = plugin.getConfig().getString("messages.cooldown", "<prefix> &cYou must wait <time> seconds.");

                cooldownMsg = cooldownMsg.replace("<prefix>", prefix)
                        .replace("<time>", String.valueOf(timeRemaining))
                        .replace("<command>", command);

                cooldownMsg = MessageUtils.convertLegacyToMiniMessage(cooldownMsg);

                Component finalMessage = MiniMessage.miniMessage().deserialize(cooldownMsg);

                player.sendMessage(finalMessage);
                return;
            }

            plugin.getCooldownManager().setCooldown(player, command, cooldownTime);
        }
    }
}