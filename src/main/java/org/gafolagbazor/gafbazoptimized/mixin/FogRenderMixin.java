package org.gafolagbazor.gafbazoptimized.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class FogRenderMixin {

    @Inject(method = "setupFog", at = @At("RETURN"))
    private static void gafbaz$disableFogAfterSetup(CallbackInfo ci) {
        RenderSystem.setShaderFogStart(999999f);
        RenderSystem.setShaderFogEnd(999999f);
    }
}
