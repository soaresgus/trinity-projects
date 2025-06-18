package com.nemiqstudios.trinityRandomTeleport.utils;

import com.nemiqstudios.trinityRandomTeleport.TrinityRandomTeleport;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTeleportController {
    private static List<Player> playersUsedRandomTeleport = new ArrayList<>();
    private static int cooldown = 120;

    public void teleportPlayer(Player player) {
        try {
            if(playersUsedRandomTeleport.contains(player)) {
                player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                player.sendMessage(ChatColor.RED + "Aguarde para utilizar o teleporte aleatório novamente.");
                return;
            }

            player.sendMessage(ChatColor.GREEN + "Aguarde, gerando terreno...");

            String configWorld = TrinityRandomTeleport.getInstance().getConfig().getString("teleportWorld");
            int minCoord = TrinityRandomTeleport.getInstance().getConfig().getInt("minCoord");
            int maxCoord = TrinityRandomTeleport.getInstance().getConfig().getInt("maxCoord");

            if (configWorld == null) {
                configWorld = "world";
            }

            if (minCoord == 0) {
                minCoord = -1000;
            }

            if (maxCoord == 0) {
                maxCoord = 10000;
            }

            World world = Bukkit.getWorld(configWorld);
            double randomX = ThreadLocalRandom.current().nextDouble(minCoord, maxCoord);
            double randomZ = ThreadLocalRandom.current().nextDouble(minCoord, maxCoord);
            int safeY = world.getHighestBlockYAt((int) randomX, (int) randomZ) + 2;

            Location location = new Location(world, randomX, safeY, randomZ);
            PotionEffect resistenceEffect = new PotionEffect(PotionEffectType.RESISTANCE, 10 * 20, Integer.MAX_VALUE);

            player.teleport(location);
            player.addPotionEffect(resistenceEffect);
            player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
            playersUsedRandomTeleport.add(player);

            new BukkitRunnable() {
                @Override
                public void run() {
                    playersUsedRandomTeleport.remove(player);
                }
            }.runTaskLater(TrinityRandomTeleport.getInstance(), cooldown * 20);
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "Ocorreu uma falha ao teleportar.");
            throw new RuntimeException(e);
        }
    }
}
