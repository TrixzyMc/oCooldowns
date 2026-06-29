package git.otrixzy.ocooldowns.internal;

import git.otrixzy.ocooldowns.GeneralUtils;
import git.otrixzy.ocooldowns.OCooldowns;
import git.otrixzy.ocooldowns.SoundUtils;
import git.otrixzy.ocooldowns.api.events.PlayerCooldownBlockEvent;
import git.otrixzy.ocooldowns.api.events.PlayerCooldownStartEvent;
import git.otrixzy.ocooldowns.api.events.PlayerCooldownTriggerEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    private final OCooldowns plugin;

    public CommandListener(OCooldowns plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        String message = event.getMessage().substring(1).toLowerCase();
        String command = message.split(" ")[0];

        int cooldownTime = getCooldown(command);
        if (cooldownTime <= 0) return;

        PlayerCooldownTriggerEvent triggerEvent =
                new PlayerCooldownTriggerEvent(player, command, cooldownTime);
        Bukkit.getPluginManager().callEvent(triggerEvent);

        if (triggerEvent.isCancelled()) return;

        if (hasBypass(player, command)) return;

        if (plugin.getCooldownManager().isOnCooldown(player, command)) {

            int timeRemaining = plugin.getCooldownManager().getRemainingCooldown(player, command);

            PlayerCooldownBlockEvent blockEvent =
                    new PlayerCooldownBlockEvent(player, command, timeRemaining);
            Bukkit.getPluginManager().callEvent(blockEvent);

            event.setCancelled(true);

            SoundUtils.playSound(plugin, player, "cooldown");
            GeneralUtils.sendCooldownMessage(plugin, player, command, timeRemaining);
            return;
        }

        PlayerCooldownStartEvent startEvent =
                new PlayerCooldownStartEvent(player, command, cooldownTime);
        Bukkit.getPluginManager().callEvent(startEvent);

        if (startEvent.getCooldownTime() <= 0) return;

        plugin.getCooldownManager()
                .setCooldown(plugin, player, command, startEvent.getCooldownTime());
    }

    private int getCooldown(String command) {
        if (plugin.getConfig().contains("cooldowns." + command)) {
            return plugin.getConfig().getInt("cooldowns." + command);
        }
        return plugin.getConfig().getInt("cooldowns.*", -1);
    }

    private boolean hasBypass(Player player, String command) {
        boolean disableBypass = plugin.getConfig().getBoolean("extra.disable_bypass_permissions", false);

        if (disableBypass) return false;

        return player.hasPermission("ocooldowns.bypass." + command)
                || player.hasPermission("ocooldowns.bypass.*");
    }
}