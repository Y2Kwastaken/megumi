package sh.miles.megumi.core;

import org.bukkit.plugin.Plugin;

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
