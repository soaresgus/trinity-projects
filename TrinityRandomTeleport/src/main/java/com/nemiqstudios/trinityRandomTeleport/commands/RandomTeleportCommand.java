package com.nemiqstudios.trinityRandomTeleport.commands;

import com.nemiqstudios.trinityRandomTeleport.utils.RandomTeleportController;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RandomTeleportCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.randomteleport.rtp")) {
                player.sendMessage(ChatColor.RED + "Você não possui permissão para usar este comando.");
                return true;
            }

            RandomTeleportController controller = new RandomTeleportController();
            controller.teleportPlayer(player);
            return true;
        }
        return false;
    }
}
