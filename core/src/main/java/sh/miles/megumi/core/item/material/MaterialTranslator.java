package sh.miles.megumi.core.item.material;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

/**
 * Map like object for translating Materials to another Material.
 */
public class MaterialTranslator {

    private final Map<Material, Material> map;
    private boolean lock;

    private MaterialTranslator() {
        this.map = new HashMap<>();
    }

    public void put(Material key, Material value) {
        if (lock) {
            throw new IllegalStateException("MaterialTranslator is locked");
        }
        map.put(key, value);
    }

    public void removeKey(Material key) {
        if (lock) {
            throw new IllegalStateException("MaterialTranslator is locked");
        }
        map.remove(key);
    }

    public Material getKey(Material key) {
        return map.get(key);
    }

    public boolean hasKey(Material key) {
        return map.containsKey(key);
    }

    public void lock() {
        lock = true;
    }

    /**
     * Creates an empty MaterialTranslator.
     * 
     * @return an empty MaterialTranslator
     */
    public static MaterialTranslator empty() {
        return new MaterialTranslator();
    }

    /**
     * Combines many MaterialTranslators into one.
     * 
     * @param translators the MaterialTranslators to combine
     * @return a MaterialTranslator that combines all the given MaterialTranslators
     */
    public static MaterialTranslator of(MaterialTranslator... translators) {
        MaterialTranslator translator = new MaterialTranslator();
        for (MaterialTranslator t : translators) {
            translator.map.putAll(t.map);
        }
        return translator;
    }

    /**
     * Builds a MaterialTranslator with a Material, Material map
     * 
     * @param map the map to build the MaterialTranslator with
     * @return the MaterialTranslator
     */
    public static MaterialTranslator of(Map<Material, Material> map) {
        MaterialTranslator translator = new MaterialTranslator();
        translator.map.putAll(map);
        return translator;
    }

    /**
     * Builds a MaterialTranslator from a Map of Materials this is achieved by using
     * a simple iteration setup. sample array below
     * <p>
     * <code>
     * Material[] materials = { Material.COBBLESTONE, Material.STONE, Material.OAK_LOG, Material.CHARCOAL};
     * </code>
     * <p>
     * this will result in the following translation
     * <p>
     * <code>
     * Material.COBBLESTONE -> Material.STONE
     * <p>
     * Material.OAK_LOG -> Material.CHARCOAL
     * </code>
     * 
     * 
     * @param array the array to build the translator from
     * @return
     */
    public static MaterialTranslator fromArray(Material[] array) {
        MaterialTranslator translator = new MaterialTranslator();
        for (int i = 0; i < array.length; i += 2) {
            translator.put(array[i], array[i + 1]);
        }
        return translator;
    }

}
