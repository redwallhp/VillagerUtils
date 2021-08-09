# VillagerUtils

Tools for managing villagers on Spigot servers.

![Editing a villager trade](http://i.imgur.com/pBWpb0d.gif)

* Commands to edit villager trades, professions, biomes and experience levels.

* Commands to save a villager to a file, spawn a villager clone from a file
  and to list and delete villager save files.

* Support for editing wandering trader traders. 
  (See the Wandering Traders section, below, for caveats.)

* Log villager deaths with enough information to reasonably recreate them.

* Protect villagers from grief by only allowing WorldGuard region members to harm them.

* Prevent villagers from being renamed by players who don't have build permission
  at the villager's location.

* Protect villagers from being hurt by blacklisted mob types. e.g. `protect_from_mobs: [Evoker, Evoker_Fangs, Vex, Vindicator]`.

Note that novice villagers select their profession to suit a nearby workstation.
Until a villager has selected a workstation, his skin will not reflect the
villager's current profession. Setting a villager to master level (5) does not
automatically create trades appropriate to their profession.


## Wandering Traders

VillagerUtils can edit the trades of wandering traders. They don't have a
profession, level or biome variants, and cannot acquire new trades, so the
`/villager profession`, `/villager level`, `/villager biome` and 
`/villager static` sub-commands are not applicable to wandering traders and
will show an appropriate error message.

Wandering traders automatically despawn after a while, despite having
`PersistenceRequired: 1b` in their NBT data. They should be configured to
delay this as long as possible by setting their `DespawnDelay` to the
largest possible value of a signed 32-bit integer 2,147,483,647 (2<sup>31</sup> - 1).
But if you have trouble remembering that exact value, 2 billion is close enough.
For example, to prevent the nearest wandering trader from despawning for about
3 years, use:

    /data merge entity @e[type=wandering_trader,sort=nearest,limit=1] {DespawnDelay: 2000000000}

Wandering traders drink an invisibility potion at night. To prevent that, you
can disable their AI using the `Bukkit.Aware` NBT tag. If you disable AI, you
will probably also need to set the direction they face. For example, to 
configure the nearest wandering trader to face north, use:

    /data merge entity @e[type=wandering_trader,sort=nearest,limit=1] {DespawnDelay: 2000000000, Bukkit.Aware: 0b, Rotation: [180f, 0f]}


## Commands
### /villager

The `/villager` command modifies the attributes and trades of the villager 
currently in your crosshairs. The villager must be within 5 blocks or they
will not be "seen".

* `/villager spawn [<biome>] [<profession>] [<level>]` - Convenience command to spawn
  a new villager at your location, optionally setting biome styling, profession
  and level (1-5).

* `/villager name <name>` - Set the villager's name.

* `/villager biome <biome>` - Set the villager's biome, which affects its appearance.

* `/villager profession <profession>` - Set the villager's profession to any valid option.

* `/villager level <level>` - Set the villager's level from 1 to 5, inclusive.

* `/villager cleartrades` - Clear all trades from a villager. You won't even be able to open the trade UI until you add some new ones.

* `/villager static <boolean>` — Set whether the villager will automatically acquire trades. Setting this to false is useful for "server merchants."

* `/villager listtrades` - List all trades offered by the villager you are looking at. Trades are numbered from 1 to the total number of trades.

* `/villager gettrade <position>` - Copy the trade at the specified position to the workspace. (See `/vtrade`.)

* `/villager settrade <position>` - Replace the trade at the specified position with the one on the workspace.

* `/villager addtrade [<position>]` - Add the trade in your workspace to the villager. If a position is specified, the trade is inserted before the existing trade at that position and all subsequent trades are moved down the list. Position 1 signifies the first trade. To add the trade after the last trade, use a position number that is greater than the number of trades, or simply omit the position.

* `/villager removetrade <position>` - Remove the trade at the specified position, from 1 to the number of trades.

* `/villager savefile <filename>` - Save the currently looked-at villager to the specified file. Valid file names can consist only of letters, digits, underscores and hyphens.

* `/villager spawnfile <filename>` - Spawn a clone of the villager described by the specified file at the player's current location.

* `/villager listfiles` - Show a list of all villager save file names.

* `/villager deletefile <filename>` - Delete the specified villager save file.


### /vtrade

The `/vtrade` command alters the properties of the trade currently on your
"workspace". You can copy a villager's trade to the workspace with
`/villager gettrade` and paste a trade to the same or a different villager
with `/villager settrade` or `/villager addtrade`.

* `/vtrade new [<maxuses>|max]` - Create a new, blank trade that gives experience. If `<maxuses>` is specified, it is the maximum number of times the trade can be used before it locked. Otherwise the number is randomly selected between 2 and 12, inclusive. The word `max` can be used to signify the largest integer (about 2 billion).

* `/vtrade items` - Open an inventory UI to insert 1-2 items for the "cost" and one item for the result of the trade.

* `/vtrade maxuses <int>|max` - Set how many times this trade can be used before it locks, requiring a player to refresh it by using other trades. A blank trade will default to a random number from 2-12, to mimic vanilla distribution. The word `max` can be used to signify the largest integer (about 2 billion).

* `/vtrade givesxp <given>` - Set whether this trade will yield experience or not. The <given> argument must be either `true` or `false`.

* `/vtrade info` - Show a description of the trade currently on your workspace.


## Permissions

* `villagerutils.editvillager` - Allow access to villager-editing commands.

