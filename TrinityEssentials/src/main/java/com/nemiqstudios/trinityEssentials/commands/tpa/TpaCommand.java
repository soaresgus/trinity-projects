package com.nemiqstudios.trinityEssentials.commands.tpa;

import com.nemiqstudios.trinityEssentials.TrinityEssentials;
import com.nemiqstudios.trinityEssentials.utils.tpa.TpaController;
import com.nemiqstudios.trinityEssentials.utils.tpa.TpaRequest;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TpaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(!player.hasPermission("trinity.essentials.tpa")) {
                player.sendMessage(ChatColor.RED + "Sem permissao.");
                return true;
            }

            if(args.length == 0) {
                player.sendMessage(ChatColor.RED + "Utilização: /" + lbl +" <jogador>.");
                return false;
            }

            String receiverNameString = args[0];

            if(Bukkit.getPlayer(receiverNameString) == null) {
                player.sendMessage(ChatColor.RED + "Jogador não encontrado.");
                return false;
            }

            if(TpaController.playersListHasSendTpa.contains(player)) {
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

            TpaRequest request = new TpaRequest(player, receiver);

            TpaController.playersListHasSendTpa.add(player);
            TpaController.tpaRequests.add(request);

            new BukkitRunnable() {
                @Override
                public void run() {
                    if(TpaController.tpaRequests.contains(request)) {
                        player.sendMessage(ChatColor.YELLOW + "Solicitação de teleporte para " + receiverName + " expirada.");
                        receiver.sendMessage(ChatColor.YELLOW + "Solicitação de teleporte solicitada por " + fromName + " expirada.");
                    }

                    TpaController.playersListHasSendTpa.remove(player);
                    TpaController.tpaRequests.remove(request);
                }
            }.runTaskLaterAsynchronously(TrinityEssentials.getInstance(), TpaController.cooldown * 20L);

            TextComponent header = new TextComponent(" \n§eVocê recebeu um pedido de teleporte de §a§l" + fromName + "\n \n");

            TextComponent accept = new TextComponent("         §a§l[ACEITAR]");
            accept.setClickEvent(new ClickEvent(
                    ClickEvent.Action.RUN_COMMAND,
                    "/tpaccept " + fromName
            ));

            TextComponent spacer = new TextComponent("         ");

            TextComponent deny = new TextComponent("§c§l[RECUSAR]");
            deny.setClickEvent(new ClickEvent(
                    ClickEvent.Action.RUN_COMMAND,
                    "/tpadeny " + fromName
            ));

            receiver.spigot().sendMessage(header, accept, spacer, deny, spacer);
            return true;
        }
        return false;
    }
}
