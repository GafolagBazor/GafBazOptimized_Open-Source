package org.gafolagbazor.gafbazoptimized.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class TextureAtlasRenderMixin {

    @Inject(method = "allChanged", at = @At("HEAD"))
    private void gafbaz$optimizeBlockOcclusion(CallbackInfo ci) {
        com.mojang.blaze3d.systems.RenderSystem.assertOnRenderThread();
        com.mojang.blaze3d.systems.RenderSystem.enableCull();
        com.mojang.blaze3d.systems.RenderSystem.setShaderFogStart(999999f);
        com.mojang.blaze3d.systems.RenderSystem.setShaderFogEnd(999999f);
    }
}
