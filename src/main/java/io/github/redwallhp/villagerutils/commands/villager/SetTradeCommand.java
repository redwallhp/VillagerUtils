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

public class SetTradeCommand extends AbstractCommand implements TabCompleter {

    public SetTradeCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "settrade";
    }

    @Override
    public String getUsage() {
        return "/villager settrade <position>";
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

        List<MerchantRecipe> recipes = new ArrayList<>(target.getRecipes());
        try {
            int position = Integer.parseInt(args[0]);
            if (position >= 1 && position <= recipes.size()) {
                recipes.set(position - 1, plugin.getWorkspaceManager().getWorkspace(player));
                target.setRecipes(recipes);
                player.sendMessage(ChatColor.DARK_AQUA + "Villager trade " + position + " set from your workspace.");
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
