package com.dezc.betterfps;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class TopkaMod implements ClientModInitializer {

    public static KeyBinding freecamKey;
    public static KeyBinding showInvisKey;
    public static KeyBinding hitboxKey;
    public static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        // FreeCam - Numpad Enter
        freecamKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.topka.freecam",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_KP_ENTER,
            "key.categories.topka"
        ));

        // ShowInvis ON - Numpad 4
        showInvisKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.topka.showinvis_on",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_KP_4,
            "key.categories.topka"
        ));

        // ShowInvis OFF - Numpad 5
        KeyBinding showInvisOffKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.topka.showinvis_off",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_KP_5,
            "key.categories.topka"
        ));

        // Hitbox увеличить - Numpad 7
        KeyBinding hitboxUpKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.topka.hitbox_up",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_KP_7,
            "key.categories.topka"
        ));

        // Hitbox уменьшить - Numpad 8
        hitboxKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.topka.hitbox_down",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_KP_8,
            "key.categories.topka"
        ));

        // Hitbox дефолт - Numpad 9
        KeyBinding hitboxResetKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.topka.hitbox_reset",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_KP_9,
            "key.categories.topka"
        ));

        // AutoCommands GUI - Right Shift
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.topka.open_gui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "key.categories.topka"
        ));

        AutoCommandsManager.init();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (freecamKey.wasPressed()) {
                FreeCamController.toggle();
            }
            while (showInvisKey.wasPressed()) {
                ShowInvisController.setActive(true);
            }
            while (showInvisOffKey.wasPressed()) {
                ShowInvisController.setActive(false);
            }
            while (hitboxUpKey.wasPressed()) {
                HitboxController.scaleUp();
            }
            while (hitboxKey.wasPressed()) {
                HitboxController.scaleDown();
            }
            while (hitboxResetKey.wasPressed()) {
                HitboxController.reset();
            }
            while (openGuiKey.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new AutoCommandsScreen());
                }
            }
        });
    }
}
