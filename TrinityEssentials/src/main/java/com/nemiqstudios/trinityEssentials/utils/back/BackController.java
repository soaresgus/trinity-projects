package com.nemiqstudios.trinityEssentials.utils.back;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BackController {
    private static HashMap<Player, Location> lastPlayersLocation = new HashMap<>();
    private static List<Player> playersWhoDied = new ArrayList<>();

    public void setPlayerLastLocation(Player player) {
        lastPlayersLocation.put(player, player.getLocation());
    }

    public Location getPlayerLastLocation(Player player) {
        if(lastPlayersLocation.containsKey(player)) {
            return lastPlayersLocation.get(player);
        }

        return null;
    }

    public void setPlayerInDeathList(Player player) {
        playersWhoDied.add(player);
    }

    public void removePlayerFromDeathList(Player player) {
        playersWhoDied.remove(player);
    }

    public List<Player> getPlayersWhoDied() {
        return playersWhoDied;
    }
}
