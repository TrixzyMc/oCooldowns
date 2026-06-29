package git.otrixzy.ocooldowns.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerCooldownStartEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final String command;
    private int cooldownTime;

    public PlayerCooldownStartEvent(Player player, String command, int cooldownTime) {
        this.player = player;
        this.command = command;
        this.cooldownTime = cooldownTime;
    }

    public Player getPlayer() {
        return player;
    }

    public String getCommand() {
        return command;
    }

    public int getCooldownTime() {
        return cooldownTime;
    }

    public void setCooldownTime(int cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}