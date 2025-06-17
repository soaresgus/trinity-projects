package com.nemiqstudios.trinityEssentials.commands.tpa;

import com.nemiqstudios.trinityEssentials.utils.permissionMessages.PermissionMessages;
import com.nemiqstudios.trinityEssentials.utils.tpa.TpaController;
import com.nemiqstudios.trinityEssentials.utils.tpa.TpaRequest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpadenyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.tpadeny")) {
                player.sendMessage(PermissionMessages.NO_PERMISSION_DEFAULT.getText());
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Utilização: /" + lbl + " <jogador>.");
                return false;
            }

            String tpaSenderNameString = args[0];

            if (Bukkit.getPlayer(tpaSenderNameString) == null) {
                player.sendMessage(ChatColor.RED + "Jogador não encontrado.");
                return false;
            }

            Player tpaSender = Bukkit.getPlayer(tpaSenderNameString);
            boolean isPlayerReceiveTpa = TpaController.tpaRequests.stream()
                    .anyMatch(req -> req.getReceiver().equals(player));

            if (!isPlayerReceiveTpa) {
                player.sendMessage(ChatColor.RED + "Você não possui solicitações pendentes.");
                return false;
            }

            TpaRequest request = TpaController.tpaRequests.stream()
                    .filter(req ->
                            req.getSender().equals(tpaSender) &&
                                    req.getReceiver().equals(player))
                    .findFirst()
                    .orElse(null);

            if (request == null) {
                player.sendMessage(ChatColor.RED + "Você não possui solicitações pendentes do jogador " + tpaSender.getName() + ".");
                return false;
            }

            TpaController.tpaRequests.remove(request);

            player.sendMessage(ChatColor.RED + "Solicitação de teleporte de " + request.getSender().getName() + " foi negada.");
            request.getSender().sendMessage(ChatColor.RED + "Sua solicitação de teleporte enviada para " + request.getReceiver().getName() + " foi negada.");
            return true;
        }

        return false;
    }
}
