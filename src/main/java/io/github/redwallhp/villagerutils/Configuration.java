package io.github.redwallhp.villagerutils;


import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class Configuration {


    private VillagerUtils plugin;


    public boolean LOGGING;
    public boolean WORLDGUARD_PROTECT;
    public List<EntityType> PROTECT_FROM_MOBS;


    public Configuration() {
        plugin = VillagerUtils.instance;
        plugin.saveDefaultConfig();
        load();
    }


    public void load() {
        plugin.reloadConfig();
        LOGGING = plugin.getConfig().getBoolean("logging", true);
        WORLDGUARD_PROTECT = plugin.getConfig().getBoolean("worldguard_protect", false);
        loadMobProtections();
    }


    private void loadMobProtections() {
        PROTECT_FROM_MOBS = new ArrayList<EntityType>();
        for (String mob : plugin.getConfig().getStringList("protect_from_mobs")) {
            try {
                EntityType type = EntityType.valueOf(mob.toUpperCase());
                PROTECT_FROM_MOBS.add(type);
            } catch (IllegalArgumentException ex) {
                plugin.getLogger().warning(String.format("\"%s\" is not a valid entity type.", mob));
            }
        }
    }


}
