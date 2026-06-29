package git.otrixzy.ocooldowns.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerCooldownTriggerEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;

    private final Player player;
    private final String command;
    private final int cooldownTime;

    public PlayerCooldownTriggerEvent(Player player, String command, int cooldownTime) {
        this.player = player;
        this.command = command;
        this.cooldownTime = cooldownTime;
    }

    public Player getPlayer() { return player; }
    public String getCommand() { return command; }
    public int getCooldownTime() { return cooldownTime; }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}