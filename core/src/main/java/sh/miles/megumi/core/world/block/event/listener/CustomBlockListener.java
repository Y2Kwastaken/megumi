package sh.miles.megumi.core.world.block.event.listener;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;

import com.jeff_media.customblockdata.CustomBlockData;

import sh.miles.megumi.core.world.block.CustomBlock;
import sh.miles.megumi.core.world.block.event.CustomBlockBreakEvent;
import sh.miles.megumi.core.world.block.event.CustomBlockInteractEvent;
import sh.miles.megumi.core.world.block.event.CustomBlockPlaceEvent;

public class CustomBlockListener implements Listener {

    private final Plugin plugin;

    public CustomBlockListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        final ItemStack item = event.getItemInHand();
        if (item == null) {
            return;
        }

        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        container.getKeys().forEach(key -> {
            CustomBlock block = CustomBlock.getBlock(plugin, key);
            if (block != null) {
                final CustomBlockPlaceEvent customEvent = new CustomBlockPlaceEvent(block,
                        event.getBlock().getLocation(), event.getPlayer(), event);
                Bukkit.getPluginManager().callEvent(customEvent);
                if (customEvent.isCancelled()) {
                    event.setCancelled(true);
                    return;
                }
                block.onPlace(event);
            }
        });
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Block block = event.getBlock();
        if (block == null) {
            return;
        }

        if (block.getType().isAir()) {
            return;
        }

        CustomBlockData data = new CustomBlockData(block, this.plugin);
        data.getKeys().forEach(key -> {
            CustomBlock customBlock = CustomBlock.getBlock(plugin, key);
            if (customBlock != null) {
                final CustomBlockBreakEvent customEvent = new CustomBlockBreakEvent(customBlock, block.getLocation(),
                        event.getPlayer(), event);
                Bukkit.getPluginManager().callEvent(customEvent);
                if (customEvent.isCancelled()) {
                    event.setCancelled(true);
                    return;
                }
                customBlock.onBreak(event);
            }
        });
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {

        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }

        if (block.getType().isAir()) {
            return;
        }

        CustomBlockData data = new CustomBlockData(block, this.plugin);
        data.getKeys().forEach(key -> {
            CustomBlock customBlock = CustomBlock.getBlock(plugin, key);
            if (customBlock != null) {
                final CustomBlockInteractEvent customEvent = new CustomBlockInteractEvent(customBlock,
                        event.getClickedBlock().getLocation(), event.getPlayer(), event);
                Bukkit.getPluginManager().callEvent(customEvent);
                if (customEvent.isCancelled()) {
                    event.setCancelled(true);
                    return;
                }
                customBlock.onInteract(event);
            }
        });

    }

}
