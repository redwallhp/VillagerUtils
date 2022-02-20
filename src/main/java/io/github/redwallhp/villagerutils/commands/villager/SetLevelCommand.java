package io.github.redwallhp.villagerutils.commands.villager;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.VillagerSpecificAbstractCommand;

public class SetLevelCommand extends VillagerSpecificAbstractCommand implements TabCompleter {

    public SetLevelCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "level";
    }

    @Override
    public String getUsage() {
        return "/villager level <level>";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }
        Player player = (Player) sender;

        Villager villager = getVillagerInLineOfSight(player, "Wandering traders can't change their level.");
        if (villager == null) {
            return false;
        }

        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "Invalid arguments. Usage: " + getUsage());
            return false;
        }

        Integer level = null;
        try {
            level = Integer.parseInt(args[0]);
        } catch (IllegalArgumentException ex) {
        }
        if (level == null || level < 1 || level > 5) {
            sender.sendMessage(ChatColor.RED + "The level must be between 1 and 5, inclusive.");
            return false;
        }

        villager.setVillagerLevel(level);
        player.sendMessage(ChatColor.DARK_AQUA + "Villager level set to " + level + ".");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 2) {
            return IntStream.rangeClosed(1, 5).mapToObj(Integer::toString)
            .filter(completion -> completion.startsWith(args[1]))
            .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}
