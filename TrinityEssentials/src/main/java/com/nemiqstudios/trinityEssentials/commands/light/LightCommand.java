package com.nemiqstudios.trinityEssentials.commands.light;

import com.nemiqstudios.trinityEssentials.utils.permissionMessages.PermissionMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class LightCommand implements CommandExecutor {
    private static List<Player> playersHasLightActive = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.light")) {
                player.sendMessage(PermissionMessages.NO_PERMISSION_DEFAULT.getText());
                return true;
            }

            if(playersHasLightActive.contains(player)) {
                playersHasLightActive.remove(player);
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                player.sendMessage(ChatColor.RED + "Desativado visão noturna.");
                return true;
            }

            playersHasLightActive.add(player);
            PotionEffect nightVision = new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, Integer.MAX_VALUE, false, false, true);
            player.addPotionEffect(nightVision);
            player.sendMessage(ChatColor.GREEN + "Ativado visão noturna.");
            return true;
        }
        return false;
    }
}
