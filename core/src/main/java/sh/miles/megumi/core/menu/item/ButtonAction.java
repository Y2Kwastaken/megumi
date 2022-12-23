package sh.miles.megumi.core.menu.item;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface ButtonAction {

    ButtonAction EMPTY = (Player player, InventoryClickEvent event) -> {
    };

    ButtonAction CLOSE = (Player player, InventoryClickEvent event) -> {
        player.closeInventory();
    };

    ButtonAction ALLOW_CLICK = (Player player, InventoryClickEvent event) -> {
        event.setCancelled(false);
    };

    void execute(Player player, InventoryClickEvent event);

    static ButtonAction playSound(final Sound sound, final float volume, final float pitch) {
        return (Player player, InventoryClickEvent event) -> {
            player.playSound(player.getLocation(), sound, volume, pitch);
        };
    }

}
