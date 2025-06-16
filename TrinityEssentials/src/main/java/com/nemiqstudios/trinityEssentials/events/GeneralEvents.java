package com.nemiqstudios.trinityEssentials.events;

import com.nemiqstudios.trinityEssentials.TrinityEssentials;
import com.nemiqstudios.trinityEssentials.utils.warp.WarpController;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

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

    @EventHandler(priority = EventPriority.HIGH)
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Location loc = plugin.getLocalesConfig().getLocation("spawn");

        if(loc == null || loc.getWorld() == null) {
            loc = player.getWorld().getSpawnLocation();
        }

        event.setRespawnLocation(loc);
        Location finalLoc = loc;
        new BukkitRunnable() {
            @Override
            public void run() {
                player.teleport(finalLoc);
            }
        }.runTaskLater(plugin, 1L);
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
