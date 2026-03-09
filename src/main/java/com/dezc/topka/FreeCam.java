package com.dezc.topka;

import net.minecraft.client.MinecraftClient;

public class FreeCam {
    public static boolean active = false;
    public static double x, y, z;
    public static float yaw, pitch;
    public static boolean cameraLock = false;
    public static boolean eyeLock = false;
    private static final float SPEED = 0.3f;

    public static void toggle() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;
        active = !active;
        if (active) {
            x = mc.player.getX();
            y = mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose());
            z = mc.player.getZ();
            yaw = mc.player.getYaw();
            pitch = mc.player.getPitch();
        }
    }

    public static boolean isActive() { return active; }

    public static void move(float forward, float strafe, boolean up, boolean down) {
        double yRad = Math.toRadians(yaw);
        double pRad = Math.toRadians(pitch);
        x += -Math.sin(yRad) * Math.cos(pRad) * forward * SPEED;
        y += -Math.sin(pRad) * forward * SPEED;
        z += Math.cos(yRad) * Math.cos(pRad) * forward * SPEED;
        x += Math.cos(yRad) * strafe * SPEED;
        z += Math.sin(yRad) * strafe * SPEED;
        if (up) y += SPEED;
        if (down) y -= SPEED;
    }
}
