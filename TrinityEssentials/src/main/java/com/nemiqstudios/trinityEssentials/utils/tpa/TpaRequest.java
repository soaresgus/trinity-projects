package com.nemiqstudios.trinityEssentials.utils.tpa;

import org.bukkit.entity.Player;

public class TpaRequest {
    private final Player sender;
    private final Player receiver;

    public TpaRequest(Player sender, Player receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public Player getSender() { return sender; }
    public Player getReceiver() { return receiver; }
}
