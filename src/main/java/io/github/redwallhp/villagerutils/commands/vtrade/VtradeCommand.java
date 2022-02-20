package io.github.redwallhp.villagerutils.commands.vtrade;

import org.bukkit.command.CommandSender;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;

public class VtradeCommand extends AbstractCommand {

    public VtradeCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
        addSubCommand(new NewVtradeCommand(plugin));
        addSubCommand(new ItemsVtradeCommand(plugin));
        addSubCommand(new MaxUsesVtradeCommand(plugin));
        addSubCommand(new GivesXPVtradeCommand(plugin));
        addSubCommand(new InfoVtradeCommand(plugin));
    }

    @Override
    public String getName() {
        return "vtrade";
    }

    @Override
    public String getUsage() {
        return "/vtrade <subcommand>";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        return true;
    }

}
