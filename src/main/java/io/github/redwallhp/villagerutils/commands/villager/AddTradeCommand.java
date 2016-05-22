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


public class AddTradeCommand extends AbstractCommand {


    public AddTradeCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }


    public String getName() {
        return "addtrade";
    }


    public String getUsage() {
        return "/villager addtrade [index]";
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
        if (!plugin.getTradeWorkspace().containsKey(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You do not have a trade loaded. Use '/vtrade'");
            return false;
        }
        MerchantRecipe recipe = plugin.getTradeWorkspace().get(player.getUniqueId());
        if (recipe.getIngredients().size() == 0) {
            player.sendMessage(ChatColor.RED + "You haven't set the trade items yet! Use '/vtrade items'");
            return false;
        }
        List<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>();
        recipes.addAll(target.getRecipes());
        recipes.add(recipe);
        target.setRecipes(recipes);
        player.sendMessage(ChatColor.DARK_AQUA + "Villager trade added.");
        return true;
    }


}
