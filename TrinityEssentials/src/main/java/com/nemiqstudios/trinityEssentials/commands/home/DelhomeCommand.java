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

public class DelhomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("trinity.essentials.delhome")) {
                player.sendMessage(PermissionMessages.NO_PERMISSION_DEFAULT.getText());
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Utilização: /" + lbl + " <nome>.");
                return false;
            }

            try {
                String homeName = args[0];
                String playerUUID = player.getUniqueId().toString();
                HomeDAO homeDAO = new HomeDAO(TrinityEssentials.getInstance().dbManager);
                List<Home> homes = homeDAO.getHomesByPlayerUUID(player.getUniqueId().toString());

                Home filteredHome = homes.stream()
                        .filter(home -> home.getHomeName().equalsIgnoreCase(homeName))
                        .findFirst()
                        .orElse(null);

                if(filteredHome == null) {
                    player.sendMessage(ChatColor.RED + "A home " + homeName + " não existe.");
                    return true;
                }

                homeDAO.deleteHomeByPlayerUUID(playerUUID, homeName);

                player.sendMessage(ChatColor.GREEN + "A home " + homeName + " foi excluida com sucesso!");
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Ocorreu um erro ao excluir sua home. Tente novamente.");
                TrinityEssentials.getInstance().getLogger().info(e.getMessage());
            }
        }
        return false;
    }
}
