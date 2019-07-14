package io.github.redwallhp.villagerutils.helpers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

/**
 * Utility functions for trades.
 */
public class TradeHelper {
    /**
     * Show a description of a trade to a command sender.
     * 
     * @param sender the command sender.
     * @param recipe the trade recipe.
     */
    public static void describeTrade(CommandSender sender, MerchantRecipe recipe) {
        int ingredientndex = 1;
        for (ItemStack item : recipe.getIngredients()) {
            sender.sendMessage(ChatColor.DARK_AQUA + "Ingredient #" + ingredientndex + ": " +
                               ChatColor.WHITE + ItemHelper.getItemDescription(item));
        }
        sender.sendMessage(ChatColor.DARK_AQUA + "Result: " + ChatColor.WHITE + ItemHelper.getItemDescription(recipe.getResult()));
        sender.sendMessage(ChatColor.DARK_AQUA + "Uses: " + ChatColor.WHITE + recipe.getUses() + " / " + recipe.getMaxUses());
        sender.sendMessage(ChatColor.DARK_AQUA + "Gives XP: " + ChatColor.WHITE + recipe.hasExperienceReward());
    }

}
