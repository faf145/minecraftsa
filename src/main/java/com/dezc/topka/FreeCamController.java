package com.dezc.topka;

import net.minecraft.client.MinecraftClient;

public class FreeCamController {
    public static boolean active = false;

    // Позиция камеры freecam
    public static double camX, camY, camZ;
    public static float  camYaw, camPitch;

    // Скорость движения (как в оригинале: acceleration/maxSpeed)
    private static float forwardVel = 0f;
    private static float leftVel    = 0f;
    private static float upVel      = 0f;

    private static final float ACCEL    = 0.08f;
    private static final float MAX_SPEED = 0.6f;
    private static final float SLOWDOWN = 0.8f;

    public static void toggle() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;
        active = !active;
        if (active) {
            camX   = mc.player.getX();
            camY   = mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose());
            camZ   = mc.player.getZ();
            camYaw  = mc.player.getYaw();
            camPitch = mc.player.getPitch();
            forwardVel = 0; leftVel = 0; upVel = 0;
        }
    }

    public static boolean isActive() { return active; }

    // Вызывается из MixinClientPlayer каждый тик — обновляет позицию камеры
    public static void tick(float forward, float strafe, boolean jump, boolean sneak, float yaw, float pitch) {
        camYaw   = yaw;
        camPitch = pitch;

        double yRad = Math.toRadians(camYaw);
        double pRad = Math.toRadians(camPitch);

        // Acceleration как в оригинале
        forwardVel = clamp(forwardVel + forward * ACCEL, -MAX_SPEED, MAX_SPEED) * SLOWDOWN;
        leftVel    = clamp(leftVel    + strafe  * ACCEL, -MAX_SPEED, MAX_SPEED) * SLOWDOWN;
        upVel      = (jump ? ACCEL : sneak ? -ACCEL : 0f);

        camX += -Math.sin(yRad) * Math.cos(pRad) * forwardVel
              + Math.cos(yRad) * leftVel;
        camY += -Math.sin(pRad) * forwardVel + upVel;
        camZ +=  Math.cos(yRad) * Math.cos(pRad) * forwardVel
              + Math.sin(yRad) * leftVel;
    }

    private static float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }
}
