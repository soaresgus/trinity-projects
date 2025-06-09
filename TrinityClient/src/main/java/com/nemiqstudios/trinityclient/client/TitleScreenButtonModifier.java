package com.nemiqstudios.trinityclient.client;

import com.nemiqstudios.trinityclient.Trinityclient;
import net.minecraft.Util;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.TitleScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = Trinityclient.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class TitleScreenButtonModifier {
    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof TitleScreen) {
            for (GuiEventListener listener : event.getListenersList()) {
                if (listener instanceof AbstractWidget widget && widget instanceof Button button && button.getMessage().getString().equals("Discord")) {
                    Button newButton = Button.builder(button.getMessage(), b -> Util.getPlatform().openUri("https://discord.gg/trinitybrasil"))
                            .bounds(button.getX(), button.getY(), button.getWidth(), button.getHeight())
                            .build();
                    event.removeListener(button);
                    event.addListener(newButton);
                    break; // Evita ConcurrentModificationException
                }
            }
        }
    }
}