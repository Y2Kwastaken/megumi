package sh.miles.megumi.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import sh.miles.megumi.command.bridge.BridgeCommand;

/**
 * Represents a command that can be executed by a CommandSender
 */
public class MegumiCommand implements MegumiCompleter, MegumiExecutor {

    @Getter
    private final MegumiLabel label;
    @Setter
    @Getter
    private MegumiExecutor executor;
    @Setter
    @Getter
    private MegumiCompleter completer;

    private final Map<String, MegumiCommand> subCommands;

    /**
     * Creates a new MegumiCommand with the given label
     * 
     * @param label the label of the command
     */
    public MegumiCommand(@NonNull final MegumiLabel label) {
        this.label = label;
        this.subCommands = new HashMap<>();
    }

    /**
     * Registers a sub command
     * 
     * @param command the subcommand to register
     * @return true if the subcommand could be registered, false otherwise
     */
    public boolean registerSubCommand(@NonNull final MegumiCommand command) {
        if (subCommands.containsKey(command.getLabel().getName())) {
            return false;
        }
        subCommands.put(command.getLabel().getName(), command);
        return true;
    }

    /**
     * Unregisters a subcommand with the given name
     * 
     * @param command the name of the subcommand to unregister
     * @return true if the subcommand could be unregistered, false otherwise
     */
    public boolean unregisterSubCommand(@NonNull final String name) {
        if (!subCommands.containsKey(name)) {
            return false;
        }
        subCommands.remove(name);
        return true;
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {

        if (args.length == 0) {
            boolean result = true;
            if (this.executor != null) {
                result = this.executor.execute(sender, args);
            }
            return result;
        }

        final MegumiCommand sub = subCommands.getOrDefault(args[0], null);
        if (sub == null) {
            return true;
        }

        return sub.execute(sender, args);
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {

        if (completer != null) {
            return completer.complete(sender, args);
        }

        if (args.length == 1) {
            return this.subCommands.keySet().stream().toList();
        }

        final MegumiCommand sub = subCommands.getOrDefault(args[0], null);
        if (sub == null) {
            return List.of();
        }

        return sub.complete(sender, args);
    }

    public static void register(@NonNull final MegumiCommand command) {
        new BridgeCommand(command).register();
    }

    public static void unregister(@NonNull final MegumiCommand command) {
        new BridgeCommand(command).unregister();
    }

}
