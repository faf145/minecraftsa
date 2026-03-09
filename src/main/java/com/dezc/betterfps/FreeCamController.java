package com.dezc.betterfps;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

public class FreeCamController {

    public static boolean active = false;
    public static double x, y, z;
    public static float yRot, xRot;
    private static float speed = 0.3f;

    public static void toggle() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        active = !active;

        if (active) {
            Vec3d pos = mc.player.getPos();
            x = pos.x;
            y = pos.y + mc.player.getEyeHeight(mc.player.getPose());
            z = pos.z;
            yRot = mc.player.getYaw();
            xRot = mc.player.getPitch();
        }
    }

    public static boolean isActive() {
        return active;
    }

    public static void move(double dx, double dy, double dz) {
        x += dx * speed;
        y += dy * speed;
        z += dz * speed;
    }

    public static float getSpeed() {
        return speed;
    }

    public static void setSpeed(float s) {
        speed = s;
    }
}
