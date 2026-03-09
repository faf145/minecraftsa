package com.dezc.betterfps;

import net.minecraft.client.MinecraftClient;

public class FreeCamController {

    public static boolean active = false;
    public static double x, y, z;
    public static float yRot, xRot;
    public static float speed = 0.1f;

    public static void toggle() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        active = !active;

        if (active) {
            x = mc.player.getX();
            y = mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose());
            z = mc.player.getZ();
            yRot = mc.player.getYaw();
            xRot = mc.player.getPitch();
        }
    }

    public static boolean isActive() {
        return active;
    }

    public static void moveCamera(float forward, float strafe, boolean up, boolean down) {
        double yawRad = Math.toRadians(yRot);
        double pitchRad = Math.toRadians(xRot);

        x += -Math.sin(yawRad) * Math.cos(pitchRad) * forward * speed;
        y += -Math.sin(pitchRad) * forward * speed;
        z += Math.cos(yawRad) * Math.cos(pitchRad) * forward * speed;

        x += Math.cos(yawRad) * strafe * speed;
        z += Math.sin(yawRad) * strafe * speed;

        if (up) y += speed;
        if (down) y -= speed;
    }
}
