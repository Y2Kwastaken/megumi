package sh.miles.megumi.core.world.block;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import com.jeff_media.customblockdata.CustomBlockData;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

/**
 * Abstract class for custom blocks.
 * <p>
 * This class handles the registration and persistence / data of custom blocks
 * <p>
 * This class handles placing and breaking of custom blocks and drops the item
 * when broken
 */
public abstract class AbstractCustomBlock {

    // this is a registry method, so we can suppress the fact its not comparable
    @SuppressWarnings("java:S6411")
    private static final Map<Plugin, Map<NamespacedKey, AbstractCustomBlock>> blocks = new HashMap<>();

    @Getter(value = AccessLevel.PROTECTED)
    private final NamespacedKey key;
    private final ItemStack item;
    @Getter(value = AccessLevel.PROTECTED)
    private final Plugin plugin;

    @NonNull
    protected AbstractCustomBlock(final Plugin plugin, final String key, final ItemStack item) {
        this.key = new NamespacedKey(plugin, key);
        this.item = item;

        final ItemMeta meta = this.item.getItemMeta();
        meta.getPersistentDataContainer().set(this.key, PersistentDataType.BYTE, (byte) 1);
        this.item.setItemMeta(meta);

        this.plugin = plugin;
    }

    public ItemStack getItem() {
        return item.clone();
    }
    
    public void onPlace(BlockPlaceEvent event) {

        final ItemStack eventItem = event.getItemInHand();
        if (eventItem == null) {
            return;
        }

        if (!eventItem.isSimilar(getItem())) {
            return;
        }

        final Block block = event.getBlock();
        if (block == null) {
            return;
        }

        CustomBlockData data = new CustomBlockData(block, this.plugin);
        data.set(this.key, PersistentDataType.BYTE, (byte) 1);
    }

    public void onBreak(BlockBreakEvent event) {

        final Block block = event.getBlock();
        if (block == null) {
            return;
        }

        CustomBlockData data = new CustomBlockData(block, this.plugin);
        if (!data.has(this.key, PersistentDataType.BYTE)) {
            return;
        }

        data.remove(key);
        event.setDropItems(false);
        block.getWorld().dropItemNaturally(block.getLocation(), getItem());
    }

    public abstract void onInteract(PlayerInteractEvent event);

    public static final void register(AbstractCustomBlock block) {
        if (!AbstractCustomBlock.blocks.containsKey(block.plugin)) {
            AbstractCustomBlock.blocks.put(block.plugin, new HashMap<>());
        }

        if (AbstractCustomBlock.blocks.get(block.plugin).containsKey(block.key)) {
            throw new IllegalArgumentException("A block with the key " + block.key + " already exists!");
        }

        block.plugin.getLogger().info(() -> "Registering CustomBlock " + block.key);
        AbstractCustomBlock.blocks.get(block.plugin).put(block.key, block);
    }

    public static AbstractCustomBlock getBlock(final Plugin plugin, final String key) {
        return blocks.get(plugin).get(new NamespacedKey(plugin, key));
    }

    public static AbstractCustomBlock getBlock(final Plugin plugin, final NamespacedKey key) {
        return blocks.get(plugin).get(key);
    }

    // this is a registry method, so we can suppress the fact its not comparable
    @SuppressWarnings("java:S6411")
    public static Map<NamespacedKey, AbstractCustomBlock> getBlockMap(final Plugin plugin) {
        return new HashMap<>(blocks.get(plugin));
    }

    public static Set<AbstractCustomBlock> getBlockSet(final Plugin plugin) {
        return Set.copyOf(blocks.get(plugin).values());
    }

}
