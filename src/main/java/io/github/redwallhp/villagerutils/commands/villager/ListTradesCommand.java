package io.github.redwallhp.villagerutils.commands.villager;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.MerchantRecipe;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import io.github.redwallhp.villagerutils.helpers.TradeHelper;
import io.github.redwallhp.villagerutils.helpers.VillagerHelper;

public class ListTradesCommand extends AbstractCommand {

    public ListTradesCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "listtrades";
    }

    @Override
    public String getUsage() {
        return "/villager listtrades";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }

        Player player = (Player) sender;
        AbstractVillager target = VillagerHelper.getAbstractVillagerInLineOfSight(player);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "You're not looking at a villager.");
            return false;
        }

        if (args.length != 0) {
            player.sendMessage(ChatColor.RED + "Invalid arguments. Usage: " + getUsage());
            return false;
        }

        int index = 1;
        for (MerchantRecipe recipe : target.getRecipes()) {
            sender.sendMessage(ChatColor.DARK_AQUA + "---------- Trade #" + index + " ----------");
            TradeHelper.describeTrade(sender, recipe);
            ++index;
        }
        sender.sendMessage(ChatColor.DARK_AQUA + "======== Total trades: " + (index - 1) + " ========");
        return true;
    }

}
