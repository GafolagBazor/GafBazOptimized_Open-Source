package org.gafolagbazor.gafbazoptimized.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class WeatherRenderMixin {

    @Inject(method = "renderSnowAndRain", at = @At("HEAD"), cancellable = true)
    private void gafbaz$stopWeatherRender(LightTexture lightTexture, float partialTick, double camX, double camY, double camZ, CallbackInfo ci) {
        ci.cancel();
    }
}
