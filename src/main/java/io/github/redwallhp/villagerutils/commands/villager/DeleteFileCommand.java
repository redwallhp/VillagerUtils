package io.github.redwallhp.villagerutils.commands.villager;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import io.github.redwallhp.villagerutils.helpers.FileHelper;

public class DeleteFileCommand extends AbstractCommand {

    public DeleteFileCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "deletefile";
    }

    @Override
    public String getUsage() {
        return "/villager deletefile <filename>";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Invalid arguments. Usage: " + getUsage());
            return false;
        }

        String fileName = args[0];
        if (!FileHelper.allowedVillagerSaveFileName(sender, fileName)) {
            return false;
        }

        File file = new File(plugin.getSavedVillagersDirectory(), fileName);
        if (file.exists()) {
            if (file.delete()) {
                sender.sendMessage(ChatColor.DARK_AQUA + "The file \"" + fileName + "\" was deleted.");
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "The file could not be deleted!");
                return false;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "There is no file named \"" + fileName + "\".");
            return false;
        }
    }
}
