package org.rohamc.timespeed.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeSpeedCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private boolean fastTimeEnabled = false;
    private BukkitRunnable timeTask;

    public TimeSpeedCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {
            if (!player.hasPermission("rohamc.timespeed")) {
                player.sendMessage("§cYou don't have permission to use this command!");
                return true;
            }
        }

        fastTimeEnabled = !fastTimeEnabled;

        if (fastTimeEnabled) {
            startFastTime();
            sender.sendMessage("§aTime speed enabled! Day and night will go faster.");
        } else {
            stopFastTime();
            sender.sendMessage("§cTime speed disabled! Time returns to normal.");
        }

        return true;
    }

    private void startFastTime() {
        if (timeTask != null) timeTask.cancel();

        timeTask = new BukkitRunnable() {
            @Override
            public void run() {
                for (World world : Bukkit.getWorlds()) {
                    long newTime = world.getTime() + 1000; // speed multiplier
                    world.setTime(newTime % 24000);
                }
            }
        };

        timeTask.runTaskTimer(plugin, 0L, 1L);
    }

    private void stopFastTime() {
        if (timeTask != null) timeTask.cancel();
        timeTask = null;
    }
}
