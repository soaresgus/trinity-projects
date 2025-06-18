package com.nemiqstudios.trinityPixelmon.commands.pokecolor;

import com.nemiqstudios.trinityPixelmon.utils.Colors;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PokecolorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("trinity.pixelmon.pokecolor")) {
                player.sendMessage(ChatColor.RED + "Você não possui permissão para executar este comando.");
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Utilização: /" + lbl + " <cor>.");
                return false;
            }

            try {
                CraftPlayer craftPlayer = (CraftPlayer) player;
                ServerPlayer serverPlayer = craftPlayer.getHandle();
                PlayerPartyStorage partyStorage = StorageProxy.getPartyNow(serverPlayer);
                String selectedColor = args[0].toUpperCase();

                ChatColor chatColor = null;

                for (Colors c : Colors.values()) {
                    if (c.getPortugueseTranslation().equalsIgnoreCase(selectedColor)) {
                        chatColor = c.getChatColor();
                        break;
                    }
                }

                if (partyStorage != null) {
                    Pokemon selectedPokemon = partyStorage.getSelectedPokemon();
                    String selectedPokemonCurrentNickname = selectedPokemon.getNickname().getString();

                    if(selectedPokemonCurrentNickname.length() <= 0) {
                        selectedPokemonCurrentNickname = selectedPokemon.getSpecies().getLocalizedName();
                    }

                    if(chatColor == null) {
                        player.sendMessage(ChatColor.RED + "Cor inválida. Utilize uma cor listada.");
                        return false;
                    }

                    String selectedPokemonNicknameWithoutColor = selectedPokemonCurrentNickname.replaceAll("[&0-9]", "");

                    selectedPokemon.setNickname(selectedPokemonNicknameWithoutColor);
                    selectedPokemon.setNickname("&" + chatColor.getChar() + selectedPokemonCurrentNickname);

                    player.playSound(player, Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1f ,1f);
                    player.sendMessage(ChatColor.GREEN + "Cor do nome do Pokémon " + selectedPokemon.getSpecies().getLocalizedName() + " alterado com sucesso!");
                }
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Não foi possível alterar a cor do nome de seu Pokémon. Tente novamente.");
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
