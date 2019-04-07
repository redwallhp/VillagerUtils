package io.github.redwallhp.villagerutils.commands.vtrade;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;

public class ItemsVtradeCommand extends AbstractCommand {

    public ItemsVtradeCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "items";
    }

    @Override
    public String getUsage() {
        return "/vtrade items";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }

        if (args.length != 0) {
            sender.sendMessage(ChatColor.RED + "Unexpected arguments. Usage: " + getUsage());
            return false;
        }

        Player player = (Player) sender;
        if (plugin.getWorkspaceManager().hasWorkspace(player)) {
            player.openInventory(getTradeInventory(player));
        } else {
            player.sendMessage(ChatColor.RED + "You do not have a villager trade loaded. Use '/vtrade new' to start one.");
        }
        return true;
    }

    private Inventory getTradeInventory(Player player) {
        MerchantRecipe recipe = plugin.getWorkspaceManager().getWorkspace(player);
        Inventory inventory = Bukkit.createInventory(null, 9, "Edit Villager Trade");
        for (int i = 2; i <= 8; i++) {
            inventory.setItem(i, new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1));
        }
        for (ItemStack ingredient : recipe.getIngredients()) {
            int num = recipe.getIngredients().indexOf(ingredient);
            inventory.setItem(num, ingredient);
        }
        inventory.setItem(8, recipe.getResult());
        return inventory;
    }

}
