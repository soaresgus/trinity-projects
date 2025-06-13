package com.nemiqstudios.trinityEssentials.utils.home;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Home {

    private final String playerUUID;
    private final String playerUsername;
    private final String homeName;
    private final boolean isPublic;
    private final String worldName;
    private final double x;
    private final double y;
    private final double z;
    private final float pitch;
    private final float yaw;

    public Home(String playerUUID, String playerUsername, String homeName, boolean isPublic, String worldName,
                double x, double y, double z, float pitch, float yaw) {
        this.playerUUID = playerUUID;
        this.playerUsername = playerUsername;
        this.homeName = homeName;
        this.isPublic = isPublic;
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public Location getLocation() {
        return new Location(
                Bukkit.getWorld(worldName),
                x, y, z, yaw, pitch
        );
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerUUID);
    }

    public String getHomeName() {
        return homeName;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getWorldName() {
        return worldName;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }
}