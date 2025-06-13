package com.nemiqstudios.trinityEssentials.events;

import com.nemiqstudios.trinityEssentials.TrinityEssentials;
import com.nemiqstudios.trinityEssentials.utils.warp.WarpController;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class GeneralEvents implements Listener {
    private TrinityEssentials plugin;

    public GeneralEvents(TrinityEssentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location spawnLocation = plugin.getLocalesConfig().getLocation("spawn");

        event.setJoinMessage("§8[§a+§8] §7" + player.getName());

        if(spawnLocation == null) {
            player.teleport(player.getWorld().getSpawnLocation());
            return;
        }

        player.teleport(spawnLocation);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage("§8[§c-§8] §7" + player.getName());
    }

    @EventHandler(ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String cmd = event.getMessage().substring(1).split(" ")[0].toLowerCase();

        if(cmd.equalsIgnoreCase("warps")) {
            event.setCancelled(true);

            if(!player.hasPermission("trinity.essentials.warps")) {
                player.sendMessage(ChatColor.RED + "Sem permissão.");
                return;
            }

            try {
                WarpController controller = new WarpController();
                List<String> warpsName = controller.getAllWarpsName(TrinityEssentials.getInstance());
                String warpsNameConcatenated = String.join(", ", warpsName);

                player.sendMessage(ChatColor.YELLOW + "Warps disponíveis: §7"+warpsNameConcatenated);
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Ocorreu um erro ao listar as warps. Tente novamente.");
                throw new RuntimeException(e);
            }
        }

        List<String> blockedCommands = TrinityEssentials.getInstance().getConfig().getStringList("blocked-commands");

        if(blockedCommands != null) {
            if (blockedCommands.contains(cmd)) {
                if(player.hasPermission("trinity.essentials.bypass.blockedcommand")) {
                    return;
                }

                event.setCancelled(true);
                event.getPlayer().sendMessage("§cEste comando está desabilitado.");
            }
        }
    }
}
