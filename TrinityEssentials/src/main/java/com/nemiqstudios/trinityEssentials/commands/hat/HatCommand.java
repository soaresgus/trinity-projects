package com.nemiqstudios.trinityEssentials.commands.hat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.hat")) {
                player.sendMessage(ChatColor.RED + "Sem permissão.");
                return true;
            }

            ItemStack playerHelmet = player.getInventory().getHelmet();

            if(playerHelmet != null) {
                player.sendMessage(ChatColor.RED + "Remova o item que está equipado em sua cabeça.");
                player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                return false;
            }

            ItemStack playerHandItem = player.getInventory().getItemInMainHand();

            if(playerHandItem.getType().equals(Material.AIR)) {
                player.sendMessage(ChatColor.RED + "Você precisa estar segurando algum item em sua mão principal.");
                player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                return false;
            }

            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
            player.sendMessage(ChatColor.GREEN + "Chapéu equipado!");
            player.getInventory().setHelmet(playerHandItem);
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        }
        return false;
    }
}
