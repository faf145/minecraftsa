package com.dezc.betterfps.mixins;

import com.dezc.betterfps.FreeCamController;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayer {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (!FreeCamController.isActive()) return;

        ClientPlayerEntity player = (ClientPlayerEntity)(Object)this;

        float forward = player.input.movementForward;
        float strafe = player.input.movementSideways;
        boolean up = player.input.playerInput.jump();
        boolean down = player.input.playerInput.sneak();

        FreeCamController.moveCamera(forward, strafe, up, down);
        FreeCamController.yRot = player.getYaw();
        FreeCamController.xRot = player.getPitch();
    }
}
