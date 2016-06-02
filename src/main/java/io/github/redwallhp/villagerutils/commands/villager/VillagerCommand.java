package io.github.redwallhp.villagerutils.commands.villager;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import org.bukkit.command.CommandSender;


public class VillagerCommand extends AbstractCommand {


    public VillagerCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
        addSubCommand("name", new SetNameCommand(plugin));
        addSubCommand("profession", new SetProfessionCommand(plugin));
        addSubCommand("addtrade", new AddTradeCommand(plugin));
        addSubCommand("cleartrades", new ClearTradesCommand(plugin));
        addSubCommand("spawn", new SpawnCommand(plugin));
        addSubCommand("static", new SetStaticCommand(plugin));
    }


    public String getName() {
        return "villager";
    }


    public String getUsage() {
        return "/villager <subcommand>";
    }


    public boolean action(CommandSender sender, String[] args) {
        return true;
    }


}
