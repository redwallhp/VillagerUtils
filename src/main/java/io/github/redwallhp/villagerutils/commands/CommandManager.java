package io.github.redwallhp.villagerutils.commands;


import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.villager.VillagerCommand;
import io.github.redwallhp.villagerutils.commands.vtrade.VtradeCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class CommandManager implements CommandExecutor {


    private VillagerUtils plugin;
    private HashMap<String, AbstractCommand> commands;


    public CommandManager() {
        this.plugin = VillagerUtils.instance;
        commands = new HashMap<String, AbstractCommand>();
        registerCommands();
    }


    private void registerCommands() {

        commands.put("villager", new VillagerCommand(plugin));
        commands.put("vtrade", new VtradeCommand(plugin));

        for (String key : commands.keySet()) {
            plugin.getCommand(key).setExecutor(this);
        }

    }


    public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
        if (commands.containsKey(cmd.getName().toLowerCase())) {
            AbstractCommand command = commands.get(cmd.getName().toLowerCase());
            command.execute(sender, args);
        }
        return true;
    }


}
