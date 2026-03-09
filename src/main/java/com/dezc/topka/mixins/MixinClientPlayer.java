package com.dezc.topka.mixins;

import com.dezc.topka.FreeCam;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayer {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (!FreeCam.isActive()) return;
        ClientPlayerEntity p = (ClientPlayerEntity)(Object)this;
        FreeCam.move(
            p.input.movementForward,
            p.input.movementSideways,
            p.input.playerInput.jump(),
            p.input.playerInput.sneak()
        );
        FreeCam.yaw = p.getYaw();
        FreeCam.pitch = p.getPitch();
    }
}
