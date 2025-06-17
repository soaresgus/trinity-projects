package com.nemiqstudios.trinityEssentials.commands.back;

import com.nemiqstudios.trinityEssentials.utils.back.BackController;
import com.nemiqstudios.trinityEssentials.utils.permissionMessages.PermissionMessages;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.back")) {
                player.sendMessage(PermissionMessages.NO_PERMISSION_DEFAULT.getText());
                return true;
            }

            BackController controller = new BackController();
            Location playerLastLocation = controller.getPlayerLastLocation(player);

            if(playerLastLocation == null) {
                player.sendMessage(ChatColor.RED + "Você não possui uma última localização salva.");
                return true;
            }

            player.teleport(playerLastLocation);

            if(controller.getPlayersWhoDied().contains(player)) {
                controller.removePlayerFromDeathList(player);
            }

            player.sendMessage(ChatColor.GREEN + "Você foi teleportado para sua última localização.");
        }
        return false;
    }
}
