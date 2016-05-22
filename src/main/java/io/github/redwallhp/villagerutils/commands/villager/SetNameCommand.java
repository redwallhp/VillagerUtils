package io.github.redwallhp.villagerutils.commands.villager;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import io.github.redwallhp.villagerutils.helpers.StringHelper;
import io.github.redwallhp.villagerutils.helpers.VillagerHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;


public class SetNameCommand extends AbstractCommand {


    public SetNameCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }


    public String getName() {
        return "name";
    }


    public String getUsage() {
        return "/villager name <name>";
    }


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
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "You must enter a name.");
            return false;
        }
        target.setCustomName(StringHelper.joinArray(" ", args));
        player.sendMessage(ChatColor.DARK_AQUA + "Villager name updated.");
        return true;
    }


}
