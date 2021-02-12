package io.github.redwallhp.villagerutils.helpers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import io.github.redwallhp.villagerutils.VillagerUtils;

/**
 * Isolated WorldGuard functions, so WG can be a soft dependency. Always use the
 * hasWG() method in the VillagerUtils class to check if WG is running before
 * calling anything in this class.
 */
public class WorldGuardHelper {

    public static boolean isVillagerViolenceProhibited(Player player, AbstractVillager villager) {
        if (!VillagerUtils.instance.getConfiguration().WORLDGUARD_PROTECT) {
            return false;
        }
        if (getWG() == null) {
            return false;
        }
        return !canBuild(player, villager.getLocation());
    }

    public static boolean canBuild(Player player, Location location) {
        if (getWG() == null)
            return true;
        RegionQuery query = getWG().getPlatform().getRegionContainer().createQuery();
        com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(location);
        LocalPlayer lp = WorldGuardPlugin.inst().wrapPlayer(player);
        if (getWG().getPlatform().getSessionManager().hasBypass(lp, BukkitAdapter.adapt(location.getWorld()))) {
            return true; // has bypass
        } else {
            return query.testState(loc, lp, Flags.BUILD);
        }
    }

    public static WorldGuard getWG() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null) {
            return null;
        }
        return WorldGuard.getInstance();
    }

}
