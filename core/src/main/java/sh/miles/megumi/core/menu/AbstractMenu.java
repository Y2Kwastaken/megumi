package sh.miles.megumi.core.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import sh.miles.megumi.core.menu.item.Button;
import sh.miles.megumi.core.menu.item.ButtonAction;

/**
 * AbstractMenu is a wrapper class for bukkit's {@link Inventory} class that has
 * numerous advantages over the bukkit class.
 * <p>
 * The first advantage is that it is abstract and can must be extended to be
 * used for delibrate purposes that are clear and concise
 * <p>
 * The second advantage is that every menu has a {@link #getViewer()} method
 * that
 * allows direct access to the player that is viewing the menu this is useful as
 * it allows you to make player specific changes to the menu
 * <p>
 * The third advantage is that it has {@link Button} support which allows event
 * driven items. Instead of hacking together a system to handle events for items
 * in bukkits {@link InventoryClickEvent}
 * you can use the {@link Button} class to handle events for the specific item
 * at hand seperately from the rest of the menu logic. This allows for much
 * clearer button fuctionality as well. Since you aren't concerned with slot
 * logic or item specifities that would trigger the click.
 * <p>
 * The fourth advantage is that it has a {@link MenuSession} system that allows
 * you to keep track of the menu that a player is viewing. This allows you to
 * have multiple menus open at once and to have a menu that is open for a player
 * at all times. This is useful for things like a player's inventory menu or a
 * player's settings menu.
 */
public abstract class AbstractMenu {

    @Getter
    protected final Inventory inventory;

    protected final Map<Integer, Button> items;

    @Getter
    private final String title;

    @Setter
    @Getter(value = AccessLevel.PROTECTED)
    private Player viewer;

    protected AbstractMenu(String title, int size) {
        this.title = title;
        this.inventory = Bukkit.createInventory(null, size, title);
        this.items = new HashMap<>();
    }

    protected void setItem(final int slot, @NonNull final Button button) {
        this.items.put(slot, button);
        this.inventory.setItem(slot, button.getItem());
    }

    protected void setItem(final int slot, @NonNull final ItemStack item) {
        setItem(slot, new Button(item, ButtonAction.EMPTY));
    }

    protected void setItem(final int slot, @NonNull final ItemStack item, @NonNull final ButtonAction action) {
        setItem(slot, new Button(item, action));
    }

    protected void fill(@NonNull final Button button, IntStream slots) {
        slots.forEach(slot -> setItem(slot, button));
    }

    protected void fill(@NonNull final Button button, int... slots) {
        fill(button, IntStream.of(slots));
    }

    protected void fill(@NonNull final ItemStack item, IntStream slots) {
        slots.forEach(slot -> setItem(slot, item));
    }

    protected void fill(@NonNull final ItemStack item, int... slots) {
        fill(item, IntStream.of(slots));
    }

    protected void removeItem(final int slot) {
        this.items.computeIfPresent(slot, (k, v) -> this.items.remove(k));
    }

    public Button getButton(final int slot) {
        return this.items.get(slot);
    }

    public abstract void init();

    public void onStart() {
    }

    public void onClose() {
    }
}
