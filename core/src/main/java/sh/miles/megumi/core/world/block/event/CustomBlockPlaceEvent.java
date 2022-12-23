package sh.miles.megumi.core.world.block.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockPlaceEvent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sh.miles.megumi.core.world.block.AbstractCustomBlock;

/**
 * CustomBlockPlaceEvent is fired when a custom block is placed in the world.
 */
@Getter
@RequiredArgsConstructor
public class CustomBlockPlaceEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled;

    private final AbstractCustomBlock block;
    private final Location location;
    private final Player player;
    private final BlockPlaceEvent event;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    // suppress warning because we know what we are doing here
    @SuppressWarnings("java:S4144")
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
