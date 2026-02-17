package org.rohamc.zeus.commands;

import org.rohamc.zeus.Zeus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ZeusCommand implements CommandExecutor {

    private final Zeus zeus;

    public ZeusCommand(Zeus zeus) {
        this.zeus = zeus;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can toggle Zeus mode!");
            return true;
        }

        if (!player.hasPermission("rohamc.zeus")) {
            player.sendMessage("§cYou don't have permission to use Zeus!");
            return true;
        }

        UUID uuid = player.getUniqueId();
        if (zeus.getActivePlayers().contains(uuid)) {
            zeus.getActivePlayers().remove(uuid);
            player.sendMessage("§cZeus mode disabled!");
        } else {
            zeus.getActivePlayers().add(uuid);
            player.sendMessage("§aZeus mode enabled!");
        }

        return true;
    }
}
