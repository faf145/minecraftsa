package com.dezc.betterfps.mixins;

import com.dezc.betterfps.HitboxController;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {

    @Inject(method = "drawEntityOutline", at = @At("HEAD"), cancellable = true)
    private void onDrawHitbox(CallbackInfo ci) {
        // этот метод не используем
    }
}
