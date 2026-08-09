package org.gafolagbazor.gafbazoptimized;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class GafBazOptimizeFPS {

    @SubscribeEvent
    public void onEntityTick(LivingEvent.LivingTickEvent event) {
        Entity entity = event.getEntity();
        if (entity.level().isClientSide()) return;

        if (entity instanceof ItemEntity || entity instanceof AbstractArrow) {
            if (entity.tickCount % 4 != 0) {
                event.setCanceled(true);
            }
        } else if (entity.tickCount % 2 != 0) {
            net.minecraft.world.entity.player.Player closestPlayer = entity.level().getNearestPlayer(entity, 64.0);
            if (closestPlayer == null) {
                event.setCanceled(true);
            }
        }
    }
}
