package com.yourname.midastouch;

import com.yourname.midastouch.commands.MidasCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MidasTouch implements Listener {

    private final Set<UUID> activePlayers = new HashSet<>();
    private final JavaPlugin plugin;

    public MidasTouch(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);

        if (plugin.getCommand("midas") != null) {
            plugin.getCommand("midas").setExecutor(new MidasCommand(this));
        }
    }

    public Set<UUID> getActivePlayers() {
        return activePlayers;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!activePlayers.contains(player.getUniqueId())) return;

        ItemStack hand = player.getInventory().getItemInMainHand();
        if (hand != null && !hand.getType().isAir()) {
            hand.setType(Material.GOLD_INGOT);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!activePlayers.contains(player.getUniqueId())) return;

        Block block = player.getLocation().getBlock().getRelative(0, -1, 0);
        if (block.getType().isAir()) return;

        block.setType(Material.GOLD_BLOCK);
    }
}