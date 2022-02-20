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
        return "/vtrade new [<maxuses>|max]";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }
        Player player = (Player) sender;

        if (args.length > 1) {
            player.sendMessage(ChatColor.RED + "Unexpected arguments. Usage: " + getUsage());
            return false;
        }

        int limit;
        if (args.length == 0) {
            // random int from 2-12, based on vanilla distribution
            limit = new Random().nextInt(11) + 2;
        } else { // (args.length == 1)
            try {
                limit = args[0].equalsIgnoreCase("max") ? Integer.MAX_VALUE : Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                player.sendMessage(ChatColor.RED + "The number of uses must be an integer or the word 'max'.");
                return false;
            }
        }

        // Create recipe with uses = maxUses = limit, or they both end up as 0.
        // Probable bug in Paper 525.
        MerchantRecipe recipe = new MerchantRecipe(new ItemStack(Material.AIR, 1), limit, limit, true);
        // Oh, to be sure, to be sure.
        recipe.setMaxUses(limit);
        recipe.setUses(limit);
        plugin.getWorkspaceManager().setWorkspace(player, recipe);
        player.sendMessage(ChatColor.DARK_AQUA + "You have created a new blank villager trade with at most " + limit + " uses before locking.");
        player.sendMessage(ChatColor.ITALIC + "Next: modify the trade items with /vtrade items");
        return true;
    }

}
