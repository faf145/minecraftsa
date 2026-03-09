package com.dezc.topka.mixins;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Заглушка — GameRenderer mixin нужен для совместимости,
// хитбоксы делаются через MixinEntity.getDimensions()
@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(RenderTickCounter counter, boolean tick, CallbackInfo ci) {
        // intentionally empty
    }
}
