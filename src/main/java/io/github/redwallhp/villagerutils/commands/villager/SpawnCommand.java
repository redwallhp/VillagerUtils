package io.github.redwallhp.villagerutils.commands.villager;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import io.github.redwallhp.villagerutils.VillagerUtils;
import io.github.redwallhp.villagerutils.commands.AbstractCommand;
import io.github.redwallhp.villagerutils.helpers.VillagerHelper;

public class SpawnCommand extends AbstractCommand implements TabCompleter {

    public SpawnCommand(VillagerUtils plugin) {
        super(plugin, "villagerutils.editvillager");
    }

    @Override
    public String getName() {
        return "spawn";
    }

    @Override
    public String getUsage() {
        return "/villager spawn [<biome>] [<profession>] [<level>]";
    }

    @Override
    public boolean action(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot spawn villagers.");
            return false;
        }

        if (args.length > 3) {
            sender.sendMessage(ChatColor.RED + "Invalid arguments. Usage: " + getUsage());
            return false;
        }

        Villager.Type biome = null;
        if (args.length >= 1) {
            biome = VillagerHelper.getVillagerTypeFromString(args[0]);
            if (biome == null) {
                sender.sendMessage(ChatColor.RED + "That's not a valid villager biome.");
                return false;
            }
        }

        Villager.Profession profession = null;
        if (args.length >= 2) {
            profession = VillagerHelper.getProfessionFromString(args[1]);
            if (profession == null) {
                sender.sendMessage(ChatColor.RED + "That's not a valid profession.");
                return false;
            }
        }

        Integer level = null;
        if (args.length >= 3) {
            try {
                level = Integer.parseInt(args[2]);
            } catch (IllegalArgumentException ex) {
            }
            if (level == null || level < 1 || level > 5) {
                sender.sendMessage(ChatColor.RED + "The level must be between 1 and 5, inclusive.");
                return false;
            }
        }

        Player player = (Player) sender;
        Location loc = player.getLocation();
        Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
        if (biome != null) {
            villager.setVillagerType(biome);
        }
        if (profession != null) {
            villager.setProfession(profession);
        }
        if (level != null) {
            villager.setVillagerLevel(level);
        }

        String description = villager.getVillagerType().name().toLowerCase() + " villager, profession " +
                             villager.getProfession().name().toLowerCase() + ", level " + villager.getVillagerLevel();
        plugin.getLogger().info(String.format("%s spawned %s at %d, %d, %d", player.getName(), description,
                                              loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        player.sendMessage(ChatColor.DARK_AQUA + "Spawned " + description + ".");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 2) {
            return VillagerHelper.getVillagerTypeNames().stream()
            .filter(completion -> completion.startsWith(args[1].toLowerCase()))
            .sorted().collect(Collectors.toList());
        } else if (args.length == 3) {
            return VillagerHelper.getProfessionNames().stream()
            .filter(completion -> completion.startsWith(args[2].toLowerCase()))
            .sorted().collect(Collectors.toList());
        } else if (args.length == 4) {
            return IntStream.rangeClosed(1, 5).mapToObj(Integer::toString)
            .filter(completion -> completion.startsWith(args[3]))
            .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

}
