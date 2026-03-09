package com.dezc.topka;

public class Hitboxes {
    private static float scale = 1.0f;
    private static final float STEP = 0.1f;

    public static void scaleUp() { scale += STEP; }
    public static void scaleDown() { scale = Math.max(0.1f, scale - STEP); }
    public static void reset() { scale = 1.0f; }
    public static boolean isActive() { return scale != 1.0f; }
    public static float getScale() { return scale; }
}
