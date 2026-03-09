package com.dezc.topka.mixins;

import com.dezc.topka.HitboxController;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class MixinEntity {

    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void onGetDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if (HitboxController.isDefault()) return;
        Entity self = (Entity)(Object)this;
        // Не трогаем собственного игрока
        net.minecraft.client.MinecraftClient mc = net.minecraft.client.MinecraftClient.getInstance();
        if (mc.player != null && mc.player == self) return;

        EntityDimensions orig = cir.getReturnValue();
        float s = HitboxController.getScale();
        cir.setReturnValue(EntityDimensions.changing(orig.width() * s, orig.height() * s));
    }
}
