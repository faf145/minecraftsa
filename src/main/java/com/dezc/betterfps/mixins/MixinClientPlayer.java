package com.dezc.betterfps.mixins;

import com.dezc.betterfps.FreeCamController;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayer {

    @Shadow public Input input;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (!FreeCamController.isActive()) return;

        ClientPlayerEntity player = (ClientPlayerEntity)(Object)this;
        float yaw = (float) Math.toRadians(player.getYaw());

        double moveX = 0, moveY = 0, moveZ = 0;

        float forward = input.movementForward;
        float side = input.movementSideways;

        moveX += -Math.sin(yaw) * forward - Math.cos(yaw) * side;
        moveZ += Math.cos(yaw) * forward - Math.sin(yaw) * side;

        if (input.jumping) moveY += 1;
        if (input.sneaking) moveY -= 1;

        FreeCamController.move(moveX, moveY, moveZ);
        FreeCamController.yRot = player.getYaw();
        FreeCamController.xRot = player.getPitch();
    }
}
