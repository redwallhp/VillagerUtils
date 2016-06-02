package io.github.redwallhp.villagerutils.listeners;

import io.github.redwallhp.villagerutils.VillagerUtils;
import org.bukkit.Location;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;


public class VillagerLogger implements Listener {


    private VillagerUtils plugin;


    public VillagerLogger() {
        plugin = VillagerUtils.instance;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!plugin.getConfiguration().LOGGING) return;
        if (event.getEntity() instanceof Villager) {
            logDeath((Villager) event.getEntity());
        }
    }


    private void logDeath(Villager villager) {

        StringBuilder sb = new StringBuilder("[VILLAGER DEATH] ");

        Location l = villager.getLocation();
        sb.append(String.format("Location: %s %d %d %d | ", l.getWorld().getName(), l.getBlockX(), l.getBlockY(), l.getBlockZ()));

        if (villager.getKiller() != null) {
            sb.append(String.format("Killer: %s (%s) | ", villager.getKiller().getName(), villager.getKiller().getUniqueId()));
        }

        String name = villager.getCustomName();
        if (name != null && !name.equals("")) {
            sb.append(String.format("Name: %s | ", name));
        }

        sb.append(String.format("Profession: %s | ", villager.getProfession()));
        sb.append(String.format("Riches: %d | ", villager.getRiches()));
        sb.append("Recipes: ");

        for (MerchantRecipe recipe : villager.getRecipes()) {
            sb.append("{");
            sb.append(getRecipeString(recipe));
            sb.append("}");
            if (villager.getRecipes().indexOf(recipe) < villager.getRecipes().size() - 1) {
                sb.append(", ");
            }
        }

        plugin.getLogger().info(sb.toString());

    }


    private String getRecipeString(MerchantRecipe recipe) {

        StringBuilder sb = new StringBuilder("");

        sb.append(String.format("[Result] %s ", recipe.getResult().toString()));

        sb.append("[Cost] ");
        for (ItemStack item : recipe.getIngredients()) {
            sb.append(item.toString());
            if (recipe.getIngredients().indexOf(item) < recipe.getIngredients().size() - 1) {
                sb.append(", ");
            } else {
                sb.append(" ");
            }
        }

        sb.append(String.format("[Max Uses] %d ", recipe.getMaxUses()));
        sb.append(String.format("[XP Reward] %b", recipe.hasExperienceReward()));

        return sb.toString();

    }


}
