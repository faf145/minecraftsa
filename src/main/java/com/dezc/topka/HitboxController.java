package com.dezc.topka;

// Управляет реальным масштабом хитбоксов всех сущностей.
// Как в оригинале: Family.class менял AxisAlignedBB размер entity.
// В 1.21.4 делаем через mixin Entity.getDimensions() — масштабируем width/height.
public class HitboxController {

    private static float scale = 1.0f;
    private static final float STEP = 0.1f;
    private static final float MIN  = 0.1f;
    private static final float MAX  = 5.0f;

    public static void increase() {
        scale = Math.min(scale + STEP, MAX);
    }

    public static void decrease() {
        scale = Math.max(scale - STEP, MIN);
    }

    public static void reset() {
        scale = 1.0f;
    }

    public static float getScale() { return scale; }

    public static boolean isDefault() { return scale == 1.0f; }
}
