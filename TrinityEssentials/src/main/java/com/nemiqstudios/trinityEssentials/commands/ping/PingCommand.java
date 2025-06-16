package com.nemiqstudios.trinityEssentials.commands.ping;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.ping")) {
                player.sendMessage(ChatColor.RED + "Sem permissão.");
                return true;
            }

            int ping = player.getPing();
            player.sendMessage(ChatColor.YELLOW + "Seu ping: §a" + ping + " ms");
        }
        return false;
    }
}
