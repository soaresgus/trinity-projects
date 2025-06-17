package com.nemiqstudios.trinityEssentials.events;

import com.nemiqstudios.trinityEssentials.utils.back.BackController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class BackEvents implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        BackController controller = new BackController();
        if (!controller.getPlayersWhoDied().contains(player)) {
            controller.setPlayerLastLocation(player);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        BackController controller = new BackController();
        controller.setPlayerLastLocation(player);
        controller.setPlayerInDeathList(player);
    }
}
