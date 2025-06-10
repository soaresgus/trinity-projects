package com.nemiqstudios.trinityEssentials.commands.tpa;

import com.nemiqstudios.trinityEssentials.TrinityEssentials;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class TpaCommand implements CommandExecutor {
    public static ArrayList<Player> playersListHasSendTpa = new ArrayList<>();
    public static ArrayList<Player> playersListHasReceiveTpa = new ArrayList<>();
    private static int cooldown = 30;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length == 0) {
                player.sendMessage(ChatColor.RED + "Utilização: /tpa <jogador>.");
                return false;
            }

            String receiverNameString = args[0];

            if(Bukkit.getPlayer(receiverNameString) == null) {
                player.sendMessage(ChatColor.RED + "Jogador não encontrado.");
                return false;
            }

            if(playersListHasSendTpa.contains(player)) {
                player.sendMessage(ChatColor.RED + "Aguarde para enviar outra solicitação.");
                return false;
            }

            Player receiver = Bukkit.getPlayer(receiverNameString);

            if(player == receiver) {
                player.sendMessage(ChatColor.RED + "Você não pode enviar solicitações para si mesmo.");
                return false;
            }

            String fromName = player.getName();
            String receiverName = receiver.getName();

            player.sendMessage(ChatColor.YELLOW + "Foi enviado uma solitação de teleporte para " + receiverName + ", para cancelar utilize /tpacancel.");

            playersListHasSendTpa.add(player);

            playersListHasReceiveTpa.add(receiver);

            new BukkitRunnable() {
                @Override
                public void run() {
                    playersListHasSendTpa.remove(player);
                    player.sendMessage(ChatColor.YELLOW + "Solicitação de teleporte para " + receiverName + " expirada.");
                    receiver.sendMessage(ChatColor.YELLOW + "Solicitação de teleporte solicitada por " + fromName + " expirada.");
                }
            }.runTaskLaterAsynchronously(TrinityEssentials.getInstance(), cooldown * 20L);

            TextComponent header = new TextComponent("§eVocê recebeu um pedido de teleporte de §a§l" + fromName + "\n \n");

            TextComponent accept = new TextComponent("         §a§l[ACEITAR]");
            accept.setClickEvent(new ClickEvent(
                    ClickEvent.Action.RUN_COMMAND,
                    "/tpaccept " + fromName
            ));

            TextComponent spacer = new TextComponent("         ");

            TextComponent deny = new TextComponent("§c§l[RECUSAR]");
            deny.setClickEvent(new ClickEvent(
                    ClickEvent.Action.RUN_COMMAND,
                    "/tpadeny"
            ));

            receiver.spigot().sendMessage(header, accept, spacer, deny, spacer);

            return true;
        }
        return false;
    }
}
