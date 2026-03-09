package com.dezc.topka.mixins;

import com.dezc.topka.ShowInvisController;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @Inject(method = "shouldRender", at = @At("RETURN"), cancellable = true)
    private <E extends Entity> void onShouldRender(E entity, Frustum frustum,
            double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if (ShowInvisController.isActive() && entity.isInvisible()) {
            cir.setReturnValue(true);
        }
    }
}
