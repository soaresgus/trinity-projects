package com.nemiqstudios.trinityEssentials;

import com.nemiqstudios.trinityEssentials.commands.spawn.SetspawnCommand;
import com.nemiqstudios.trinityEssentials.commands.spawn.SpawnCommand;
import com.nemiqstudios.trinityEssentials.commands.tpa.TpaCommand;
import com.nemiqstudios.trinityEssentials.events.GeneralEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class TrinityEssentials extends JavaPlugin {
    private static TrinityEssentials instance;
    private FileConfiguration localesConfig = null;
    private File localesFile = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();
        saveDefaultLocales();

        getCommand("setspawn").setExecutor(new SetspawnCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));

        getCommand("tpa").setExecutor(new TpaCommand());

        Bukkit.getPluginManager().registerEvents(new GeneralEvents(this), this);

        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "[TrinityEssentials] Plugin iniciado com sucesso!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "[TrinityEssentials] Plugin encerrado com sucesso!");
    }

    private void saveDefaultLocales() {
        if (localesFile == null) {
            localesFile = new File(getDataFolder(), "locales.yml");
        }

        if (!localesFile.exists()) {
            saveResource("locales.yml", false);
        }

        localesConfig = YamlConfiguration.loadConfiguration(localesFile);
    }

    public FileConfiguration getLocalesConfig() {
        return localesConfig;
    }

    public void saveLocales() {
        if (localesConfig == null || localesFile == null) return;

        try {
            localesConfig.save(localesFile);
        } catch (IOException e) {
            getLogger().severe("Erro ao salvar o arquivo locales.yml:");
            e.printStackTrace();
        }
    }

    public static TrinityEssentials getInstance() {
        return instance;
    }
}
