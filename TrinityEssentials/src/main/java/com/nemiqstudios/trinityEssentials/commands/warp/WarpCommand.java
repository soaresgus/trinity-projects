package com.nemiqstudios.trinityEssentials.commands.warp;

import com.nemiqstudios.trinityEssentials.TrinityEssentials;
import com.nemiqstudios.trinityEssentials.utils.warp.WarpController;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.warp")) {
                player.sendMessage(ChatColor.RED + "Sem permissão.");
                return true;
            }

            if(args.length == 0) {
                player.sendMessage(ChatColor.RED + "Utilização: /"+lbl+" <nome>.");
                return false;
            }

            try {
                String warpName = args[0];
                WarpController controller = new WarpController();
                Location warpLocation = controller.getWarpLocationByName(TrinityEssentials.getInstance(), warpName);

                if(warpLocation == null) {
                    player.sendMessage(ChatColor.RED + "A warp " + warpName + " não existe.");
                    return false;
                }

                player.teleport(warpLocation);
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f , 1f);
                player.sendMessage(ChatColor.GREEN + "Você foi teleportado para a warp " + warpName + ".");
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Ocorreu um erro ao teleporta-lo para a warp. Tente novamente.");
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
