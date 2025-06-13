package com.nemiqstudios.trinityEssentials.commands.warp;

import com.nemiqstudios.trinityEssentials.TrinityEssentials;
import com.nemiqstudios.trinityEssentials.utils.home.Home;
import com.nemiqstudios.trinityEssentials.utils.warp.WarpController;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class WarpsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.warps")) {
                player.sendMessage(ChatColor.RED + "Sem permissão.");
                return true;
            }

            try {
                WarpController controller = new WarpController();
                List<String> warpsName = controller.getAllWarpsName(TrinityEssentials.getInstance());
                String warpsNameConcatenated = String.join(", ", warpsName);

                player.sendMessage(ChatColor.YELLOW + "Warps disponíveis: §7"+warpsNameConcatenated);
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Ocorreu um erro ao listar as warps. Tente novamente.");
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
