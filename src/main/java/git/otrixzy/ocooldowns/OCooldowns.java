package git.otrixzy.ocooldowns;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;

public final class OCooldowns extends JavaPlugin {

    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.cooldownManager = new CooldownManager(this);

        getServer().getPluginManager().registerEvents(new CommandListener(this), this);

        if (getCommand("ocooldowns") != null) {
            getCommand("ocooldowns").setExecutor(new OCooldownsCommand(this));
        }

        String enableMsg = MessageUtils.convertLegacyToMiniMessage("&8[&coCooldowns&8] &8» &aoCooldowns plugin has been loaded.");
        getServer().getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize(enableMsg));
    }

    @Override
    public void onDisable() {
        String disableMsg = MessageUtils.convertLegacyToMiniMessage("&8[&coCooldowns&8] &8» &coCooldowns plugin has been disabled.");
        getServer().getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize(disableMsg));
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}