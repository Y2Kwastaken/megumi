package sh.miles.megumi.core.command;

import org.bukkit.command.CommandSender;

import lombok.NonNull;

public interface MegumiExecutor {

    /**
     * Acts as an executor for a MegumiCommand the boolean returned will be used to
     * tell the server if the command was executed successfully unsuccessfull
     * execution will return the usage message
     * 
     * @param sender the sender of the command
     * @param args   the arguments of the command
     * @return true if the command was executed successfully, false otherwise
     */
    public boolean execute(@NonNull final CommandSender sender, @NonNull final String[] args);

}
