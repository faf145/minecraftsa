package com.dezc.betterfps;

public class ShowInvisController {
    private static boolean active = false;

    public static void setActive(boolean value) {
        active = value;
    }

    public static boolean isActive() {
        return active;
    }
}
