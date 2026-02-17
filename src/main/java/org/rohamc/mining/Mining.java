package org.rohamc.mining;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Mining implements Listener {

    private final JavaPlugin plugin;
    private final Set<UUID> activePlayers = new HashSet<>();

    public Mining(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public Set<UUID> getActivePlayers() { return activePlayers; }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!activePlayers.contains(player.getUniqueId())) return;

        Block block = event.getBlock();
        Material type = block.getType();

        if (!isValidOre(type)) return;

        event.setCancelled(true); // cancel default drop

        Set<Block> vein = new HashSet<>();
        findVein(block, type, vein, 50); // max 50 blocks per action

        for (Block b : vein) {
            b.setType(Material.AIR);
            ItemStack drop = getSmeltedOrRaw(b);

            var notStored = player.getInventory().addItem(drop);
            for (ItemStack leftover : notStored.values()) {
                b.getWorld().dropItemNaturally(b.getLocation(), leftover);
            }
        }
    }

    private boolean isValidOre(Material type) {
        return type.name().endsWith("_ORE") || type == Material.ANCIENT_DEBRIS;
    }

    private void findVein(Block block, Material type, Set<Block> vein, int limit) {
        if (vein.size() >= limit || vein.contains(block) || block.getType() != type) return;
        vein.add(block);

        for (int x = -1; x <= 1; x++)
            for (int y = -1; y <= 1; y++)
                for (int z = -1; z <= 1; z++)
                    findVein(block.getRelative(x, y, z), type, vein, limit);
    }

    private ItemStack getSmeltedOrRaw(Block block) {
        return switch (block.getType()) {
            case RAW_IRON -> new ItemStack(Material.IRON_INGOT);
            case RAW_GOLD -> new ItemStack(Material.GOLD_INGOT);
            case RAW_COPPER -> new ItemStack(Material.COPPER_INGOT);
            case ANCIENT_DEBRIS -> new ItemStack(Material.NETHERITE_SCRAP);
            default -> new ItemStack(block.getType());
        };
    }
}
