package sh.miles.megumi.core.command;

import java.util.List;

/**
 * MegumiLabel is a wrapper class for command labels that allows for easy
 * integration
 * <p>
 * A command label is the name, permission, and aliases of a command
 * <p>
 * MegumiLabel is a nice alternative to using the plugin.yml file for a command
 */
public class MegumiLabel {

    private final String name;
    private final String permission;
    private final List<String> aliases;

    public MegumiLabel(String name, String permission, String... aliases) {
        this.name = name;
        this.permission = permission;
        this.aliases = List.of(aliases);
    }

    public String getName() {
        return this.name;
    }

    public String getPermission() {
        return this.permission;
    }

    public List<String> getAliases() {
        return List.copyOf(this.aliases);
    }

}
