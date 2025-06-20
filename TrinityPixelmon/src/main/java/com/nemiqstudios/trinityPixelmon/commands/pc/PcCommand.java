package com.nemiqstudios.trinityPixelmon.commands.pc;

import com.nemiqstudios.trinityPixelmon.utils.permissionMessages.PermissionMessages;
import com.pixelmonmod.pixelmon.api.storage.PCStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PcCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.pixelmon.pc")) {
                player.sendMessage(PermissionMessages.NO_PERMISSION_DEFAULT.getText());
                return true;
            }

            try {
                CraftPlayer craftPlayer = (CraftPlayer) player;
                ServerPlayer serverPlayer = craftPlayer.getHandle();
                PCStorage pc = StorageProxy.getPCForPlayerNow(serverPlayer);

                if(pc != null) {
                    pc.open(serverPlayer);
                    player.playSound(player, Sound.ENTITY_PUFFER_FISH_BLOW_UP, 1f ,1f);
                }
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Ocorreu um erro ao executar o comando. Tente novamente.");
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
