package com.nemiqstudios.trinityNametag;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public final class TrinityNametag extends JavaPlugin implements Listener {

    private LuckPerms luckPerms;

    @Override
    public void onEnable() {
        // Plugin startup logic
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }

        Bukkit.getPluginManager().registerEvents(this, this);

        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "[TrinityNametag] Plugin iniciado com sucesso!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "[TrinityNametag] Plugin encerrado com sucesso.");
    }

    public void setPlayerNametag(Player player, String prefix, String groupName) {
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        String replacedPrefix = prefix != null ? prefix.replaceAll("&", "§") : "";
        String safeGroupName = groupName.length() > 16 ? groupName.substring(0, 16) : groupName;

        // Remove o jogador apenas dos times em que ele está
        for (Team t : board.getTeams()) {
            if (t.hasEntry(player.getName())) {
                try {
                    t.removeEntry(player.getName());
                } catch (IllegalStateException ignored) {
                    // Evita crash caso o protocolo já tenha removido
                }
            }
        }

        Team team = board.getTeam(safeGroupName);
        if (team == null) {
            try {
                team = board.registerNewTeam(safeGroupName);
            } catch (IllegalArgumentException ignored) {
                // Time já existe, ignora
                team = board.getTeam(safeGroupName);
            }
        }

        if (board.getTeam(groupName).hasEntry(player.getName())
                && board.getEntryTeam(player.getName()) == team) {
            team.removeEntry(player.getName());
        }

        if (team != null) {
            team.setPrefix(replacedPrefix);

            if (replacedPrefix.length() >= 2 && replacedPrefix.charAt(0) == '§') {
                ChatColor color = ChatColor.getByChar(replacedPrefix.charAt(1));
                if (color != null && color.isColor()) {
                    team.setColor(color);
                }
            }

            if (!team.hasEntry(player.getName())) {
                try {
                    team.addEntry(player.getName());
                } catch (IllegalStateException ignored) {
                    // Evita crash caso o protocolo já tenha adicionado
                }
            }
        }
    }

    public boolean isPlayerInGroup(Player player, String group) {
        if (luckPerms == null) return false;
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user == null) return false;
        return user.getPrimaryGroup().equalsIgnoreCase(group);
    }

    public void setPlayerTag(Player player) {
        for (Group group : luckPerms.getGroupManager().getLoadedGroups()) {
            String groupName = group.getName();
            String prefix = group.getCachedData().getMetaData().getPrefix();

            if(isPlayerInGroup(player, groupName)) {
                setPlayerNametag(player, prefix, groupName);
                break;
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        setPlayerTag(player);
    }
}
