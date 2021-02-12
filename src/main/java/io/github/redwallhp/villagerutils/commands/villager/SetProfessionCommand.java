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
import org.bukkit.entity.Villager.Profession;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.VillagerSpecificAbstractCommand;
import io.github.redwallhp.villagerutils.helpers.VillagerHelper;

public class SetProfessionCommand extends VillagerSpecificAbstractCommand implements TabCompleter {

    public SetProfessionCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "profession";
    }

    @Override
    public String getUsage() {
        return "/villager profession <profession>";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }
        Player player = (Player) sender;

        Villager villager = getVillagerInLineOfSight(player, "Wandering traders can't change their profession.");
        if (villager == null) {
            return false;
        }

        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "Invalid arguments. Usage: " + getUsage());
            return false;
        }

        Profession profession = (args.length == 0) ? null : VillagerHelper.getProfessionFromString(args[0]);
        if (profession == null) {
            player.sendMessage(ChatColor.RED + "You must specify a profession.");
            player.sendMessage(ChatColor.GRAY + "Valid professions: " + String.join(", ", VillagerHelper.getProfessionNames()));
            return false;
        }

        villager.setProfession(profession);
        player.sendMessage(ChatColor.DARK_AQUA + "Villager profession updated.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 2) {
            return VillagerHelper.getProfessionNames().stream()
            .filter(completion -> completion.startsWith(args[1].toLowerCase()))
            .sorted().collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

}
