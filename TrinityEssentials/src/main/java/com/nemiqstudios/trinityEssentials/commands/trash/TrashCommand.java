package com.nemiqstudios.trinityEssentials.commands.trash;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TrashCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.trash")) {
                player.sendMessage(ChatColor.RED + "Sem permissão.");
                return true;
            }

            Inventory trashInventory = Bukkit.createInventory(null, 9*6, "Lixeira");

            player.openInventory(trashInventory);
        }
        return false;
    }
}
