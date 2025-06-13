package com.nemiqstudios.trinityEssentials.commands.spawn;

import com.nemiqstudios.trinityEssentials.TrinityEssentials;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetspawnCommand implements CommandExecutor {
    private TrinityEssentials plugin;

    public SetspawnCommand(TrinityEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.setspawn")) {
                player.sendMessage(ChatColor.RED + "Sem permissao.");
                return true;
            }

            Location currentLocation = player.getLocation();

            plugin.getLocalesConfig().set("spawn", currentLocation);
            plugin.saveLocales();

            player.getWorld().setSpawnLocation(currentLocation);

            player.sendMessage(ChatColor.GREEN + "Spawn definido como sucesso.");
        }
        return false;
    }
}
