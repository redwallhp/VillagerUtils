package io.github.redwallhp.villagerutils;

import com.sk89q.worldguard.WorldGuard;
import io.github.redwallhp.villagerutils.commands.CommandManager;
import io.github.redwallhp.villagerutils.listeners.TradeListener;
import io.github.redwallhp.villagerutils.listeners.VillagerLogger;
import io.github.redwallhp.villagerutils.listeners.VillagerProtector;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;


public class VillagerUtils extends JavaPlugin {


    public static VillagerUtils instance;
    private HashMap<UUID, MerchantRecipe> tradeWorkspace;
    private Configuration configuration;
    private VillagerMeta villagerMeta;


    public void onEnable() {

        VillagerUtils.instance = this;
        tradeWorkspace = new HashMap<UUID, MerchantRecipe>();
        configuration = new Configuration();
        villagerMeta = new VillagerMeta();

        new CommandManager();
        new TradeListener();
        new VillagerLogger();
        new VillagerProtector();

    }


    public void onDisable() {
        villagerMeta.save();
    }


    public Configuration getConfiguration() {
        return configuration;
    }


    public VillagerMeta getVillagerMeta() {
        return villagerMeta;
    }


    public HashMap<UUID, MerchantRecipe> getTradeWorkspace() {
        return tradeWorkspace;
    }


    /**
     * Check if WorldGuard is installed
     */
    public boolean hasWG() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
        return plugin != null;
    }


}
