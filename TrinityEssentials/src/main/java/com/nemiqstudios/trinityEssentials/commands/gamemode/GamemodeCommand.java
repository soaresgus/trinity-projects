package com.nemiqstudios.trinityEssentials.commands.gamemode;

import com.nemiqstudios.trinityEssentials.utils.permissionMessages.PermissionMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("trinity.essentials.gamemode")) {
                player.sendMessage(PermissionMessages.NO_PERMISSION_DEFAULT.getText());
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Utilização: /" + lbl + " <modo> [jogador]");
                return false;
            }

            try {
                String gameModeString = args[0];

                if(!GamemodeTabCompleter.VALID_MODES.contains(gameModeString.toLowerCase())) {
                    player.sendMessage(ChatColor.RED + "Modo de jogo " + gameModeString + " não encontrado.");
                    return false;
                }

                if (args.length == 2) {
                    String playerName = args[1];
                    Player receiver = Bukkit.getPlayer(playerName);

                    if(receiver == null) {
                        player.sendMessage(ChatColor.RED + "O jogador " + playerName + " não foi encontrado.");
                        return false;
                    }

                    String changedGamemodeName = changeGameModeByString(receiver, gameModeString);
                    player.sendMessage(ChatColor.GREEN + "O modo de jogo de " + receiver.getName() + " foi alterado para " + changedGamemodeName + ".");

                    return true;
                }

                changeGameModeByString(player, gameModeString);
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Ocorreu um erro ao executar o comando. Tente novamente.");
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public String changeGameModeByString(Player player, String gameModeString) {
        if(gameModeString.equalsIgnoreCase("survival") || gameModeString.equals("0")) {
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(ChatColor.GREEN + "Seu modo de jogo foi alterado para sobrevivência.");
            return "sobrevivência";
        }

        if(gameModeString.equalsIgnoreCase("creative") || gameModeString.equals("1")) {
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(ChatColor.GREEN + "Seu modo de jogo foi alterado para criativo.");
            return "criativo";
        }

        if(gameModeString.equalsIgnoreCase("spectator") || gameModeString.equals("3")) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(ChatColor.GREEN + "Seu modo de jogo foi alterado para espectador.");
            return "espectador";
        }

        return null;
    }
}
