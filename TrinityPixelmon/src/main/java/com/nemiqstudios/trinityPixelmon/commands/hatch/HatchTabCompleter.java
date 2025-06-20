package com.nemiqstudios.trinityPixelmon.commands.hatch;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class HatchTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            completions.add("1");
            completions.add("2");
            completions.add("3");
            completions.add("4");
            completions.add("5");
            completions.add("6");

            return completions;
        }
        return null;
    }
}
