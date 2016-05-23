package io.github.redwallhp.villagerutils.commands.vtrade;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.MerchantRecipe;


public class MaxUsesVtradeCommand extends AbstractCommand {


    public MaxUsesVtradeCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }


    public String getName() {
        return "maxuses";
    }


    public String getUsage() {
        return "/vtrade maxuses <int>";
    }


    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }
        Player player = (Player) sender;
        if (!plugin.getTradeWorkspace().containsKey(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You do not have a villager trade loaded. Use '/vtrade new' to start one.");
            return false;
        }
        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + getUsage());
            return false;
        }
        try {
            Integer value;
            if (args[0].equalsIgnoreCase("max")) {
                value = Integer.MAX_VALUE;
            } else {
                value = Integer.parseInt(args[0]);
            }
            MerchantRecipe recipe = plugin.getTradeWorkspace().get(player.getUniqueId());
            recipe.setMaxUses(value);
            player.sendMessage(ChatColor.DARK_AQUA + "Max uses for trade set to " + value);
        } catch (NumberFormatException ex) {
            player.sendMessage(ChatColor.RED + getUsage());
            return false;
        }
        return true;
    }


}
