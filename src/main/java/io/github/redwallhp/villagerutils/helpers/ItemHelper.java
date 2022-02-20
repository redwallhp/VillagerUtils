package io.github.redwallhp.villagerutils.helpers;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;

/**
 * Utility functions for ItemStacks.
 */
public class ItemHelper {
    /**
     * Return a string describing a dropped item stack.
     *
     * The string contains the material type name, data value and amount, as
     * well as a list of enchantments. It is used in methods that log drops.
     *
     * @param item the droppped item stack.
     * @return a string describing a dropped item stack.
     */
    public static String getItemDescription(ItemStack item) {
        if (item == null) {
            return "null";
        }

        StringBuilder description = new StringBuilder();
        if (item.getAmount() != 1) {
            description.append(item.getAmount()).append('x');
        }

        description.append(item.getType().name());
        if (item.getDurability() != 0) {
            description.append(':').append(item.getDurability());
        }

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (meta instanceof SkullMeta) {
                SkullMeta skullMeta = (SkullMeta) meta;
                if (skullMeta.getOwner() != null) {
                    description.append(" of \"").append(skullMeta.getOwner()).append("\"");
                }
            } else if (meta instanceof SpawnEggMeta) {
                SpawnEggMeta eggMeta = (SpawnEggMeta) meta;
                description.append(" of ").append(eggMeta.getSpawnedType());
            } else if (meta instanceof EnchantmentStorageMeta) {
                EnchantmentStorageMeta bookEnchants = (EnchantmentStorageMeta) meta;
                description.append(" with").append(enchantsToString(bookEnchants.getStoredEnchants()));
            } else if (meta instanceof BookMeta) {
                BookMeta bookMeta = (BookMeta) meta;
                if (bookMeta.getTitle() != null) {
                    description.append(" titled \"").append(bookMeta.getTitle()).append("\"");
                }
                if (bookMeta.getAuthor() != null) {
                    description.append(" by ").append(bookMeta.getAuthor());
                }
            } else if (meta instanceof PotionMeta) {
                PotionMeta potionMeta = (PotionMeta) meta;
                description.append(" of ");
                PotionData data = potionMeta.getBasePotionData();
                description.append(data.getType());
                if (data.isExtended()) {
                    description.append(" extended");
                }
                if (data.isUpgraded()) {
                    description.append(" upgraded");
                }

                List<PotionEffect> effects = potionMeta.getCustomEffects();
                if (effects != null && !effects.isEmpty()) {
                    description.append(" with ");
                    String sep = "";
                    for (PotionEffect effect : potionMeta.getCustomEffects()) {
                        description.append(sep).append(potionToString(effect));
                        sep = "+";
                    }
                }
            }

            if (meta.getDisplayName() != null && !meta.getDisplayName().isEmpty()) {
                description.append(" named \"").append(meta.getDisplayName()).append(ChatColor.WHITE).append("\"");
            }

            List<String> lore = meta.getLore();
            if (lore != null && !lore.isEmpty()) {
                description.append(" lore \"").append(String.join("|", lore)).append(ChatColor.WHITE).append("\"");
            }
        }

        description.append(enchantsToString(item.getEnchantments()));
        return description.toString();
    }

    /**
     * Return the string description of a potion effect.
     *
     * @param effect the effect.
     * @return the description.
     */
    public static String potionToString(PotionEffect effect) {
        StringBuilder description = new StringBuilder();
        description.append(effect.getType().getName()).append("/");
        description.append(effect.getAmplifier() + 1).append("/");
        description.append(effect.getDuration() / 20.0).append('s');
        return description.toString();
    }

    /**
     * Return the string description of a set of enchantments.
     *
     * @param enchants map from enchantment type to level, from the Bukkit API.
     * @return the description.
     */
    public static String enchantsToString(Map<Enchantment, Integer> enchants) {
        StringBuilder description = new StringBuilder();
        if (enchants.size() > 0) {
            description.append(" (");
            String sep = "";
            for (Entry<Enchantment, Integer> entry : enchants.entrySet()) {
                description.append(sep).append(entry.getKey().getName()).append(':').append(entry.getValue());
                sep = ",";
            }
            description.append(')');
        }
        return description.toString();
    }

}
