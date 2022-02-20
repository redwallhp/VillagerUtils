package io.github.redwallhp.villagerutils.commands.vtrade;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.MerchantRecipe;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import io.github.redwallhp.villagerutils.helpers.TradeHelper;

public class InfoVtradeCommand extends AbstractCommand {

    public InfoVtradeCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getUsage() {
        return "/vtrade info";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }
        Player player = (Player) sender;

        if (args.length != 0) {
            player.sendMessage(ChatColor.RED + "Unexpected arguments. Usage: " + getUsage());
            return false;
        }

        MerchantRecipe recipe = plugin.getWorkspaceManager().getWorkspace(player);
        if (recipe == null) {
            player.sendMessage(ChatColor.RED + "Your workspace is empty.");
            return false;
        } else {
            player.sendMessage(ChatColor.DARK_AQUA + "---------- Workspace ----------");
            TradeHelper.describeTrade(sender, recipe);
            return true;
        }
    }

}
