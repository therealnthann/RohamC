package org.rohamc;

import org.bukkit.plugin.java.JavaPlugin;

public class RohamC extends JavaPlugin {

    private static RohamC instance;

    @Override
    public void onEnable() {
        instance = this;

        // Save default config if it doesn't exist
        saveDefaultConfig();

        getLogger().info("RohamC plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("RohamC plugin has been disabled!");
    }

    // Getter for static instance
    public static RohamC getInstance() {
        return instance;
    }
}
