package sh.miles.megumi.menu;

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
import sh.miles.megumi.menu.item.Button;
import sh.miles.megumi.menu.item.ButtonAction;

public abstract class Menu {

    @Getter
    protected final Inventory inventory;

    protected final Map<Integer, Button> items;

    @Getter
    private final String title;

    @Setter
    @Getter(value = AccessLevel.PROTECTED)
    private Player viewer;

    protected Menu(String title, int size) {
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
