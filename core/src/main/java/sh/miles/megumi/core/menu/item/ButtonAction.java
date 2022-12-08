package sh.miles.megumi.core.menu.item;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface ButtonAction {

    void execute(Player player, InventoryClickEvent event);

    public static final ButtonAction EMPTY = (player, event) -> {
    };
    public static final ButtonAction CLOSE = (player, event) -> {
        player.closeInventory();
    };
    public static final ButtonAction ALLOW_CLICK = (player, event) -> {
        event.setCancelled(false);
    };

    public static ButtonAction PLAY_SOUND(final Sound sound, final float volume, final float pitch) {
        return (player, event) -> {
            player.playSound(player.getLocation(), sound, volume, pitch);
        };
    }

    

}
