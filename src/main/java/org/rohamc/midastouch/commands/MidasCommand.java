package org.rohamc.midastouch.commands;

import org.rohamc.midastouch.MidasTouch;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MidasCommand implements CommandExecutor {

    private final MidasTouch plugin;

    public MidasCommand(MidasTouch plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can toggle Midas Touch!");
            return true;
        }

        if (!player.hasPermission("rohamc.midas")) {
            player.sendMessage("§cYou don't have permission to use Midas Touch!");
            return true;
        }

        UUID uuid = player.getUniqueId();

        if (plugin.getActivePlayers().contains(uuid)) {
            plugin.getActivePlayers().remove(uuid);
            player.sendMessage("§cMidas Touch disabled!");
        } else {
            plugin.getActivePlayers().add(uuid);
            player.sendMessage("§aMidas Touch enabled!");
        }

        return true;
    }
}
