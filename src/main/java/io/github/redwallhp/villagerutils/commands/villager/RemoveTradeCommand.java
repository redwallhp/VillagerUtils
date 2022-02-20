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

public class RemoveTradeCommand extends AbstractCommand implements TabCompleter {

    public RemoveTradeCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "removetrade";
    }

    @Override
    public String getUsage() {
        return "/villager removetrade <position>";
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

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Invalid arguments. Usage: " + getUsage());
            return false;
        }

        List<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>();
        recipes.addAll(target.getRecipes());

        try {
            int position = Integer.parseInt(args[0]);
            if (position >= 1 && position <= recipes.size()) {
                recipes.remove(position - 1);
                target.setRecipes(recipes);
                player.sendMessage(ChatColor.DARK_AQUA + "Villager trade " + position + " removed.");
                return true;
            }
        } catch (IllegalArgumentException ex) {
        }
        player.sendMessage(ChatColor.RED + "The position must be between 1 and the number of trades.");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 2 && sender instanceof Player) {
            Player player = (Player) sender;
            AbstractVillager target = VillagerHelper.getAbstractVillagerInLineOfSight(player);
            if (target != null) {
                return IntStream.rangeClosed(1, target.getRecipeCount())
                .mapToObj(i -> Integer.toString(i))
                .filter(completion -> completion.startsWith(args[1]))
                .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

}