package io.github.redwallhp.villagerutils.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.WanderingTrader;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.helpers.VillagerHelper;

/**
 * An abstract subclass of {@link AbstractCommand} that adds some boilerplate
 * checking code to ensure that an AbstractVillager is actually a Villager (with
 * profession, type, level) rather than just a WanderingTrader.
 */
public abstract class VillagerSpecificAbstractCommand extends AbstractCommand {
    /**
     * Contstructor.
     * 
     * @param plugin the owning plugin.
     * @param permission the command permission.
     */
    public VillagerSpecificAbstractCommand(VillagerUtils plugin, String permission) {
        super(plugin, permission);
    }

    /**
     * Return the Villager (but not other AbstractVillager subtypes) in the
     * players's line of sight, and send appropriate error messages to the
     * player if they are looking at something else.
     * 
     * The intended purpose of this method is to return a Villager instance for
     * use in commands that perform Villager-specific actions where just any
     * AbstractVillager (such as WanderingTrader) would not be appropriate.
     * 
     * @param player the player.
     * @param wanderingTraderMessage the error message to send if the player is
     *        looking at a WanderingTrader.
     * @return the Villager (but not other AbstractVillager subtypes) in the
     *         players's line of sight, or null if the player is not a Villager.
     */
    public Villager getVillagerInLineOfSight(Player player, String wanderingTraderMessage) {
        AbstractVillager target = VillagerHelper.getAbstractVillagerInLineOfSight(player);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "You're not looking at a villager.");
            return null;
        }

        if (target instanceof WanderingTrader) {
            player.sendMessage(ChatColor.RED + wanderingTraderMessage);
            return null;
        }

        if (!(target instanceof Villager)) {
            player.sendMessage(ChatColor.RED + "The game added a new villager type. Remind a tech!");
            return null;
        }

        return (Villager) target;
    }
}