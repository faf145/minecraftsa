package com.dezc.topka.mixins;

import com.dezc.topka.FreeCamController;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class MixinCamera {
    @Shadow protected abstract void setPos(double x, double y, double z);
    @Shadow protected abstract void setRotation(float yaw, float pitch);

    @Inject(method = "update", at = @At("TAIL"))
    private void onUpdate(BlockView area, Entity entity, boolean thirdPerson,
                          boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (!FreeCamController.isActive()) return;
        setPos(FreeCamController.camX, FreeCamController.camY, FreeCamController.camZ);
        setRotation(FreeCamController.camYaw, FreeCamController.camPitch);
    }
}
