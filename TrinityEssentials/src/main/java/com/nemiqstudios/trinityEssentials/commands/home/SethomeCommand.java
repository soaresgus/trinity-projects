package com.nemiqstudios.trinityEssentials.commands.home;

import com.nemiqstudios.trinityEssentials.TrinityEssentials;
import com.nemiqstudios.trinityEssentials.utils.database.HomeDAO;
import com.nemiqstudios.trinityEssentials.utils.home.Home;
import com.nemiqstudios.trinityEssentials.utils.permissionMessages.PermissionMessages;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.LuckPerms;

import java.util.List;

public class SethomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("trinity.essentials.sethome")) {
                player.sendMessage(PermissionMessages.NO_PERMISSION_DEFAULT.getText());
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Utilização: /" + lbl + " <nome>.");
                return false;
            }

            String homeName = args[0];
            Location location = player.getLocation();

            try {
                HomeDAO homeDAO = new HomeDAO(TrinityEssentials.getInstance().dbManager);
                String playerUUID = player.getUniqueId().toString();
                int homeLimit = getHomeLimit(player);
                List<Home> playerHomes = homeDAO.getHomesByPlayerUUID(playerUUID);

                if(playerHomes.size() >= homeLimit) {
                    player.sendMessage(ChatColor.RED + "Você atingiu seu limite de homes ("+ homeLimit +"). Exclua alguma com /delhome e tente novamente.");
                    return true;
                }

                Home filteredHome = playerHomes.stream()
                        .filter(home -> home.getHomeName().equalsIgnoreCase(homeName))
                        .findFirst()
                        .orElse(null);

                if(filteredHome != null) {
                    player.sendMessage(ChatColor.RED + "A home com o nome " + homeName + " já existe.");
                    return true;
                }

                homeDAO.insertHome(playerUUID, player.getName(), homeName, false, location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
                player.sendMessage(ChatColor.GREEN + "A home " + homeName + " foi salva com sucesso.");
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Falha ao criar a home. Tente novamente.");
                TrinityEssentials.getInstance().getLogger().info(e.getMessage());
            }
        }
        return false;
    }

    public int getHomeLimit(Player player) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        CachedMetaData metaData = luckPerms.getPlayerAdapter(Player.class).getMetaData(player);

        return metaData.getMetaValue("homelimit", Integer::parseInt).orElse(3);
    }
}
