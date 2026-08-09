package org.gafolagbazor.gafbazoptimized;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;

public class GafBazOptimizeExplosion {

    @SubscribeEvent
    public void onRecipesUpdated(RecipesUpdatedEvent event) {
    }

    public static boolean shouldSkyExplosion(Vec3 pos) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return true;

        Vec3 playerPos = mc.player.position();
        double distSqr = playerPos.distanceToSqr(pos);

        if (distSqr > 4096) {
            return false;
        }

        if (distSqr > 256) {
            Vec3 lookVec = mc.player.getLookAngle();
            Vec3 toExplosion = pos.subtract(playerPos).normalize();
            double dot = lookVec.dot(toExplosion);
            if (dot < 0.3) {
                return false;
            }
        }

        return true;
    }
}
