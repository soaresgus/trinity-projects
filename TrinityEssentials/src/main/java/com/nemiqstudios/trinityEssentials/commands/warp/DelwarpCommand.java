package com.nemiqstudios.trinityEssentials.commands.warp;

import com.nemiqstudios.trinityEssentials.TrinityEssentials;
import com.nemiqstudios.trinityEssentials.utils.warp.WarpController;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class DelwarpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("trinity.essentials.delwarp")) {
                player.sendMessage(ChatColor.RED + "Sem permissão.");
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Utilização: /" + lbl + " <nome>.");
                return false;
            }

            try {
                String warpName = args[0];
                WarpController controller = new WarpController();
                List<String> warpsName = controller.getAllWarpsName(TrinityEssentials.getInstance());

                if(!warpsName.contains(warpName)) {
                    player.sendMessage(ChatColor.RED + "A warp " + warpName + " não existe.");
                    return false;
                }

                controller.deleteWarp(TrinityEssentials.getInstance(), warpName);
                player.sendMessage(ChatColor.GREEN + "Warp " + warpName + " excluida com sucesso!");
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Ocorreu uma falha a excluir a warp. Tente novamente.");
                throw new RuntimeException(e);
            }

        }
        return false;
    }
}
