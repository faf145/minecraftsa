package com.dezc.betterfps.mixins;

import com.dezc.betterfps.ShowInvisController;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @Inject(method = "shouldRender", at = @At("RETURN"), cancellable = true)
    private void onShouldRender(Entity entity, net.minecraft.client.frustum.Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if (ShowInvisController.isActive() && entity.isInvisible()) {
            cir.setReturnValue(true);
        }
    }
}
