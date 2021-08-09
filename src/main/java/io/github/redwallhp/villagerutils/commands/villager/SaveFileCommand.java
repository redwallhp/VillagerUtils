package io.github.redwallhp.villagerutils.commands.villager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.MerchantRecipe;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.VillagerSpecificAbstractCommand;
import io.github.redwallhp.villagerutils.helpers.FileHelper;

public class SaveFileCommand extends VillagerSpecificAbstractCommand {

    public SaveFileCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "savefile";
    }

    @Override
    public String getUsage() {
        return "/villager savefile <filename>";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot edit villagers.");
            return false;
        }
        Player player = (Player) sender;

        Villager villager = getVillagerInLineOfSight(player, "This command doesn't work with wandering traders.");
        if (villager == null) {
            return false;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "You must specify a file name.");
            return false;
        }

        String fileName = args[0];
        if (!FileHelper.allowedVillagerSaveFileName(sender, fileName)) {
            return false;
        }

        YamlConfiguration config = new YamlConfiguration();
        config.set("name", villager.getCustomName());
        config.set("biome", villager.getVillagerType().toString());
        config.set("profession", villager.getProfession().toString());
        config.set("level", villager.getVillagerLevel());
        config.set("static", plugin.getVillagerMeta().STATIC_MERCHANTS.contains(villager.getUniqueId().toString()));

        List<Map<String, Object>> recipes = new ArrayList<>();
        for (MerchantRecipe recipe : villager.getRecipes()) {
            Map<String, Object> recipeMap = new HashMap<>();
            recipeMap.put("ingredients", recipe.getIngredients());
            recipeMap.put("max-uses", recipe.getMaxUses());
            recipeMap.put("price-multiplier", recipe.getPriceMultiplier());
            recipeMap.put("result", recipe.getResult());
            recipeMap.put("uses", recipe.getUses());
            recipeMap.put("villager-experience", recipe.getVillagerExperience());
            recipeMap.put("experience-reward", recipe.hasExperienceReward());
            recipes.add(recipeMap);
        }
        config.set("recipes", recipes);

        File file = new File(plugin.getSavedVillagersDirectory(), fileName);
        try {
            config.save(file);
            player.sendMessage(ChatColor.DARK_AQUA + "Villager trades saved to \"" + fileName + "\".");
        } catch (IOException ex) {
            player.sendMessage(ChatColor.RED + "Error saving file: " + ex.getMessage());
        }
        return true;
    }
}
