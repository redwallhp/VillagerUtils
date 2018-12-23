package io.github.redwallhp.villagerutils.listeners;

import io.github.redwallhp.villagerutils.VillagerUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;


public class TradeListener implements Listener {


    private VillagerUtils plugin;


    public TradeListener() {
        plugin = VillagerUtils.instance;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    /**
     * Update the trade recipe being edited when the trade editor inventory is closed
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (!event.getInventory().getName().equals("Edit Villager Trade")) return;
        if (!plugin.getTradeWorkspace().containsKey(player.getUniqueId())) return;
        MerchantRecipe oldRecipe = plugin.getTradeWorkspace().get(player.getUniqueId());
        MerchantRecipe newRecipe = new MerchantRecipe(event.getInventory().getItem(8), oldRecipe.getUses());
        List<ItemStack> ingredients = new ArrayList<ItemStack>();
        for (int i = 0; i <= 1; i++) {
            ItemStack item = event.getInventory().getItem(i);
            if (item != null) ingredients.add(item);
        }
        newRecipe.setIngredients(ingredients);
        plugin.getTradeWorkspace().put(player.getUniqueId(), newRecipe);
        player.sendMessage(ChatColor.DARK_AQUA + "Trade items updated.");
    }


    /**
     * Block glass panes from being removed from the trade editor UI
     */
    @EventHandler
    public void onInventoryMoveItem(InventoryClickEvent event) {
        if (event.getClickedInventory() == null || event.getClickedInventory().getName() == null) return;
        if (!event.getClickedInventory().getName().equals("Edit Villager Trade")) return;
        if (event.getCurrentItem() != null && event.getCurrentItem().getType().equals(Material.WHITE_STAINED_GLASS_PANE)) {
            event.setCancelled(true);
        }
    }


    /**
     * Stop villagers from acquiring new trades if the villager is a server merchant
     */
    @EventHandler
    public void onVillagerAcquireTrade(VillagerAcquireTradeEvent event) {
        String villagerId = event.getEntity().getUniqueId().toString();
        if (plugin.getVillagerMeta().STATIC_MERCHANTS.contains(villagerId)) {
            event.setCancelled(true);
        }
    }


    /**
     * Clean up after server merchant villagers so we don't have an ever-expanding UUID list
     */
    @EventHandler
    public void onVillagerDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Villager) {
            String villagerId = event.getEntity().getUniqueId().toString();
            if (plugin.getVillagerMeta().STATIC_MERCHANTS.contains(villagerId)) {
                plugin.getVillagerMeta().STATIC_MERCHANTS.remove(villagerId);
                plugin.getVillagerMeta().save();
            }
        }
    }


}
