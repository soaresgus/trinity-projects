package com.nemiqstudios.trinityEssentials.commands.feed;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.feed")) {
                player.sendMessage(ChatColor.RED + "Sem permissão.");
                return true;
            }

            int maxFood = 20;
            float maxSaturation = 20f;

            player.setFoodLevel(maxFood);
            player.setSaturation(maxSaturation);
            player.playSound(player, Sound.ENTITY_PLAYER_BURP, 1f, 1f);
            player.sendMessage(ChatColor.GREEN + "Sua fome foi saciada.");
        }
        return false;
    }
}
