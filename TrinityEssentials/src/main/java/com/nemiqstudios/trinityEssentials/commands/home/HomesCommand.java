package com.nemiqstudios.trinityEssentials.commands.home;

import com.nemiqstudios.trinityEssentials.TrinityEssentials;
import com.nemiqstudios.trinityEssentials.utils.database.HomeDAO;
import com.nemiqstudios.trinityEssentials.utils.home.Home;
import com.nemiqstudios.trinityEssentials.utils.permissionMessages.PermissionMessages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HomesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.homes")) {
                player.sendMessage(PermissionMessages.NO_PERMISSION_DEFAULT.getText());
                return true;
            }

            try {
                HomeDAO homeDAO = new HomeDAO(TrinityEssentials.getInstance().dbManager);
                List<Home> homes = homeDAO.getHomesByPlayerUUID(player.getUniqueId().toString());
                String homesNameConcatenated = homes.stream()
                        .map(Home::getHomeName)
                        .collect(java.util.stream.Collectors.joining(", "));

                if (homesNameConcatenated.isEmpty()) {
                    player.sendMessage(ChatColor.YELLOW + "Você não possuí nenhuma home. Crie uma utilizando /sethome.");
                    return false;
                }

                player.sendMessage(ChatColor.YELLOW + "Suas homes: §7"+homesNameConcatenated);
                return true;

            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Ocorreu um erro ao listar suas homes. Tente novamente.");
                TrinityEssentials.getInstance().getLogger().info(e.getMessage());
            }
        }
        return false;
    }
}
