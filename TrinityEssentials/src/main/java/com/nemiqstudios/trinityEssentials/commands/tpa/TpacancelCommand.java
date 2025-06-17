package com.nemiqstudios.trinityEssentials.commands.tpa;

import com.nemiqstudios.trinityEssentials.utils.permissionMessages.PermissionMessages;
import com.nemiqstudios.trinityEssentials.utils.tpa.TpaController;
import com.nemiqstudios.trinityEssentials.utils.tpa.TpaRequest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpacancelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.tpacancel")) {
                player.sendMessage(PermissionMessages.NO_PERMISSION_DEFAULT.getText());
                return true;
            }

            TpaRequest request = TpaController.tpaRequests.stream()
                    .filter(req -> req.getSender().equals(player))
                    .findFirst()
                    .orElse(null);

            if (request == null) {
                player.sendMessage(ChatColor.RED + "Você não possui solicitações pendentes.");
                return false;
            }

            Player receiver = request.getReceiver();

            TpaController.tpaRequests.remove(request);
            player.sendMessage(ChatColor.GREEN + "Solicitação de teleporte para " + receiver.getName() + " cancelada.");
            receiver.sendMessage(ChatColor.YELLOW + "O jogador " + player.getName() + " cancelou a solicitação de teleporte.");
        }
        return false;
    }
}
