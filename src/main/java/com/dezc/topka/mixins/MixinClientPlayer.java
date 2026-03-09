package com.dezc.topka.mixins;

import com.dezc.topka.FreeCamController;
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
        ClientPlayerEntity p = (ClientPlayerEntity)(Object)this;

        // Двигаем виртуальную камеру по инпутам игрока
        FreeCamController.tick(
            p.input.movementForward,
            p.input.movementSideways,
            p.input.playerInput.jump(),
            p.input.playerInput.sneak(),
            p.getYaw(),
            p.getPitch()
        );

        // Обнуляем движение реального игрока — тело стоит на месте
        p.input.movementForward  = 0f;
        p.input.movementSideways = 0f;
    }
}
