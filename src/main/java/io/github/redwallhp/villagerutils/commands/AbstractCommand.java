package io.github.redwallhp.villagerutils.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.redwallhp.villagerutils.VillagerUtils;

public abstract class AbstractCommand {

    protected VillagerUtils plugin;
    private final String permission;
    private final Map<String, AbstractCommand> subCommands;

    public AbstractCommand(VillagerUtils plugin, String permission) {
        this.plugin = plugin;
        this.permission = permission;
        this.subCommands = new LinkedHashMap<String, AbstractCommand>();
    }

    public boolean execute(CommandSender sender, String[] args) {
        if (!checkPermission(sender)) {
            return false;
        }

        if (subCommands.size() > 0) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Usage: " + getUsage());
                sender.sendMessage(getSubcommandsHelp());
                return false;
            } else if (args.length > 0 && !subCommands.containsKey(args[0].toLowerCase())) {
                sender.sendMessage(ChatColor.RED + "Usage: " + getUsage());
                sender.sendMessage(getSubcommandsHelp());
                return false;
            } else {
                AbstractCommand subcmd = subCommands.get(args[0].toLowerCase());
                args = Arrays.copyOfRange(args, 1, args.length);
                subcmd.execute(sender, args);
            }
        }
        return action(sender, args);
    }

    public boolean checkPermission(CommandSender sender) {
        if (!sender.hasPermission(permission)) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
            return false;
        }
        return true;
    }

    public void addSubCommand(AbstractCommand command) {
        subCommands.put(command.getName(), command);
    }

    public AbstractCommand getSubCommand(String name) {
        return subCommands.get(name.toLowerCase());
    }

    public Collection<String> getSubCommandNames() {
        return subCommands.keySet();
    }

    private String getSubcommandsHelp() {
        return ChatColor.DARK_AQUA + "Subcommands: " +
               ChatColor.GRAY + subCommands.keySet().stream().collect(Collectors.joining(", "));
    }

    /**
     * The action the command should perform
     * 
     * @return true if the command was successful
     */
    public abstract boolean action(CommandSender sender, String[] args);

    /**
     * The name of the command
     */
    public abstract String getName();

    /**
     * The command usage, for this specific command
     */
    public abstract String getUsage();

}
