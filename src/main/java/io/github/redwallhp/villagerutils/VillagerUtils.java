package io.github.redwallhp.villagerutils;

import io.github.redwallhp.villagerutils.commands.CommandManager;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;


public class VillagerUtils extends JavaPlugin {


    public static VillagerUtils instance;
    private HashMap<UUID, MerchantRecipe> tradeWorkspace;
    private Configuration configuration;


    public void onEnable() {

        VillagerUtils.instance = this;
        tradeWorkspace = new HashMap<UUID, MerchantRecipe>();
        configuration = new Configuration();

        new CommandManager();
        new TradeEditorListener();
        new VillagerLogger();

    }


    public Configuration getConfiguration() {
        return configuration;
    }


    public HashMap<UUID, MerchantRecipe> getTradeWorkspace() {
        return tradeWorkspace;
    }


}
