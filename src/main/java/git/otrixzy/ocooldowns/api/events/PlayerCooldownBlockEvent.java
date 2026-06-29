package git.otrixzy.ocooldowns.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerCooldownBlockEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final String command;
    private final int timeRemaining;

    public PlayerCooldownBlockEvent(Player player, String command, int timeRemaining) {
        this.player = player;
        this.command = command;
        this.timeRemaining = timeRemaining;
    }

    public Player getPlayer() {
        return player;
    }

    public String getCommand() {
        return command;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}