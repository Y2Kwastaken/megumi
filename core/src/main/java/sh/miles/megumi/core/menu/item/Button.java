package sh.miles.megumi.core.menu.item;

import org.bukkit.inventory.ItemStack;

import lombok.Data;

/**
 * Button is a wrapper class for {@link ItemStack} that allows for event driven
 * items. this is utilized by the {@link AbstractMenu} class to allow for events
 * to easily be called for specific items
 */
@Data
public class Button {

    private final ItemStack item;
    private final ButtonAction action;

}
