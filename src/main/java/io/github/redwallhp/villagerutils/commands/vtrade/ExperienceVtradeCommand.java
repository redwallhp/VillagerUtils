package io.github.redwallhp.villagerutils.commands.vtrade;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.MerchantRecipe;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;

public class ExperienceVtradeCommand extends AbstractCommand implements TabCompleter {

    public ExperienceVtradeCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "experience";
    }

    @Override
    public String getUsage() {
        return "/vtrade experience <boolean>";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }
        Player player = (Player) sender;
        if (!plugin.getTradeWorkspace().containsKey(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You do not have a villager trade loaded. Use '/vtrade new' to start one.");
            return false;
        }
        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + getUsage());
            return false;
        }
        try {
            Boolean value = Boolean.parseBoolean(args[0]);
            MerchantRecipe recipe = plugin.getTradeWorkspace().get(player.getUniqueId());
            recipe.setExperienceReward(value);
            player.sendMessage(ChatColor.DARK_AQUA + "Experience reward for trade set to " + value);
        } catch (Exception ex) {
            player.sendMessage(ChatColor.RED + getUsage());
            return false;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 2) {
            return Arrays.asList("false", "true").stream()
            .filter(completion -> completion.startsWith(args[1].toLowerCase()))
            .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

}
