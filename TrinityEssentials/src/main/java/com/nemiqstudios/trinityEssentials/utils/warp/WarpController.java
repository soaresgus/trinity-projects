package com.nemiqstudios.trinityEssentials.utils.warp;

import com.nemiqstudios.trinityEssentials.TrinityEssentials;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class WarpController {

    public Location getWarpLocationByName(TrinityEssentials plugin, String name) {
        try {
            FileConfiguration config = plugin.getLocalesConfig();

            return config.getLocation("warps." + name + ".location");
        } catch (Exception e) {
            plugin.getLogger().info(e.getMessage());
        }

        return null;
    }

    public List<String> getAllWarpsName(TrinityEssentials plugin) {
        try {
            FileConfiguration config = plugin.getLocalesConfig();
            if (config.isConfigurationSection("warps")) {
                return config.getConfigurationSection("warps")
                        .getKeys(false)
                        .stream()
                        .map(key -> config.getString("warps." + key + ".name"))
                        .filter(name -> name != null)
                        .toList();
            }
        } catch (Exception e) {
            plugin.getLogger().info(e.getMessage());
        }

        return java.util.Collections.emptyList();
    }

    public void deleteWarp(TrinityEssentials plugin, String name) {
        try {
            FileConfiguration config = plugin.getLocalesConfig();

            config.set("warps." + name, null);
            plugin.saveLocales();
        } catch (Exception e) {
            plugin.getLogger().info(e.getMessage());
        }
    }

    public void setWarp(TrinityEssentials plugin, String name, Location location) {
        try {
            FileConfiguration config = plugin.getLocalesConfig();

            config.set("warps." + name + ".name", name);
            config.set("warps." + name + ".location", location);
            plugin.saveLocales();
        } catch (Exception e) {
            plugin.getLogger().info(e.getMessage());
        }
    }
}
