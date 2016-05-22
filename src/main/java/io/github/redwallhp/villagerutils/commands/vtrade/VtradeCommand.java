package io.github.redwallhp.villagerutils.commands.vtrade;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import org.bukkit.command.CommandSender;


public class VtradeCommand extends AbstractCommand {


    public VtradeCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
        addSubCommand("new", new NewVtradeCommand(plugin));
        addSubCommand("items", new ItemsVtradeCommand(plugin));
        addSubCommand("maxuses", new MaxUsesVtradeCommand(plugin));
        addSubCommand("experience", new ExperienceVtradeCommand(plugin));
    }


    public String getName() {
        return "vtrade";
    }


    public String getUsage() {
        return "/vtrade <subcommand>";
    }


    public boolean action(CommandSender sender, String[] args) {
        return true;
    }


}
