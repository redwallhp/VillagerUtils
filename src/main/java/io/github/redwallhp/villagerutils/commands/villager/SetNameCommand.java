package io.github.redwallhp.villagerutils.commands.villager;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Player;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import io.github.redwallhp.villagerutils.helpers.VillagerHelper;

public class SetNameCommand extends AbstractCommand {

    public SetNameCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "name";
    }

    @Override
    public String getUsage() {
        return "/villager name <name>";
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
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "You must enter a name.");
            return false;
        }
        String name = ChatColor.translateAlternateColorCodes('&', String.join(" ", args));
        target.setCustomName(name);
        player.sendMessage(ChatColor.DARK_AQUA + "Villager name updated.");
        return true;
    }

}
