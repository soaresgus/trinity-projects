package com.nemiqstudios.trinityPixelmon.utils;

import org.bukkit.ChatColor;

public enum Colors {
    DARK_BLUE(ChatColor.DARK_BLUE, "AZUL_ESCURO"),
    BLACK(ChatColor.BLACK, "PRETO"),
    DARK_GREEN(ChatColor.DARK_GREEN, "VERDE_ESCURO"),
    DARK_AQUA(ChatColor.DARK_AQUA, "AGUA_MARINHA_ESCURO"),
    DARK_RED(ChatColor.DARK_RED, "VERMELHO_ESCURO"),
    DARK_PURPLE(ChatColor.DARK_PURPLE, "ROXO_ESCURO"),
    GOLD(ChatColor.GOLD, "OURO"),
    GRAY(ChatColor.GRAY, "CINZA"),
    DARK_GRAY(ChatColor.DARK_GRAY, "CINZA_ESCURO"),
    BLUE(ChatColor.BLUE, "AZUL"),
    GREEN(ChatColor.GREEN, "VERDE"),
    AQUA(ChatColor.AQUA, "AGUA_MARINHA"),
    RED(ChatColor.RED, "VERMELHO"),
    LIGHT_PURPLE(ChatColor.LIGHT_PURPLE, "ROXO_CLARO"),
    YELLOW(ChatColor.YELLOW, "AMARELO"),
    WHITE(ChatColor.WHITE, "BRANCO");

    private final ChatColor chatColor;
    private final String portugueseTranslation;

    Colors(ChatColor chatColor, String portugueseTranslation) {
        this.chatColor = chatColor;
        this.portugueseTranslation = portugueseTranslation;
    }

    public String getPortugueseTranslation() {
        return portugueseTranslation;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public ChatColor getChatColorByPortugueseTranslation(String portugueseTranslation) {
        for (Colors color : Colors.values()) {
            if (color.getPortugueseTranslation().equalsIgnoreCase(portugueseTranslation)) {
                return color.chatColor;
            }
        }
        return null;
    }
}
