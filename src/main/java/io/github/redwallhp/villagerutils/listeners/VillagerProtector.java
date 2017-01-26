package io.github.redwallhp.villagerutils.listeners;


import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.helpers.WorldGuardHelper;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VillagerProtector implements Listener {


    private VillagerUtils plugin;


    public VillagerProtector() {
        plugin = VillagerUtils.instance;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    /**
     * Handle WorldGuard based protection from players
     */
    @EventHandler
    public void onEntityDamageByPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Villager)) return;
        if (!plugin.getConfiguration().WORLDGUARD_PROTECT) return;
        if (!plugin.hasWG()) return;
        Player player;
        if (event.getDamager() instanceof Player) {
            player = (Player) event.getDamager();
        }
        else if (event.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getDamager();
            if (projectile.getShooter() instanceof Player) {
                player = (Player) projectile.getShooter();
            } else {
                return;
            }
        }
        else {
            return;
        }
        Villager villager = (Villager) event.getEntity();
        boolean isProhibited = WorldGuardHelper.isVillagerViolenceProhibited(player, villager);
        if (isProhibited) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You don't have permission to harm villagers in this region.");
        }
    }


    /**
     * Handle WorldGuard based protection from players' potions
     */
    @EventHandler
    public void onAreaEffectApply(AreaEffectCloudApplyEvent event) {
        if (!plugin.getConfiguration().WORLDGUARD_PROTECT) return;
        if (!(event.getEntity().getSource() instanceof Player)) return;
        if (!plugin.hasWG()) return;
        PotionType type = event.getEntity().getBasePotionData().getType();
        List<PotionType> blacklist = new ArrayList<PotionType>();
        blacklist.add(PotionType.INSTANT_DAMAGE);
        blacklist.add(PotionType.POISON);
        blacklist.add(PotionType.SLOWNESS);
        blacklist.add(PotionType.WEAKNESS);
        Iterator<LivingEntity> iterator = event.getAffectedEntities().iterator();
        Player player = (Player) event.getEntity().getSource();
        while (iterator.hasNext()) {
            LivingEntity ent = iterator.next();
            if (ent instanceof Villager && blacklist.contains(type)) {
                if (WorldGuardHelper.isVillagerViolenceProhibited(player, (Villager) ent)) {
                    iterator.remove();
                }
            }
        }
    }


    /**
     * Prevent blacklisted mob types from harming villagers.
     * e.g. EVOKER, EVOKER_FANGS, VEX, VINDICATOR
     */
    @EventHandler
    public void onEntityDamageByMob(EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Villager)) return;
        if (plugin.getConfiguration().PROTECT_FROM_MOBS.size() < 1) return;
        boolean isProhibited = false;

        if (plugin.getConfiguration().PROTECT_FROM_MOBS.contains(event.getDamager().getType())) {
            isProhibited = true;
        }
        else if (event.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getDamager();
            Entity shooter = (Entity) projectile.getShooter();
            if (!(shooter instanceof Player)) {
                if (plugin.getConfiguration().PROTECT_FROM_MOBS.contains(shooter.getType())) {
                    isProhibited = true;
                }
            }
        }

        if (isProhibited) {
            event.setCancelled(true);
        }

    }


}
