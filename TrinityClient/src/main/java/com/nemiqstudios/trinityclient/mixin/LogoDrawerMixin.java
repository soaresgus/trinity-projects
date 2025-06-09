package com.nemiqstudios.trinityclient.mixin;

import net.minecraft.client.gui.components.LogoRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.GuiGraphics;

@Mixin(LogoRenderer.class)
public abstract class LogoDrawerMixin {
    @Inject(
            method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IF)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void onRenderLogo(GuiGraphics graphics, int screenWidth, float alpha, CallbackInfo ci) {
        ci.cancel();
    }
}
