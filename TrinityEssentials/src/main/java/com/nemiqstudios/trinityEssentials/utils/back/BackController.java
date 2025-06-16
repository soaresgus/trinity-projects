package com.nemiqstudios.trinityEssentials.utils.back;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BackController {
    private static HashMap<Player, Location> lastPlayersLocation = new HashMap<>();

    public void setPlayerLastLocation(Player player) {
        lastPlayersLocation.put(player, player.getLocation());
    }

    public Location getPlayerLastLocation(Player player) {
        if(lastPlayersLocation.containsKey(player)) {
            return lastPlayersLocation.get(player);
        }

        return null;
    }
}
