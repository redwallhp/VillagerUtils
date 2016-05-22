package io.github.redwallhp.villagerutils;

import io.github.redwallhp.villagerutils.commands.CommandManager;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;


public class VillagerUtils extends JavaPlugin {


    public static VillagerUtils instance;
    private HashMap<UUID, MerchantRecipe> tradeWorkspace;


    public void onEnable() {

        VillagerUtils.instance = this;
        tradeWorkspace = new HashMap<UUID, MerchantRecipe>();

        new CommandManager();
        new TradeEditorListener();

    }


    public HashMap<UUID, MerchantRecipe> getTradeWorkspace() {
        return tradeWorkspace;
    }


}
