package io.github.redwallhp.villagerutils.commands.villager;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import io.github.redwallhp.villagerutils.helpers.VillagerHelper;

public class SetProfessionCommand extends AbstractCommand {

    public SetProfessionCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "profession";
    }

    @Override
    public String getUsage() {
        return "/villager profession <name>";
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
        if (args.length == 0 || VillagerHelper.getProfessionFromString(args[0]) == null) {
            player.sendMessage(ChatColor.RED + "You must specify a profession.");
            player.sendMessage(ChatColor.GRAY + "Valid professions: blacksmitch, butcher, farmer, librarian, priest");
            return false;
        }
        target.setProfession(VillagerHelper.getProfessionFromString(args[0]));
        player.sendMessage(ChatColor.DARK_AQUA + "Villager profession updated.");
        return true;
    }

}
