package io.github.redwallhp.villagerutils.helpers;


import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import io.github.redwallhp.villagerutils.VillagerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.Plugin;

/**
 * Isolated WorldGuard functions, so WG can be a soft dependency.
 * Always use the hasWG() method in the VillagerUtils class to check if WG is running
 * before calling anything in this class.
 */
public class WorldGuardHelper {


    public static boolean isVillagerViolenceProhibited(Player player, Villager villager) {
        if (!VillagerUtils.instance.getConfiguration().WORLDGUARD_PROTECT) return false;
        if (getWG() == null) return false;
        return !getWG().canBuild(player, villager.getLocation());
    }


    public static WorldGuardPlugin getWG() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin) plugin;
    }


}
