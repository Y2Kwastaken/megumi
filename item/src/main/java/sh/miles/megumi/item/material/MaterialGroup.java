package sh.miles.megumi.item.material;

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
    public static final MaterialGroup TOOLS = MaterialGroup.of(MaterialGroup.PICKAXES, MaterialGroup.AXES,
            MaterialGroup.SHOVELS, MaterialGroup.HOES);
    public static final MaterialGroup WEAPONS = MaterialGroup.of(MaterialGroup.AXES, MaterialGroup.SWORDS);

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