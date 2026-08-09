package org.gafolagbazor.gafbazoptimized.mixin;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.VideoSettingsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.gafolagbazor.gafbazoptimized.Main;

@Mixin(VideoSettingsScreen.class)
public abstract class VideoSettingsScreenMixin extends Screen {

    protected VideoSettingsScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void gafbaz$addFpsButton(CallbackInfo ci) {
        Button fpsButton = Button.builder(
                        Component.literal("Отображение FPS: " + (Main.showFpsCounter ? "ВКЛ" : "ВЫКЛ")),
                        (button) -> {
                            Main.showFpsCounter = !Main.showFpsCounter;
                            button.setMessage(Component.literal("Отображение FPS: " + (Main.showFpsCounter ? "ВКЛ" : "ВЫКЛ")));
                        }
                )
                .bounds(5, 5, 140, 20)
                .build();

        this.addRenderableWidget(fpsButton);
    }
}
