package com.yourname.berserk.commands;

import com.yourname.berserk.Berserk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BerserkCommand implements CommandExecutor {

    private final Berserk berserk;

    public BerserkCommand(Berserk berserk) {
        this.berserk = berserk;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can toggle Berserk mode!");
            return true;
        }

        if (!player.hasPermission("rohamc.berserk")) {
            player.sendMessage("§cYou don't have permission to use Berserk mode!");
            return true;
        }

        UUID uuid = player.getUniqueId();
        if (berserk.getActivePlayers().contains(uuid)) {
            berserk.getActivePlayers().remove(uuid);
            player.sendMessage("§cBerserk mode disabled!");
        } else {
            berserk.getActivePlayers().add(uuid);
            player.sendMessage("§aBerserk mode enabled!");
        }

        return true;
    }
}
