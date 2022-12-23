package sh.miles.megumi.core.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * MegumiCompleter is a functional interface for tab completing a MegumiCommand
 * this interface is used instead of bukkit's {@link TabCompleter} interface
 * because it removes the redundant label argument which is prone to be abused
 * or ignored
 */
interface MegumiCompleter {

    /**
     * Acts as a tab completer for a MegumiCommand the list returned will be used to
     * serve autocomplete suggestions
     * 
     * @param sender the sender of the command
     * @param args   the arguments of the command
     * @return a list of strings to be used as autocomplete suggestions
     */
    List<String> complete(CommandSender sender, String[] args);

    static List<String> empty() {
        return List.of();
    }

    static List<String> intRange(int min, int max, int step) {
        List<String> list = new ArrayList<>();
        for (int i = min; i <= max; i += step) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    static List<String> doubleRange(double min, double max, double step) {
        List<String> list = new ArrayList<>();
        for (double i = min; i <= max; i += step) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    static List<String> onlinePlayers() {
        List<String> list = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            list.add(player.getName());
        }
        return list;
    }
}
