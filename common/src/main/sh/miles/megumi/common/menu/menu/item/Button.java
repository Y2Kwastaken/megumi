package sh.miles.megumi.menu.item;

import org.bukkit.inventory.ItemStack;

import lombok.Data;

@Data
public class Button {
    
    private final ItemStack item;
    private final ButtonAction action;

}
