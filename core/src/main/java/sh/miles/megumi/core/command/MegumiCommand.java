package sh.miles.megumi.core.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import sh.miles.megumi.core.command.bridge.BridgeCommand;

/**
 * A MegumiCommand is a wrapper for bukkit's Command system that allows for
 * registartion of commands without using the plugin.yml file
 * <p>
 * The numerour other benefits of this system are the following:
 * <ul>
 * <li>A flexible system that allows for subcommands and automatically completes
 * for them with permissions thought through</li>
 * <li>Allows for the use of {@link MegumiCompleter} and {@link MegumiExecutor}
 * which remove redundancies and the abuse of the label argument</li>
 * <li>Allows for the use of {@link MegumiLabel} which allows for easy creation
 * of Labels</li>
 * </ul>
 * <p>
 * It is also worth noting that MegumiCommand class can also be used as a
 * subcommand and its label would then be the name of the subcommand and its
 * aliases and permissions follow suite.
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
            Bukkit.getLogger().warning("Command " + command.getLabel().getName() + " already exists on this command");
            return false;
        }

        for (final String alias : command.getLabel().getAliases()) {
            if (!subCommands.containsKey(alias)) {
                subCommands.put(alias, command);
            } else {
                Bukkit.getLogger()
                        .warning("Alias for Command " + command.getLabel().getName() + " already exists: " + alias);
            }
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

        final String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, subArgs.length);
        return sub.execute(sender, subArgs);
    }

    @Override
    // java:S2211 and java:S1142 are suppressed
    @SuppressWarnings("all")
    public List<String> complete(CommandSender sender, String[] args) {

        if (completer != null) {
            return completer.complete(sender, args);
        }

        if (args.length == 1) {
            return this.subCommands.keySet().stream().filter(s -> {
                final MegumiCommand sub = subCommands.get(s);
                if (sub.getLabel().getPermission() == null) {
                    return true;
                }

                return sender.hasPermission(sub.getLabel().getPermission());
            }).toList();
        }

        final MegumiCommand sub = subCommands.getOrDefault(args[0], null);
        if (sub == null) {
            return List.of();
        }

        if (sub.getLabel().getPermission() != null && !sender.hasPermission(sub.getLabel().getPermission())) {
            return List.of();
        }

        final String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, subArgs.length);
        return sub.complete(sender, subArgs);
    }

    public static void register(@NonNull final MegumiCommand command) {
        new BridgeCommand(command).register();
    }

    public static void unregister(@NonNull final MegumiCommand command) {
        new BridgeCommand(command).unregister();
    }

}
