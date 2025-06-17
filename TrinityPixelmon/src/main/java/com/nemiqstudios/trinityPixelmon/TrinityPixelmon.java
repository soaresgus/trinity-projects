package com.nemiqstudios.trinityPixelmon;

import com.nemiqstudios.trinityPixelmon.commands.pokeheal.PokehealCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class TrinityPixelmon extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("pokeheal").setExecutor(new PokehealCommand());

        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "[TrinityPixelmon] Plugin iniciado com sucesso!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "[TrinityPixelmon] Plugin encerrado com sucesso!");
    }
}
