package io.github.redwallhp.villagerutils.commands.vtrade;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.Random;


public class NewVtradeCommand extends AbstractCommand {


    public NewVtradeCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }


    public String getName() {
        return "new";
    }


    public String getUsage() {
        return "/vtrade new <limit>";
    }


    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }
        Player player = (Player) sender;
        Random rand = new Random();
        int limit = rand.nextInt(10) + 2; // random int from 2-12, based on vanilla distribution
        MerchantRecipe recipe = new MerchantRecipe(new ItemStack(Material.AIR, 1), limit);
        recipe.setUses(limit); //unlock the trade automatically
        plugin.getTradeWorkspace().put(player.getUniqueId(), recipe);
        player.sendMessage(ChatColor.DARK_AQUA + "You have created a new blank villager trade.");
        player.sendMessage(ChatColor.ITALIC + "Next: modify the trade items with /vtrade items");
        return true;
    }


}
