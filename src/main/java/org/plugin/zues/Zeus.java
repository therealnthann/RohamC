package com.yourname.zeus;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.block.Action;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Zeus implements Listener {

    private final Set<UUID> activePlayers = new HashSet<>();
    private final JavaPlugin plugin;

    public Zeus(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public Set<UUID> getActivePlayers() {
        return activePlayers;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!activePlayers.contains(player.getUniqueId())) return;

        Action action = event.getAction();
        if (action != Action.LEFT_CLICK_BLOCK && action != Action.LEFT_CLICK_AIR) return;

        Block block = event.getClickedBlock();
        if (block != null) {
            block.getWorld().strikeLightning(block.getLocation());
        } else {
            // If clicking air, strike where the player is looking (up to 20 blocks)
            Block target = player.getTargetBlock(null, 20);
            if (target != null && !target.getType().isAir()) {
                target.getWorld().strikeLightning(target.getLocation());
            }
        }
    }
}
