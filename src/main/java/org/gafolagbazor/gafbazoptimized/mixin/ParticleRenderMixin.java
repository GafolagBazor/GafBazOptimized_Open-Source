package org.gafolagbazor.gafbazoptimized.mixin;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public class ParticleRenderMixin {

    @Unique
    private static int gafbaz$tickParticles = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    private void gafbaz$resetParticleCounter(CallbackInfo ci) {
        gafbaz$tickParticles = 0;
    }

    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void gafbaz$limitParticlesOnSpawn(Particle particle, CallbackInfo ci) {
        gafbaz$tickParticles++;
        if (gafbaz$tickParticles > 400) {
            ci.cancel();
        }
    }
}
