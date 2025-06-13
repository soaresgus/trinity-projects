package com.nemiqstudios.trinityEssentials.commands.warp;

import com.nemiqstudios.trinityEssentials.TrinityEssentials;
import com.nemiqstudios.trinityEssentials.utils.warp.WarpController;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetwarpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("trinity.essentials.setwarp")) {
                player.sendMessage(ChatColor.RED + "Sem permissão.");
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Utilização: /" + lbl + " <nome>.");
                return false;
            }

            try {
                String warpName = args[0];
                Location warpLocation = player.getLocation();

                WarpController controller = new WarpController();
                controller.setWarp(TrinityEssentials.getInstance(), warpName, warpLocation);

                player.sendMessage(ChatColor.GREEN + "Warp " + warpName + " criada com sucesso!");
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Ocorreu uma falha a criar a warp. Tente novamente.");
                throw new RuntimeException(e);
            }

        }
        return false;
    }
}
