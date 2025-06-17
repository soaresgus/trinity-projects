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

public class HomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.home")) {
                player.sendMessage(PermissionMessages.NO_PERMISSION_DEFAULT.getText());
                return true;
            }

            if(args.length == 0) {
                player.sendMessage(ChatColor.RED + "Utilização: /"+lbl+" <nome>.");
                return false;
            }

            String homeName = args[0];
            boolean isHomeNamePublicHome = homeName.matches("^[^:]+:[^:]+$");

            if (isHomeNamePublicHome) {
                String[] publicHomeParts = homeName.split(":", 2);
                String publicHomeNickname = publicHomeParts[0];
                String publicHomeName = publicHomeParts[1];

                try {
                    HomeDAO homeDao = new HomeDAO(TrinityEssentials.getInstance().dbManager);
                    List<Home> otherPlayerHomes = homeDao.getHomesByPlayerUsername(publicHomeNickname);

                    Home otherPlayerPublicHome = otherPlayerHomes.stream()
                            .filter(home -> home.getHomeName().equalsIgnoreCase(publicHomeName))
                            .findFirst()
                            .orElse(null);

                    if(otherPlayerPublicHome == null) {
                        player.sendMessage(ChatColor.RED + "A home " + publicHomeName + " do jogador " + publicHomeNickname + " não existe.");
                        return true;
                    }

                    if(!otherPlayerPublicHome.isPublic()) {
                        player.sendMessage(ChatColor.RED + "A home " + publicHomeName + " do jogador " + publicHomeNickname + " não é pública.");
                        return true;
                    }

                    player.teleport(otherPlayerPublicHome.getLocation());
                    player.sendMessage(ChatColor.GREEN + "Você foi teleportado para a home " + publicHomeName + " do jogador " + publicHomeNickname + ".");
                    return true;
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "Ocorreu um erro ao acessar a home solicitada. Tente novamente.");
                    TrinityEssentials.getInstance().getLogger().info(e.getMessage());
                    return false;
                }
            }

            try {
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

                player.teleport(filteredHome.getLocation());
                player.sendMessage(ChatColor.GREEN + "Você foi teleportado para a home " + homeName + ".");
                return true;
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Ocorreu um erro ao acessar a home solicitada. Tente novamente.");
                TrinityEssentials.getInstance().getLogger().info(e.getMessage());
                return false;
            }

        }
        return false;
    }
}
