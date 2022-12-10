package sh.miles.megumi.core.menu.listener;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import sh.miles.megumi.core.menu.MenuSession;
import sh.miles.megumi.core.menu.item.Button;

public class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            return;
        }

        if (!(e.getWhoClicked() instanceof Player player)) {
            return;
        }

        Optional<MenuSession> session = MenuSession.get(player);
        if (session.isEmpty()) {
            return;
        }

        MenuSession ses = session.get();
        if (ses.getMenu().getInventory().equals(e.getClickedInventory())) {
            e.setCancelled(true);
            final Button button = ses.getMenu().getButton(e.getSlot());
            if (button == null) {
                return;
            }
            
            button.getAction().execute(player, e);
        }
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent e) {
        if (e.getInventory() == null) {
            return;
        }

        if (!(e.getPlayer() instanceof Player player)) {
            return;
        }

        Optional<MenuSession> session = MenuSession.get(player);
        if (session.isEmpty()) {
            return;
        }

        MenuSession ses = session.get();
        if (ses.getMenu().getInventory().equals(e.getInventory())) {
            ses.getMenu().onClose();
            MenuSession.end(player);
        }
    }
}
