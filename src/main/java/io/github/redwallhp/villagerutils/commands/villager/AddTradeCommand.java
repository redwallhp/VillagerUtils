package io.github.redwallhp.villagerutils.commands.villager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.MerchantRecipe;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import io.github.redwallhp.villagerutils.helpers.VillagerHelper;

public class AddTradeCommand extends AbstractCommand implements TabCompleter {

    public AddTradeCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "addtrade";
    }

    @Override
    public String getUsage() {
        return "/villager addtrade [<position>]";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }
        Player player = (Player) sender;
        AbstractVillager target = VillagerHelper.getAbstractVillagerInLineOfSight(player);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "You're not looking at a villager.");
            return false;
        }
        if (!plugin.getWorkspaceManager().hasWorkspace(player)) {
            player.sendMessage(ChatColor.RED + "You do not have a trade loaded. Use '/vtrade'");
            return false;
        }
        MerchantRecipe recipe = plugin.getWorkspaceManager().getWorkspace(player);
        if (recipe.getIngredients().size() == 0) {
            player.sendMessage(ChatColor.RED + "You haven't set the trade items yet! Use '/vtrade items'");
            return false;
        }

        if (args.length > 1) {
            player.sendMessage(ChatColor.RED + "Invalid arguments. Usage: " + getUsage());
            return false;
        }

        List<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>();
        recipes.addAll(target.getRecipes());

        int position = Integer.MAX_VALUE;
        if (args.length == 1) {
            try {
                position = Integer.parseInt(args[0]);
            } catch (IllegalArgumentException ex) {
                player.sendMessage(ChatColor.RED + "The position must be an integer.");
                return false;
            }
        }

        if (position <= 0) {
            player.sendMessage(ChatColor.RED + "The position must be at least 1.");
            return false;
        } else if (position > recipes.size()) {
            // Allow any large position value when appending.
            recipes.add(recipe);
        } else {
            recipes.add(position - 1, recipe);
        }

        target.setRecipes(recipes);
        player.sendMessage(ChatColor.DARK_AQUA + "Villager trade added.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 2 && sender instanceof Player) {
            Player player = (Player) sender;
            AbstractVillager target = VillagerHelper.getAbstractVillagerInLineOfSight(player);
            if (target != null) {
                return IntStream.rangeClosed(1, target.getRecipeCount() + 1)
                .mapToObj(i -> Integer.toString(i))
                .filter(completion -> completion.startsWith(args[1]))
                .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

}
