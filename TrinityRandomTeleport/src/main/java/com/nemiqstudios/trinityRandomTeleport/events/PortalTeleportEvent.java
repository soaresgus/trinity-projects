package com.nemiqstudios.trinityRandomTeleport.events;

import com.nemiqstudios.trinityRandomTeleport.utils.RandomTeleportController;
import org.bukkit.PortalType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PortalTeleportEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onEndPortalTeleport(PlayerPortalEvent event) {
        Player player = event.getPlayer();

        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)) {
            event.setCancelled(true);
            RandomTeleportController controller = new RandomTeleportController();
            controller.teleportPlayer(player);
        }

    }
}
