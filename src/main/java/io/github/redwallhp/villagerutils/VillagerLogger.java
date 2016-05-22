package io.github.redwallhp.villagerutils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;


public class VillagerLogger implements Listener {


    private VillagerUtils plugin;


    public VillagerLogger() {
        plugin = VillagerUtils.instance;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        // do something if configured to do so
    }


}
