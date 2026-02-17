package org.rohamc.antidupe;

import org.rohamc.antidupe.commands.AntiDupeCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiDupe extends JavaPlugin {

    private static AntiDupe instance;

    @Override
    public void onEnable() {
        instance = this;

        // Save default config
        saveDefaultConfig();

        // Register command
        if (getCommand("antidupe") != null) {
            getCommand("antidupe").setExecutor(new AntiDupeCommand());
        }

        getLogger().info("AntiDupe has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("AntiDupe has been disabled!");
    }

    public static AntiDupe getInstance() {
        return instance;
    }
}
