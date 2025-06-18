package com.nemiqstudios.trinityPixelmon.commands.pokecolor;

import com.nemiqstudios.trinityPixelmon.utils.Colors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class PokecolorTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            String input = args[0].toLowerCase();
            for (Colors color : Colors.values()) {
                String translation = color.getPortugueseTranslation().toLowerCase();
                if (translation.startsWith(input)) {
                    completions.add(color.getPortugueseTranslation());
                }
            }
            return completions;
        }
        return null;
    }
}