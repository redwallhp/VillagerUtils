package io.github.redwallhp.villagerutils.commands.vtrade;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;

public class NewVtradeCommand extends AbstractCommand {

    public NewVtradeCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "new";
    }

    @Override
    public String getUsage() {
        return "/vtrade new <limit>";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }
        Player player = (Player) sender;
        Random rand = new Random();
        // random int from 2-12, based on vanilla distribution
        int limit = rand.nextInt(11) + 2;
        // Create recipe with uses = maxUses = limit, or they both end up as 0.
        // Probable bug in Paper 525.
        MerchantRecipe recipe = new MerchantRecipe(new ItemStack(Material.AIR, 1), limit, limit, true);
        recipe.setUses(limit);
        plugin.getTradeWorkspace().put(player.getUniqueId(), recipe);
        player.sendMessage(ChatColor.DARK_AQUA + "You have created a new blank villager trade.");
        player.sendMessage(ChatColor.ITALIC + "Next: modify the trade items with /vtrade items");
        return true;
    }

}
