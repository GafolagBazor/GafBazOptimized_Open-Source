package org.gafolagbazor.gafbazoptimized;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod("gafbazoptimized")
public class Main {

    public static boolean showFpsCounter = true;

    public Main(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.register(new GafBazOptimizeExplosion());
        NeoForge.EVENT_BUS.register(new GafBazOptimizeFPS());
        NeoForge.EVENT_BUS.register(new GafBazOptimizeRender());
        NeoForge.EVENT_BUS.register(this);

        GafBazOptimizeRender.forceUnlockFramerate();
    }

    @SubscribeEvent
    public void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        if (mc.options != null && mc.options.enableVsync().get()) {
            mc.options.framerateLimit().set(260);
            mc.options.enableVsync().set(false);
        }

        if (showFpsCounter && !mc.getDebugOverlay().showDebugScreen()) {
            GuiGraphics graphics = event.getGuiGraphics();
            Font font = mc.font;

            int currentFps = mc.getFps();
            String fpsText = "FPS: " + currentFps;

            double mspt = 0.0;
            double tps = 20.0;

            MinecraftServer server = mc.getSingleplayerServer();
            if (server != null) {
                long[] tickTimes = server.getTickTimesNanos();
                if (tickTimes != null && tickTimes.length > 0) {
                    long totalNanos = 0;
                    for (long time : tickTimes) {
                        totalNanos += time;
                    }
                    mspt = (totalNanos / (double) tickTimes.length) * 1.0E-6;
                    tps = Math.min(20.0, 1000.0 / Math.max(50.0, mspt));
                }
            } else if (mc.getCurrentServer() != null) {
                mspt = 1000.0 / Math.max(1, currentFps);
                tps = 20.0;
            }

            String serverText = String.format("TPS: %.1f | MSPT: %.1f", tps, mspt);

            int posX = 5;
            int posY = 5;

            int textWidth1 = font.width(fpsText);
            int textWidth2 = font.width(serverText);
            int maxWidth = Math.max(textWidth1, textWidth2);

            graphics.fill(posX - 2, posY - 2, posX + maxWidth + 2, posY + (font.lineHeight * 2) + 3, 0x90101010);

            int fpsColor = currentFps > 60 ? 0x00FF00 : (currentFps < 30 ? 0xFF0000 : 0xFFFF00);
            graphics.drawString(font, fpsText, posX, posY, fpsColor, true);

            int serverColor = tps > 18.0 ? 0x55FFFF : (tps < 15.0 ? 0xFF5555 : 0xFFFF55);
            graphics.drawString(font, serverText, posX, posY + font.lineHeight + 2, serverColor, true);
        }
    }
}
