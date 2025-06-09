package com.nemiqstudios.trinityclient.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static org.lwjgl.opengl.GL11.*;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {
    // Injeção para renderizar o fundo customizado (atrás de tudo)
    @Inject(method = "render", at = @At("HEAD"), remap = false)
    private void renderCustomBackground(GuiGraphics graphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        ResourceLocation background = ResourceLocation.fromNamespaceAndPath(
                "trinityclient", "textures/gui/custom_background.png"
        );

        int textureWidth = 3840;
        int textureHeight = 2160;

        double scaleX = (double) screenWidth / textureWidth;
        double scaleY = (double) screenHeight / textureHeight;
        double scaleFactor = Math.max(scaleX, scaleY);

        int drawWidth = (int) (textureWidth * scaleFactor);
        int drawHeight = (int) (textureHeight * scaleFactor);

        int xOffset = (screenWidth - drawWidth) / 2;
        int yOffset = (screenHeight - drawHeight) / 2;

        graphics.blit(
                background,
                xOffset, yOffset,
                drawWidth, drawHeight,
                0, 0,
                textureWidth, textureHeight,
                textureWidth, textureHeight
        );
    }

    // Injeção para renderizar a logo customizada (se desejar que ela fique acima de alguns elementos)
    @Inject(method = "render", at = @At("TAIL"), remap = false)
    private void renderCustomLogo(GuiGraphics graphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        // Você pode definir a posição e escala da logo conforme preferir...
        ResourceLocation logo = ResourceLocation.fromNamespaceAndPath("trinityclient", "textures/gui/custom_logo.png");

        RenderSystem.setShaderTexture(0, logo);
        // Parâmetros para evitar repetição
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, 0x812F);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, 0x812F);

        int origWidth = 500;
        int origHeight = 500;
        float logoScaleFactor = 0.4F;
        int scaledWidth = (int) (origWidth * logoScaleFactor);
        int scaledHeight = (int) (origHeight * logoScaleFactor);

        int logoX = (screenWidth - scaledWidth) / 2;
        int logoY = 10;

        graphics.pose().pushPose();
        graphics.pose().translate(logoX, logoY, 0);
        graphics.pose().scale(logoScaleFactor, logoScaleFactor, logoScaleFactor);
        graphics.blit(logo, 0, 0, 0, 0, origWidth, origHeight);
        graphics.pose().popPose();
    }
}


