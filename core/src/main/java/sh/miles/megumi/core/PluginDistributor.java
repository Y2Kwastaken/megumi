package sh.miles.megumi.core;

import org.bukkit.plugin.Plugin;

/**
 * This class is used to distribute the plugin instance to other classes.
 * It is used to avoid the use of static methods in the main plugin class of
 * your plugin.
 * <p>
 * To use this class, you need to call {@link #setInstance(Plugin)} in your main
 * plugin class.
 * <p>
 * Ensure that you relocate this plugin to avoid conflicts with other plugins.
 */
public final class PluginDistributor {

    private static Plugin instance;

    private PluginDistributor() {
    }

    public static void setInstance(Plugin instance) {
        PluginDistributor.instance = instance;
    }

    public static Plugin getInstance() {
        return instance;
    }

}
