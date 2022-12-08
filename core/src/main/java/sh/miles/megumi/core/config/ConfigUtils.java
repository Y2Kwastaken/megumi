package sh.miles.megumi.core.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigUtils {

    private ConfigUtils() {
    }

    public static FileConfiguration getConfigFile(final Plugin plugin, final String name) {
        return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), name));
    }

    public static File createDirectory(final Plugin plugin, final String DirName) {
        final File newDir = new File(plugin.getDataFolder(), DirName.replace("/", File.separator));
        if (!newDir.exists()) {
            newDir.mkdirs();
        }
        return newDir;
    }

    public static FileConfiguration createConfig(final Plugin plugin, final String name) {
        final File file = new File(plugin.getDataFolder(), name);

        if (!new File(plugin.getDataFolder(), name).exists()) {
            plugin.saveResource(name, false);
        }

        final FileConfiguration configuration = getConfigFile(plugin, name);
        if (!file.exists()) {
            try {
                configuration.save(file);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return configuration;
    }

    public static void createFile(final Plugin plugin, final String name) {
        final File file = new File(plugin.getDataFolder(), name);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean exists(final Plugin plugin, final String name) {
        return new File(plugin.getDataFolder(), name).exists();
    }

    public static void saveConfig(final Plugin plugin, final FileConfiguration config, final String name) {
        try {
            config.save(new File(plugin.getDataFolder(), name));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
