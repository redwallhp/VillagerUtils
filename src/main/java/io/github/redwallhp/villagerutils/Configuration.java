package io.github.redwallhp.villagerutils;


public class Configuration {


    private VillagerUtils plugin;


    public boolean LOGGING;
    public boolean WORLDGUARD_PROTECT;


    public Configuration() {
        plugin = VillagerUtils.instance;
        plugin.saveDefaultConfig();
        load();
    }


    public void load() {
        plugin.reloadConfig();
        LOGGING = plugin.getConfig().getBoolean("logging", true);
        WORLDGUARD_PROTECT = plugin.getConfig().getBoolean("worldguard_protect", false);
    }


}
