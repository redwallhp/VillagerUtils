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
import org.bukkit.entity.Villager.Career;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import io.github.redwallhp.villagerutils.helpers.VillagerHelper;

public class SetCareerCommand extends AbstractCommand implements TabCompleter {

    public SetCareerCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "career";
    }

    @Override
    public String getUsage() {
        return "/villager career <name>";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }
        Player player = (Player) sender;

        Villager target = VillagerHelper.getVillagerInLineOfSight(player);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "You're not looking at a villager.");
            return false;
        }

        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "Invalid arguments. Usage: " + getUsage());
            return false;
        }

        Career career = (args.length == 0) ? null : VillagerHelper.getCareerFromString(args[0]);
        if (career == null) {
            player.sendMessage(ChatColor.RED + "You must specify a career.");
            player.sendMessage(ChatColor.GRAY + "Valid careers: " + String.join(", ", VillagerHelper.getCareerNames()));
            return false;
        }

        target.setProfession(career.getProfession());
        target.setCareer(career, false);
        player.sendMessage(ChatColor.DARK_AQUA + "Villager career updated.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 2) {
            return VillagerHelper.getCareerNames().stream()
            .filter(completion -> completion.startsWith(args[1].toLowerCase()))
            .sorted().collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

}
