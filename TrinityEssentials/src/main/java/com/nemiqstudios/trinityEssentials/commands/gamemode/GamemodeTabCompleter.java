package com.nemiqstudios.trinityEssentials.commands.gamemode;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GamemodeTabCompleter implements TabCompleter {

    public static final List<String> VALID_MODES = Arrays.asList(
            "survival", "0", "creative", "1", "spectator", "3"
    );

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (args.length == 1) {
            return VALID_MODES;
        }

        if (args.length == 2) {
            return null;
        }
        return Collections.emptyList();
    }
}
