package io.github.redwallhp.villagerutils.commands.villager;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import io.github.redwallhp.villagerutils.helpers.VillagerHelper;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;


public class SetStaticCommand extends AbstractCommand {


    public SetStaticCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }


    public String getName() {
        return "static";
    }


    public String getUsage() {
        return "/villager static <boolean>";
    }


    public boolean action(CommandSender sender, String[] args) {
        if (!plugin.getServer().getOnlineMode()) {
            sender.sendMessage(ChatColor.RED + "This feature does not work with an offline mode server.");
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot spawn villagers.");
            return false;
        }
        Player player = (Player) sender;
        Villager target = VillagerHelper.getVillagerInLineOfSight(player);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "You're not looking at a villager.");
            return false;
        }
        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + getUsage());
            return false;
        }
        Boolean value = Boolean.parseBoolean(args[0]);
        if (value) {
            plugin.getVillagerMeta().STATIC_MERCHANTS.add(target.getUniqueId().toString());
            sender.sendMessage(ChatColor.DARK_AQUA + "This villager will not acquire its own trades.");
        } else {
            plugin.getVillagerMeta().STATIC_MERCHANTS.remove(target.getUniqueId().toString());
            sender.sendMessage(ChatColor.DARK_AQUA + "This villager will acquire its own trades");
        }
        plugin.getVillagerMeta().save();
        return true;
    }


}
