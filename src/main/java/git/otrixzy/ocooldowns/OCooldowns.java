package git.otrixzy.ocooldowns;

import com.google.j2objc.annotations.Property;
import git.otrixzy.ocooldowns.api.OCooldownsAPI;
import git.otrixzy.ocooldowns.internal.CommandListener;
import git.otrixzy.ocooldowns.internal.CooldownManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bstats.charts.SingleLineChart;
import org.bukkit.plugin.java.JavaPlugin;

public final class OCooldowns extends JavaPlugin {
    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        int pluginId = 32289;
        Metrics metrics = new Metrics(this, pluginId);

        saveDefaultConfig();

        this.cooldownManager = new CooldownManager();

        OCooldownsAPI.init(this);

        getServer().getPluginManager().registerEvents(new CommandListener(this), this);

        if (getCommand("ocooldowns") != null) {
            getCommand("ocooldowns").setExecutor(new OCooldownsCommand(this));
        }

        sendConsole("&8[&coCooldowns&8] &8» &aoCooldowns plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        sendConsole("&8[&coCooldowns&8] &8» &coCooldowns plugin has been disabled.");
    }

    private void sendConsole(String msg) {
        String formatted = MessageUtils.convertLegacyToMiniMessage(msg);
        getServer().getConsoleSender()
                .sendMessage(MiniMessage.miniMessage().deserialize(formatted));
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}