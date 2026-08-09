package org.gafolagbazor.gafbazoptimized;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;

public class GafBazOptimizeRender {

    private static long lastRenderTime = 0;

    @SubscribeEvent
    public void onRenderEntity(RenderLivingEvent.Pre<?, ?> event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        Entity entity = event.getEntity();
        if (entity == mc.player) return;

        Vec3 entityPos = entity.position();
        Vec3 toEntity = entityPos.subtract(mc.player.position());

        if (toEntity.lengthSqr() > 144) {
            Vec3 lookVec = mc.player.getLookAngle();
            double dotProduct = lookVec.dot(toEntity.normalize());

            if (dotProduct < 0.7) {
                event.setCanceled(true);
            }
        }
    }

    public static void forceUnlockFramerate() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options != null) {
            mc.options.framerateLimit().set(260);
            mc.options.enableVsync().set(false);

            System.setProperty("neoforge.render.chunk_updates", "1");
        }
    }
}
