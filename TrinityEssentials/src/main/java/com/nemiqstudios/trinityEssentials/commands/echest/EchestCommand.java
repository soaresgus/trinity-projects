package com.nemiqstudios.trinityEssentials.commands.echest;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class EchestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.echest")) {
                player.sendMessage(ChatColor.RED + "Sem permissão.");
                return true;
            }

            Inventory enderchestInventory = player.getEnderChest();
            player.openInventory(enderchestInventory);
        }
        return false;
    }
}
