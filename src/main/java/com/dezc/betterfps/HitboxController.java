package com.dezc.betterfps;

public class HitboxController {
    private static boolean active = false;
    private static float scale = 1.0f;
    private static final float STEP = 0.1f;
    private static final float DEFAULT = 1.0f;

    public static void scaleUp() {
        scale += STEP;
        active = true;
    }

    public static void scaleDown() {
        scale = Math.max(0.1f, scale - STEP);
        active = true;
    }

    public static void reset() {
        scale = DEFAULT;
        active = false;
    }

    public static boolean isActive() {
        return active;
    }

    public static float getScale() {
        return scale;
    }
}
