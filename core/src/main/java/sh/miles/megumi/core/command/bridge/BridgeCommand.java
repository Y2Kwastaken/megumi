package sh.miles.megumi.core.command.bridge;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.SimplePluginManager;

import lombok.NonNull;
import sh.miles.megumi.core.command.MegumiCommand;

/**
 * Not meant to be used by api.
 * 
 * @implNote
 *           By using you accept that you are responsible for any problems that
 *           may arise from using this class.
 */
public class BridgeCommand extends BukkitCommand {

    private static final Field COMMAND_MAP_FIELD;
    private static final Field KNOWN_COMMANDS_FIELD;

    /**
     * Initializes fields prior to use
     */
    static {
        try {
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);
            COMMAND_MAP_FIELD = field;

            field = SimpleCommandMap.class.getDeclaredField("knownCommands");
            field.setAccessible(true);
            KNOWN_COMMANDS_FIELD = field;

        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get command map from plugin manager");
        }
    }

    private final MegumiCommand command;

    public BridgeCommand(@NonNull final MegumiCommand command) {
        super(command.getLabel().getName(), "A Megumi Framework Command", "/" + command.getLabel().getName(),
                command.getLabel().getAliases());
        this.command = command;
        setPermission(command.getLabel().getPermission());
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        return command.execute(sender, args);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return command.complete(sender, args);
    }

    /**
     * Registers the command with the server
     */
    @SuppressWarnings("unchecked")
    public void register() {
        try {
            final CommandMap cmap = (CommandMap) COMMAND_MAP_FIELD.get(org.bukkit.Bukkit.getPluginManager());
            final Map<String, Command> knownCommands = (Map<String, Command>) KNOWN_COMMANDS_FIELD.get(cmap);
            if (cmap.getCommand(getLabel()) != null) {
                knownCommands.remove(getLabel());
            }

            getAliases().forEach(alias -> {
                if (cmap.getCommand(alias) != null) {
                    knownCommands.remove(alias);
                }
            });

            cmap.register(getLabel(), this);

        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void unregister() {
        try {
            final CommandMap cmap = (CommandMap) COMMAND_MAP_FIELD.get(org.bukkit.Bukkit.getPluginManager());
            final Map<String, Command> knownCommands = (Map<String, Command>) KNOWN_COMMANDS_FIELD.get(cmap);
            if (cmap.getCommand(getLabel()) != null) {
                knownCommands.remove(getLabel());
            }

            getAliases().forEach(alias -> {
                if (cmap.getCommand(alias) != null) {
                    knownCommands.remove(alias);
                }
            });

        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
