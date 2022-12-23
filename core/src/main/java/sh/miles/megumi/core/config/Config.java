package sh.miles.megumi.core.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import lombok.NonNull;

/**
 * A FileConfiguration wrapper that is semi-redundant at this point
 * it is encouraged to use the ConfigUtils class instead of this class
 * 
 * @deprecated use {@link ConfigUtils} instead this class will likely be removed
 *             in the future
 */
@Deprecated
public class Config {

    private final Plugin plugin;
    private final String name;
    private FileConfiguration conf;

    public Config(@NonNull final Plugin plugin, @NonNull final String name) {
        this.plugin = plugin;
        this.name = name;
        this.conf = ConfigUtils.getConfigFile(plugin, name);
    }

    public boolean exists() {
        return ConfigUtils.exists(plugin, name);
    }

    public void save() {
        ConfigUtils.saveConfig(plugin, conf, name);
    }

    public FileConfiguration getConfig() {
        return conf;
    }

    public void reload() {
        this.conf = ConfigUtils.getConfigFile(plugin, name);
    }

}
