package com.nemiqstudios.trinityEssentials.commands.spawn;

import com.nemiqstudios.trinityEssentials.TrinityEssentials;
import com.nemiqstudios.trinityEssentials.utils.permissionMessages.PermissionMessages;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetspawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.setspawn")) {
                player.sendMessage(PermissionMessages.NO_PERMISSION_DEFAULT.getText());
                return true;
            }

            Location currentLocation = player.getLocation();

            TrinityEssentials.getInstance().getLocalesConfig().set("spawn", currentLocation);
            TrinityEssentials.getInstance().saveLocales();

            player.getWorld().setSpawnLocation(currentLocation);

            player.sendMessage(ChatColor.GREEN + "Spawn definido como sucesso.");
        }
        return false;
    }
}
