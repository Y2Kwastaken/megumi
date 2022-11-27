package sh.miles.megumi.menu.listener;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import sh.miles.megumi.menu.MenuSession;

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
            ses.getMenu().getButton(e.getSlot()).getAction().execute(player, e);
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
