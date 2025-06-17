package com.nemiqstudios.trinityPixelmon.commands.pokeheal;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PokehealCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("trinity.pixelmon.pokeheal")) {
                player.sendMessage(ChatColor.RED + "Você não possui permissão para executar este comando.");
                return true;
            }

            try {
                PartyStorage party = StorageProxy.getPartyNow(player.getUniqueId());

                if (party == null) {
                    player.sendMessage(ChatColor.RED + "Não foi possível acessar seus Pokémon.");
                    return false;
                }
                for (Pokemon pokemon : party.getAll()) {
                    if (pokemon != null) {
                        pokemon.heal();
                    }
                }

                player.playSound(player, Sound.ENTITY_GLOW_SQUID_AMBIENT, 1f, 1f);
                player.sendMessage(ChatColor.GREEN + "Todos os seus Pokémons foram curados!");
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Ocorreu um erro ao curar seus pokémons. Tente novamente.");
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}