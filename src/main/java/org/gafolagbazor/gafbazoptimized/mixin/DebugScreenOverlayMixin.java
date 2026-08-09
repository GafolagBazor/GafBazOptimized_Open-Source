package org.gafolagbazor.gafbazoptimized.mixin;

import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DebugScreenOverlay.class)
public class DebugScreenOverlayMixin {

    @Inject(method = "getSystemInformation", at = @At("RETURN"))
    private void gafbaz$addCustomBrandUnderGPU(CallbackInfoReturnable<List<String>> cir) {
        List<String> info = cir.getReturnValue();
        if (info != null) {
            int gpuIndex = -1;
            for (int i = 0; i < info.size(); i++) {
                String line = info.get(i);
                if (line.contains("GeForce") || line.contains("RTX") || line.contains("GT") || line.contains("GTX") || line.contains("Radeon") || line.contains("Graphics")) {
                    gpuIndex = i;
                    break;
                }
            }
            if (gpuIndex != -1) {
                info.add(gpuIndex + 1, "§a§l[GafBaz Render] (1.1-TEST)");
            } else {
                info.add("§a§l[GafBaz Render] (1.1-ALPHA)");
            }
        }
    }
}
