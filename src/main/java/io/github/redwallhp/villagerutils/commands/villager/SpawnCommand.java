package io.github.redwallhp.villagerutils.commands.villager;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import io.github.redwallhp.villagerutils.helpers.VillagerHelper;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.Set;


public class SpawnCommand extends AbstractCommand {


    public SpawnCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }


    public String getName() {
        return "spawn";
    }


    public String getUsage() {
        return "/villager spawn [profession]";
    }


    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot spawn villagers.");
            return false;
        }
        Player player = (Player) sender;
        Location loc = player.getLocation();
        Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
        if (args.length > 0 && VillagerHelper.getProfessionFromString(args[0]) != null) {
            villager.setProfession(VillagerHelper.getProfessionFromString(args[0]));
        }
        int x = villager.getLocation().getBlockX();
        int y = villager.getLocation().getBlockY();
        int z = villager.getLocation().getBlockZ();
        plugin.getLogger().info(String.format("%s spawned villager at %d, %d, %d", player.getName(), x, y ,z));
        player.sendMessage(ChatColor.DARK_AQUA + "Spawned villager.");
        return true;
    }


}
