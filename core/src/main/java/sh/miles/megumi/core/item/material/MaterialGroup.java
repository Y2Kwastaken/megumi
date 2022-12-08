package sh.miles.megumi.core.item.material;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;

public class MaterialGroup {

    public static final MaterialGroup PICKAXES = MaterialGroup.of(
            Material.NETHERITE_PICKAXE,
            Material.DIAMOND_PICKAXE,
            Material.GOLDEN_PICKAXE,
            Material.IRON_PICKAXE,
            Material.STONE_PICKAXE,
            Material.WOODEN_PICKAXE);
    public static final MaterialGroup AXES = MaterialGroup.of(
            Material.NETHERITE_AXE,
            Material.DIAMOND_AXE,
            Material.GOLDEN_AXE,
            Material.IRON_AXE,
            Material.STONE_AXE,
            Material.WOODEN_AXE);
    public static final MaterialGroup SHOVELS = MaterialGroup.of(
            Material.NETHERITE_SHOVEL,
            Material.DIAMOND_SHOVEL,
            Material.GOLDEN_SHOVEL,
            Material.IRON_SHOVEL,
            Material.STONE_SHOVEL,
            Material.WOODEN_SHOVEL);
    public static final MaterialGroup HOES = MaterialGroup.of(
            Material.NETHERITE_HOE,
            Material.DIAMOND_HOE,
            Material.GOLDEN_HOE,
            Material.STONE_SHOVEL,
            Material.WOODEN_SHOVEL);
    public static final MaterialGroup SWORDS = MaterialGroup.of(
            Material.NETHERITE_SWORD,
            Material.DIAMOND_SWORD,
            Material.GOLDEN_SWORD,
            Material.IRON_SWORD,
            Material.STONE_SWORD,
            Material.WOODEN_SWORD);
    public static final MaterialGroup BOW = MaterialGroup.of(Material.BOW);
    public static final MaterialGroup HELMETS = MaterialGroup.of(
            Material.NETHERITE_HELMET,
            Material.DIAMOND_HELMET,
            Material.GOLDEN_HELMET,
            Material.IRON_HELMET,
            Material.LEATHER_HELMET);
    // material group for chestplates
    public static final MaterialGroup CHESTPLATES = MaterialGroup.of(
            Material.NETHERITE_CHESTPLATE,
            Material.DIAMOND_CHESTPLATE,
            Material.GOLDEN_CHESTPLATE,
            Material.IRON_CHESTPLATE,
            Material.LEATHER_CHESTPLATE);
    // material group for leggings
    public static final MaterialGroup LEGGINGS = MaterialGroup.of(
            Material.NETHERITE_LEGGINGS,
            Material.DIAMOND_LEGGINGS,
            Material.GOLDEN_LEGGINGS,
            Material.IRON_LEGGINGS,
            Material.LEATHER_LEGGINGS);
    // material group for boots
    public static final MaterialGroup BOOTS = MaterialGroup.of(
            Material.NETHERITE_BOOTS,
            Material.DIAMOND_BOOTS,
            Material.GOLDEN_BOOTS,
            Material.IRON_BOOTS,
            Material.LEATHER_BOOTS);
    // material group for all armor
    public static final MaterialGroup ARMOR = MaterialGroup.of(HELMETS, CHESTPLATES, LEGGINGS, BOOTS);
    // material group for all tools
    public static final MaterialGroup TOOLS = MaterialGroup.of(PICKAXES, AXES, SHOVELS, HOES);
    // material group for melee weapons
    public static final MaterialGroup MELEE_WEAPONS = MaterialGroup.of(AXES, SWORDS);
    // material group for all weapons
    public static final MaterialGroup ALL_WEAPONS = MaterialGroup.of(MELEE_WEAPONS, BOW);
    // materail group for all damageable items
    public static final MaterialGroup DAMAGEABLE = MaterialGroup.of(ARMOR, TOOLS, ALL_WEAPONS);

    private final Set<Material> materials;

    private MaterialGroup() {
        this.materials = new HashSet<>();
    }

    protected void addMaterial(Material material) {
        this.materials.add(material);
    }

    protected void addManyMaterials(Material... materials) {
        for (Material material : materials) {
            this.materials.add(material);
        }
    }

    public boolean contains(Material material) {
        return this.materials.contains(material);
    }

    public Set<Material> getMaterials() {
        return new HashSet<>(this.materials);
    }

    public static MaterialGroup of(Material... materials) {
        MaterialGroup group = new MaterialGroup();
        for (Material material : materials) {
            group.addMaterial(material);
        }
        return group;
    }

    public static MaterialGroup of(MaterialGroup... groups) {
        MaterialGroup group = new MaterialGroup();
        for (MaterialGroup g : groups) {
            group.addManyMaterials(g.getMaterials().toArray(new Material[g.getMaterials().size()]));
        }
        return group;
    }

}
