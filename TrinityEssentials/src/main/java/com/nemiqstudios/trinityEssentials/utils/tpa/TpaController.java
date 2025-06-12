package com.nemiqstudios.trinityEssentials.utils.tpa;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TpaController {
    public static ArrayList<Player> playersListHasSendTpa = new ArrayList<>();
    public static List<TpaRequest> tpaRequests = new ArrayList<>();
    public static int cooldown = 30;
}
