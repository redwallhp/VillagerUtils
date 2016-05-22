package io.github.redwallhp.villagerutils;


public class Configuration {


    private VillagerUtils plugin;


    public boolean LOGGING;


    public Configuration() {
        plugin = VillagerUtils.instance;
        plugin.saveDefaultConfig();
        load();
    }


    public void load() {
        plugin.reloadConfig();
        LOGGING = plugin.getConfig().getBoolean("logging", true);
    }


}
