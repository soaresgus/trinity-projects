package com.nemiqstudios.trinityRandomTeleport;

import com.nemiqstudios.trinityRandomTeleport.commands.RandomTeleportCommand;
import com.nemiqstudios.trinityRandomTeleport.events.PortalTeleportEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class TrinityRandomTeleport extends JavaPlugin {
    private static TrinityRandomTeleport instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();

        getCommand("randomteleport").setExecutor(new RandomTeleportCommand());

        Bukkit.getPluginManager().registerEvents(new PortalTeleportEvent(), this);

        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "[TrinityRandomTeleport] Plugin iniciado com sucesso!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "[TrinityRandomTeleport] Plugin encerrado com sucesso!");
    }

    public static TrinityRandomTeleport getInstance() { return instance; }

}
