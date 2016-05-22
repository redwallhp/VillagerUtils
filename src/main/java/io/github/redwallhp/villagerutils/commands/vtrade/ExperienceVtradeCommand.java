package io.github.redwallhp.villagerutils.commands.vtrade;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.MerchantRecipe;


public class ExperienceVtradeCommand extends AbstractCommand {


    public ExperienceVtradeCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }


    public String getName() {
        return "experience";
    }


    public String getUsage() {
        return "/vtrade experience <boolean>";
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
            Boolean value = Boolean.parseBoolean(args[0]);
            MerchantRecipe recipe = plugin.getTradeWorkspace().get(player.getUniqueId());
            recipe.setExperienceReward(value);
            player.sendMessage(ChatColor.DARK_AQUA + "Experience reward for trade set to " + value);
        } catch (Exception ex) {
            player.sendMessage(ChatColor.RED + getUsage());
            return false;
        }
        return true;
    }


}
