package com.nemiqstudios.trinityEssentials;

import com.nemiqstudios.trinityEssentials.commands.home.*;
import com.nemiqstudios.trinityEssentials.commands.spawn.SetspawnCommand;
import com.nemiqstudios.trinityEssentials.commands.spawn.SpawnCommand;
import com.nemiqstudios.trinityEssentials.commands.tpa.TpaCommand;
import com.nemiqstudios.trinityEssentials.commands.tpa.TpacancelCommand;
import com.nemiqstudios.trinityEssentials.commands.tpa.TpacceptCommand;
import com.nemiqstudios.trinityEssentials.commands.tpa.TpadenyCommand;
import com.nemiqstudios.trinityEssentials.commands.warp.DelwarpCommand;
import com.nemiqstudios.trinityEssentials.commands.warp.SetwarpCommand;
import com.nemiqstudios.trinityEssentials.commands.warp.WarpCommand;
import com.nemiqstudios.trinityEssentials.commands.warp.WarpsCommand;
import com.nemiqstudios.trinityEssentials.events.GeneralEvents;
import com.nemiqstudios.trinityEssentials.utils.database.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public final class TrinityEssentials extends JavaPlugin {
    private static TrinityEssentials instance;
    private FileConfiguration localesConfig = null;
    private File localesFile = null;

    public DatabaseManager dbManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();
        saveDefaultLocales();

        String host = this.getConfig().getString("database.host");
        int port = this.getConfig().getInt("database.port");
        String db = this.getConfig().getString("database.db");
        String user = this.getConfig().getString("database.user");
        String password = this.getConfig().getString("database.password");

        dbManager = new DatabaseManager(host, port, db, user, password);
        dbManager.connect();

        dbManager.createEssentialsHomesTable();

        getCommand("setspawn").setExecutor(new SetspawnCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());

        getCommand("tpa").setExecutor(new TpaCommand());
        getCommand("tpaccept").setExecutor(new TpacceptCommand());
        getCommand("tpadeny").setExecutor(new TpadenyCommand());
        getCommand("tpacancel").setExecutor(new TpacancelCommand());

        getCommand("home").setExecutor(new HomeCommand());
        getCommand("homes").setExecutor(new HomesCommand());
        getCommand("sethome").setExecutor(new SethomeCommand());
        getCommand("delhome").setExecutor(new DelhomeCommand());
        getCommand("public").setExecutor(new PublicCommand());
        getCommand("private").setExecutor(new PrivateCommand());

        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("warps").setExecutor(new WarpsCommand());
        getCommand("setwarp").setExecutor(new SetwarpCommand());
        getCommand("delwarp").setExecutor(new DelwarpCommand());

        Bukkit.getPluginManager().registerEvents(new GeneralEvents(this), this);

        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "[TrinityEssentials] Plugin iniciado com sucesso!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        dbManager.disconnect();
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
