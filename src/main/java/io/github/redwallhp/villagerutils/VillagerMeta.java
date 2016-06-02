package io.github.redwallhp.villagerutils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class VillagerMeta {


    private VillagerUtils plugin;
    private File file;
    private FileConfiguration yaml;


    public List<String> STATIC_MERCHANTS;


    public VillagerMeta() {
        plugin = VillagerUtils.instance;
        file = new File(plugin.getDataFolder(), "villager_meta.yml");
        load();
    }


    public void load() {
        if (file.exists()) {
            yaml = YamlConfiguration.loadConfiguration(file);
        } else {
            yaml = new YamlConfiguration();
            save();
        }
        STATIC_MERCHANTS = yaml.getStringList("static_merchants");
    }


    public void save() {
        yaml.set("static_merchants", STATIC_MERCHANTS);
        try {
            yaml.save(file);
        } catch (IOException ex) {
            plugin.getLogger().warning("Error saving villager meta.");
        }
    }


}
