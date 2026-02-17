package com.yourname.berserk;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Berserk implements Listener {

    private final Set<UUID> activePlayers = new HashSet<>();
    private final JavaPlugin plugin;

    public Berserk(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public Set<UUID> getActivePlayers() {
        return activePlayers;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        if (!activePlayers.contains(player.getUniqueId())) return;

        // Double damage
        event.setDamage(event.getDamage() * 2);

        // Optional: small knockback for style
        if (event.getEntity() instanceof Player target) {
            target.setVelocity(player.getLocation().getDirection().multiply(0.5));
        }
    }
}
