package io.github.redwallhp.villagerutils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.MerchantRecipe;

/**
 * Manages the association between players and their trade workspaces.
 */
public class WorkspaceManager {
    private final HashMap<UUID, MerchantRecipe> uuidToTrade = new HashMap<>();

    public boolean hasWorkspace(Player player) {
        return uuidToTrade.containsKey(player.getUniqueId());
    }

    public MerchantRecipe getWorkspace(Player player) {
        return uuidToTrade.get(player.getUniqueId());
    }

    public void setWorkspace(Player player, MerchantRecipe recipe) {
        uuidToTrade.put(player.getUniqueId(), recipe);
    }

}
