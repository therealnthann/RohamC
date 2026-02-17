package com.yourname.timespeed;

import com.yourname.timespeed.commands.TimeSpeedCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class TimeSpeed extends JavaPlugin {

    private static TimeSpeed instance;

    @Override
    public void onEnable() {
        instance = this;

        // Register command
        if (getCommand("timespeed") != null) {
            getCommand("timespeed").setExecutor(new TimeSpeedCommand(this));
        }

        getLogger().info("TimeSpeed has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("TimeSpeed has been disabled!");
    }

    public static TimeSpeed getInstance() {
        return instance;
    }
}
