package io.github.redwallhp.villagerutils.commands.villager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Villager.Type;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;

public class SpawnFileCommand extends AbstractCommand {

    public SpawnFileCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "spawnfile";
    }

    @Override
    public String getUsage() {
        return "/villager spawnfile <filename>";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }
        Player player = (Player) sender;
        Location loc = player.getLocation();

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "You must specify a file name.");
            return false;
        }

        String fileName = args[0];
        if (!Pattern.matches("(\\w|-)+", fileName)) {
            sender.sendMessage(ChatColor.RED + "The file name can only contain letters, digits, underscores and hyphens.");
            return false;
        }

        File file = new File(plugin.getSavedVillagersDirectory(), fileName);
        if (!file.canRead()) {
            sender.sendMessage(ChatColor.RED + "The file \"" + fileName + "\" cannot be read.");
            return false;
        }

        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException ex) {
            sender.sendMessage(ChatColor.RED + "Error loading \"" + fileName + "\": " + ex.getMessage());
            return false;
        }

        Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
        villager.setCustomName(config.getString("name"));
        try {
            String setting = config.getString("biome", "PLAINS").toUpperCase();
            villager.setVillagerType(Type.valueOf(setting));
        } catch (IllegalArgumentException ex) {
            sender.sendMessage(ChatColor.RED + "Invalid biome in save file.");
            return false;
        }
        try {
            String setting = config.getString("profession", "ARMORER").toUpperCase();
            villager.setProfession(Profession.valueOf(setting));
        } catch (IllegalArgumentException ex) {
            sender.sendMessage(ChatColor.RED + "Invalid biome in save file.");
            return false;
        }
        villager.setVillagerLevel(config.getInt("level"));
        if (config.getBoolean("static")) {
            plugin.getVillagerMeta().STATIC_MERCHANTS.add(villager.getUniqueId().toString());
            plugin.getVillagerMeta().save();
        }

        List<MerchantRecipe> recipes = new ArrayList<>();
        for (Map<?, ?> untypedMap : config.getMapList("recipes")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> recipeMap = (Map<String, Object>) untypedMap;
            List<ItemStack> ingredients = (List<ItemStack>) recipeMap.get("ingredients");
            int maxUses = (Integer) recipeMap.getOrDefault("max-uses", 1);
            double priceMultiplier = (Double) recipeMap.getOrDefault("price-multiplier", 0.0);
            ItemStack result = (ItemStack) recipeMap.get("result");
            int uses = (Integer) recipeMap.getOrDefault("uses", 0);
            int villagerExperience = (Integer) recipeMap.getOrDefault("villager-experience", 0);
            boolean experienceReward = (Boolean) recipeMap.getOrDefault("experience-reward", false);

            MerchantRecipe recipe = new MerchantRecipe(result, uses, maxUses, experienceReward, villagerExperience, (float) priceMultiplier);
            recipe.setIngredients(ingredients);
            recipes.add(recipe);
        }
        villager.setRecipes(recipes);

        String description = villager.getVillagerType().name().toLowerCase() + " villager, profession " +
                             villager.getProfession().name().toLowerCase() + ", level " + villager.getVillagerLevel();
        plugin.getLogger().info(String.format("%s spawned %s at %d, %d, %d", player.getName(), description,
                                              loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        player.sendMessage(ChatColor.DARK_AQUA + "Spawned " + description + ".");
        return true;
    }
}
