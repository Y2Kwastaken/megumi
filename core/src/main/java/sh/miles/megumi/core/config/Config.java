package sh.miles.megumi.core.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import lombok.NonNull;

public class Config {

    private final Plugin plugin;
    private final String name;
    private FileConfiguration config;

    public Config(@NonNull final Plugin plugin, @NonNull final String name) {
        this.plugin = plugin;
        this.name = name;
        this.config = ConfigUtils.getConfigFile(plugin, name);
    }

    public boolean exists() {
        return ConfigUtils.exists(plugin, name);
    }

    public void save() {
        ConfigUtils.saveConfig(plugin, config, name);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void reload() {
        this.config = ConfigUtils.getConfigFile(plugin, name);
    }

}
