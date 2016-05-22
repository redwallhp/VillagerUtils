package io.github.redwallhp.villagerutils.commands.villager;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import io.github.redwallhp.villagerutils.helpers.VillagerHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;


public class ClearTradesCommand extends AbstractCommand {


    public ClearTradesCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }


    public String getName() {
        return "cleartrades";
    }


    public String getUsage() {
        return "/villager cleartrades";
    }


    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }
        Player player = (Player) sender;
        Villager target = VillagerHelper.getVillagerInLineOfSight(player);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "You're not looking at a villager.");
            return false;
        }
        List<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>();
        target.setRecipes(recipes);
        player.sendMessage(ChatColor.DARK_AQUA + "Villager trades cleared.");
        return true;
    }


}
