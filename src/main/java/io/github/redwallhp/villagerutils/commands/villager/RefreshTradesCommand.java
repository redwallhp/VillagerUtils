package io.github.redwallhp.villagerutils.commands.villager;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.MerchantRecipe;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import io.github.redwallhp.villagerutils.helpers.VillagerHelper;

public class RefreshTradesCommand extends AbstractCommand {

    public RefreshTradesCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "refreshtrades";
    }

    @Override
    public String getUsage() {
        return "/villager refreshtrades";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }

        Player player = (Player) sender;
        AbstractVillager target = VillagerHelper.getAbstractVillagerInLineOfSight(player);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "You're not looking at a villager.");
            return false;
        }

        List<MerchantRecipe> recipes = target.getRecipes();
        for (MerchantRecipe recipe : recipes) {
            recipe.setUses(0);
        }
        target.setRecipes(recipes);

        Location particleLoc = target.getLocation().add(0, 1.5, 0);
        particleLoc.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, particleLoc, 10, 0.5, 0.5, 0.5);
        player.sendMessage(ChatColor.DARK_AQUA + "Villager trades refreshed.");
        return true;
    }

}
