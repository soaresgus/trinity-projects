package com.nemiqstudios.trinityPixelmon.commands.hatch;

import com.nemiqstudios.trinityPixelmon.utils.permissionMessages.PermissionMessages;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class HatchCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.pixelmon.hatch")) {
                player.sendMessage(PermissionMessages.NO_PERMISSION_DEFAULT.getText());
                return true;
            }

            if(args.length == 0) {
                player.sendMessage(ChatColor.RED + "Utilização: /"+lbl+" <slot>.");
                return false;
            }

            String slot = args[0];
            boolean isValidSlot = slot.matches("[1-6]");

            if(!isValidSlot) {
                player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                player.sendMessage(ChatColor.RED + "Digite um número válido entre 1 a 6.");
                return false;
            }

            int slotNumber = Integer.parseInt(slot) - 1;

            CraftPlayer craftPlayer = (CraftPlayer) player;
            ServerPlayer serverPlayer = craftPlayer.getHandle();
            PlayerPartyStorage party = com.pixelmonmod.pixelmon.api.storage.StorageProxy.getPartyNow(serverPlayer);

            if (party == null) {
                player.sendMessage(ChatColor.RED + "Não foi possível acessar o seu inventário Pixelmon.");
                return false;
            }

            Pokemon pokemon = party.get(slotNumber);

            if (pokemon == null) {
                player.sendMessage(ChatColor.RED + "Este slot está vazio.");
                return false;
            }

            if (!pokemon.isEgg()) {
                player.sendMessage(ChatColor.RED + "Este slot não contém um ovo.");
                return false;
            }

            pokemon.hatchEgg();
            return true;
        }
        return false;
    }
}
