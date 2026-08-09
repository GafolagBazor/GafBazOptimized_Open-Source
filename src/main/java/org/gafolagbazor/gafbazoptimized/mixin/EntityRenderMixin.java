package org.gafolagbazor.gafbazoptimized.mixin;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private <E extends Entity> void gafbaz$limitTntRender(E entity, double x, double y, double z, float rotation, float partialTicks, com.mojang.blaze3d.vertex.PoseStack matrixStack, net.minecraft.client.renderer.MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        if (entity instanceof PrimedTnt) {
            if (entity.tickCount % 2 != 0) {
                ci.cancel();
            }
        }
    }
}
