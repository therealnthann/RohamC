package com.yourname.mining.commands;

import com.yourname.mining.Mining;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MiningCommand implements CommandExecutor {

    private final Mining mining;

    public MiningCommand(Mining mining) {
        this.mining = mining;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can toggle Mining Enhancement!");
            return true;
        }

        if (!player.hasPermission("rohamc.mining")) {
            player.sendMessage("§cYou don't have permission!");
            return true;
        }

        UUID uuid = player.getUniqueId();
        if (mining.getActivePlayers().contains(uuid)) {
            mining.getActivePlayers().remove(uuid);
            player.sendMessage("§cMining Enhancement disabled!");
        } else {
            mining.getActivePlayers().add(uuid);
            player.sendMessage("§aMining Enhancement enabled!");
        }

        return true;
    }
}
