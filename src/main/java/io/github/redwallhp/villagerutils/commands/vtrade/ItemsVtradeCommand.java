package io.github.redwallhp.villagerutils.commands.vtrade;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;


public class ItemsVtradeCommand extends AbstractCommand {


    public ItemsVtradeCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }


    public String getName() {
        return "items";
    }


    public String getUsage() {
        return "/vtrade items";
    }


    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }
        Player player = (Player) sender;
        if (plugin.getTradeWorkspace().containsKey(player.getUniqueId())) {
            player.openInventory(getTradeInventory(player));
        } else {
            player.sendMessage(ChatColor.RED + "You do not have a villager trade loaded. Use '/vtrade new' to start one.");
        }
        return true;
    }


    private Inventory getTradeInventory(Player player) {
        MerchantRecipe recipe = plugin.getTradeWorkspace().get(player.getUniqueId());
        Inventory inventory = Bukkit.createInventory(null, 9, "Edit Villager Trade");
        for (int i = 2; i <= 8; i++) {
            inventory.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1));
        }
        for (ItemStack ingredient : recipe.getIngredients()) {
            int num = recipe.getIngredients().indexOf(ingredient);
            inventory.setItem(num, ingredient);
        }
        inventory.setItem(8, recipe.getResult());
        return inventory;
    }


}
