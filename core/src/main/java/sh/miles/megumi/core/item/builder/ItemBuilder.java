package sh.miles.megumi.core.item.builder;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import lombok.Builder;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class ItemBuilder {

    protected ItemStack item;
    protected Material material;
    @Builder.Default
    protected int amount = 1;
    @Builder.Default
    protected int damage = -1;
    protected int modelData;
    protected String name;
    @Singular
    protected List<String> lores;
    @Singular
    protected Set<ItemFlag> flags;
    @Singular
    protected Map<Enchantment, Integer> enchantments;
    protected boolean glow;
    protected boolean unbreakable;
    @Builder.Default
    protected boolean hideTags = false;

    // only exceeding congnotive complexity by 1 which is reasonable in this case
    @SuppressWarnings("java:S1541")
    protected ItemMeta getItemMeta(final ItemStack metable) {
        final ItemMeta meta = metable.getItemMeta();
        meta.setCustomModelData(modelData);
        if (glow) {
            meta.addEnchant(Enchantment.IMPALING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (enchantments != null) {
            if (material == Material.ENCHANTED_BOOK) {
                this.enchantments.forEach((e, l) -> ((EnchantmentStorageMeta) meta).addStoredEnchant(e, l, true));
            } else {
                this.enchantments.forEach((e, l) -> meta.addEnchant(e, l, true));
            }
        }

        if (name != null) {
            meta.setDisplayName(name);
        }

        if (lores != null && !lores.isEmpty()) {
            meta.setLore(lores);
        }

        if (flags != null && !flags.isEmpty()) {
            meta.addItemFlags(flags.toArray(new ItemFlag[flags.size()]));
        }

        if (unbreakable) {
            meta.setUnbreakable(true);
        }

        if (hideTags) {
            meta.addItemFlags(ItemFlag.values());
        }

        return meta;
    }

    // or statements can not be merged
    @SuppressWarnings("java:S1066")
    public ItemStack make() {

        ItemStack make = item != null ? item : new ItemStack(material, amount);
        final ItemMeta meta = getItemMeta(make);

        if (damage != -1) {
            // ^ see above
            if (meta instanceof Damageable damageable) {
                damageable.setDamage(damage);
            }
        }

        make.setItemMeta(meta);
        return make;
    }

}
