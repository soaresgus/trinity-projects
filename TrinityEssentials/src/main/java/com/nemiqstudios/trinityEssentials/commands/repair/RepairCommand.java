package com.nemiqstudios.trinityEssentials.commands.repair;

import com.nemiqstudios.trinityEssentials.utils.permissionMessages.PermissionMessages;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RepairCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.repair")) {
                player.sendMessage(PermissionMessages.NO_PERMISSION_DEFAULT.getText());
                return true;
            }

            ItemStack[] inventoryItems = player.getInventory().getContents();

            for(ItemStack item : inventoryItems) {
                if (item != null && item.getType().getMaxDurability() > 0) {
                    item.setDurability((short) 0);
                }
            }

            player.sendMessage(ChatColor.GREEN + "Todos os itens do seu inventário foram reparados!");
            player.playSound(player, Sound.BLOCK_ANVIL_USE, 1f, 1f);
            return true;
        }
        return false;
    }
}
