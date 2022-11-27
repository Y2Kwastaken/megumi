package sh.miles.megumi.command;

import java.util.List;

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
