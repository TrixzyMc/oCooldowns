package git.otrixzy.ocooldowns;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class OCooldownsCommand implements CommandExecutor {

    private final OCooldowns plugin;

    public OCooldownsCommand(OCooldowns plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        String prefix = plugin.getConfig().getString("messages.prefix", "&8[&c&lCOOLDOWN&8]&r &8»&f");

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {

            if (sender.hasPermission("ocooldowns.admin")) {

                long startTime = System.currentTimeMillis();
                plugin.reloadConfig();
                long timeTaken = System.currentTimeMillis() - startTime;

                String reloadMsg = plugin.getConfig().getString("messages.reloadMessage", "<prefix> &aoCooldowns Reloaded in <time>ms!");

                reloadMsg = reloadMsg.replace("<prefix>", prefix)
                        .replace("<time>", String.valueOf(timeTaken));

                sendMessage(sender, reloadMsg);
                return true;

            } else {

                String noPermMsg = plugin.getConfig().getString("messages.permissionMessage", "<prefix> &cYou do not have permission to use this command.");
                noPermMsg = noPermMsg.replace("<prefix>", prefix);

                sendMessage(sender, noPermMsg);
                return true;
            }
        }

        String infoMsg = "<prefix> &eRunning OCooldowns by oTrixzy. Use &6/" + label + " reload&e to reload.";
        infoMsg = infoMsg.replace("<prefix>", prefix);
        sendMessage(sender, infoMsg);

        return true;
    }

    private void sendMessage(CommandSender sender, String message) {
        String converted = MessageUtils.convertLegacyToMiniMessage(message);
        Component finalMessage = MiniMessage.miniMessage().deserialize(converted);
        sender.sendMessage(finalMessage);
    }
}