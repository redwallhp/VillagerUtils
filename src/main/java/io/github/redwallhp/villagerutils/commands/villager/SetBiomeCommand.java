package io.github.redwallhp.villagerutils.commands.villager;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.VillagerSpecificAbstractCommand;
import io.github.redwallhp.villagerutils.helpers.VillagerHelper;

public class SetBiomeCommand extends VillagerSpecificAbstractCommand implements TabCompleter {

    public SetBiomeCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "biome";
    }

    @Override
    public String getUsage() {
        return "/villager biome <biome>";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }
        Player player = (Player) sender;

        Villager villager = getVillagerInLineOfSight(player, "Wandering traders can't change their appearance.");
        if (villager == null) {
            return false;
        }

        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "Invalid arguments. Usage: " + getUsage());
            return false;
        }

        Villager.Type biome = (args.length == 0) ? null : VillagerHelper.getVillagerTypeFromString(args[0]);
        if (biome == null) {
            player.sendMessage(ChatColor.RED + "You must specify a villager biome.");
            player.sendMessage(ChatColor.GRAY + "Valid biones: " + String.join(", ", VillagerHelper.getVillagerTypeNames()));
            return false;
        }

        villager.setVillagerType(biome);
        player.sendMessage(ChatColor.DARK_AQUA + "Villager biome updated.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 2) {
            return VillagerHelper.getVillagerTypeNames().stream()
            .filter(completion -> completion.startsWith(args[1].toLowerCase()))
            .sorted().collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}
