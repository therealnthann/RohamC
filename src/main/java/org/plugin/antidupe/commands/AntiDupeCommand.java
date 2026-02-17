package com.yourname.antidupe.commands;

import com.yourname.antidupe.AntiDupe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class AntiDupeCommand implements CommandExecutor {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final long COOLDOWN_TIME = 5000; // 5 seconds

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cThis command can only be executed by players!");
            return true;
        }

        // Permission check
        if (!player.hasPermission("rohamc.antidupe")) {
            player.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        // Cooldown check
        UUID playerId = player.getUniqueId();
        if (cooldowns.containsKey(playerId)) {
            long timeLeft = (cooldowns.get(playerId) + COOLDOWN_TIME) - System.currentTimeMillis();
            if (timeLeft > 0) {
                player.sendMessage(String.format("§cPlease wait %.1f seconds before using this again!",
                        timeLeft / 1000.0));
                return true;
            }
        }

        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand == null || itemInHand.getType().isAir()) {
            player.sendMessage("§cYou must be holding an item to use this command!");
            return true;
        }

        int currentAmount = itemInHand.getAmount();
        int maxStackSize = itemInHand.getMaxStackSize();
        int doubledAmount = currentAmount * 2;

        int amountInHand = Math.min(doubledAmount, maxStackSize);
        itemInHand.setAmount(amountInHand);
        player.getInventory().setItemInMainHand(itemInHand);

        int leftover = doubledAmount - amountInHand;
        if (leftover > 0) {
            ItemStack extra = itemInHand.clone();
            extra.setAmount(leftover);
            var notStored = player.getInventory().addItem(extra);
            for (ItemStack drop : notStored.values()) {
                player.getWorld().dropItemNaturally(player.getLocation(), drop);
            }
        }

        String successMessage = AntiDupe.getInstance().getConfig()
                .getString("messages.success", "&aSuccessfully doubled your {item} to {amount}!")
                .replace("{amount}", String.valueOf(doubledAmount))
                .replace("{item}", itemInHand.getType().toString().toLowerCase().replace("_", " "))
                .replace("&", "§");
        player.sendMessage(successMessage);

        cooldowns.put(playerId, System.currentTimeMillis());
        return true;
    }
}
