package com.nemiqstudios.trinityEssentials.events;

import com.nemiqstudios.trinityEssentials.TrinityEssentials;
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

        event.setJoinMessage(null);

        if(spawnLocation == null) {
            player.teleport(player.getWorld().getSpawnLocation());
            return;
        }

        player.teleport(spawnLocation);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
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
