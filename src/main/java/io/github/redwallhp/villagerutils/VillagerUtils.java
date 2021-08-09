package io.github.redwallhp.villagerutils;

import java.io.File;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.redwallhp.villagerutils.commands.CommandManager;
import io.github.redwallhp.villagerutils.listeners.TradeListener;
import io.github.redwallhp.villagerutils.listeners.VillagerLogger;
import io.github.redwallhp.villagerutils.listeners.VillagerProtector;

public class VillagerUtils extends JavaPlugin {

    public static VillagerUtils instance;
    private WorkspaceManager workspaceManager;
    private Configuration configuration;
    private VillagerMeta villagerMeta;

    @Override
    public void onEnable() {

        VillagerUtils.instance = this;
        workspaceManager = new WorkspaceManager();
        configuration = new Configuration();
        villagerMeta = new VillagerMeta();

        getSavedVillagersDirectory().mkdir();

        new CommandManager();
        new TradeListener();
        new VillagerLogger();
        new VillagerProtector();

    }

    @Override
    public void onDisable() {
        villagerMeta.save();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public VillagerMeta getVillagerMeta() {
        return villagerMeta;
    }

    public WorkspaceManager getWorkspaceManager() {
        return workspaceManager;
    }

    /**
     * Check if WorldGuard is installed
     */
    public boolean hasWG() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
        return plugin != null;
    }

    /**
     * Return the directory where {@code /villager save} stores files.
     *
     * @return the directory where {@code /villager save} stores files.
     */
    public File getSavedVillagersDirectory() {
        return new File(getDataFolder(), "saved-villagers");
    }
}
