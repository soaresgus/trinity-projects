package com.nemiqstudios.trinityEssentials.commands.spawn;

import com.nemiqstudios.trinityEssentials.TrinityEssentials;
import com.nemiqstudios.trinityEssentials.utils.permissionMessages.PermissionMessages;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.spawn")) {
                player.sendMessage(PermissionMessages.NO_PERMISSION_DEFAULT.getText());
                return true;
            }

            Location spawnLocation = TrinityEssentials.getInstance().getLocalesConfig().getLocation("spawn");

            player.getWorld().playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);

            if(spawnLocation == null) {
                player.teleport(player.getWorld().getSpawnLocation());
                return false;
            }

            player.teleport(spawnLocation);
        }
        return false;
    }
}
