package io.github.redwallhp.villagerutils.commands.villager;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;

public class ListFilesCommand extends AbstractCommand {

    public ListFilesCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "listfiles";
    }

    @Override
    public String getUsage() {
        return "/villager listfiles";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        if (args.length != 0) {
            sender.sendMessage(ChatColor.RED + "Invalid arguments. Usage: " + getUsage());
            return false;
        }

        List<String> files = Stream.of(plugin.getSavedVillagersDirectory().listFiles())
            .filter(file -> !file.isDirectory())
            .map(File::getName)
            .collect(Collectors.toList());
        if (files.isEmpty()) {
            sender.sendMessage(ChatColor.DARK_AQUA + "There are no villager save files.");
        } else {
            sender.sendMessage(ChatColor.DARK_AQUA + "Villager save files: " + String.join(", ", files));
        }
        return true;
    }
}
