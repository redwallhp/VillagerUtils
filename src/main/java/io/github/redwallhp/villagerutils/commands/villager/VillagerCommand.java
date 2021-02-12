package io.github.redwallhp.villagerutils.commands.villager;

import org.bukkit.command.CommandSender;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;

public class VillagerCommand extends AbstractCommand {

    public VillagerCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
        // Subcommands added in the order listed by /villager help.
        addSubCommand(new SpawnCommand(plugin));
        addSubCommand(new SetBiomeCommand(plugin));
        addSubCommand(new SetProfessionCommand(plugin));
        addSubCommand(new SetLevelCommand(plugin));
        addSubCommand(new SetNameCommand(plugin));
        addSubCommand(new SetStaticCommand(plugin));
        addSubCommand(new AddTradeCommand(plugin));
        addSubCommand(new RemoveTradeCommand(plugin));
        addSubCommand(new ListTradesCommand(plugin));
        addSubCommand(new ClearTradesCommand(plugin));
        // Disabled because it doesn't work:
        // addSubCommand(new RefreshTradesCommand(plugin));
        addSubCommand(new GetTradeCommand(plugin));
        addSubCommand(new SetTradeCommand(plugin));
    }

    @Override
    public String getName() {
        return "villager";
    }

    @Override
    public String getUsage() {
        return "/villager <subcommand>";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        return true;
    }

}
