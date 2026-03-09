package com.dezc.topka;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class TopkaMod implements ClientModInitializer {

    // Numpad Enter - freecam toggle
    public static KeyBinding toggleFreeCam;
    // Numpad 4 - ShowInvis ON
    public static KeyBinding showInvisOn;
    // Numpad 5 - ShowInvis OFF
    public static KeyBinding showInvisOff;
    // Numpad 7 - Hitbox увеличить
    public static KeyBinding hitboxUp;
    // Numpad 8 - Hitbox уменьшить
    public static KeyBinding hitboxDown;
    // Numpad 9 - Hitbox сброс (дефолт)
    public static KeyBinding hitboxReset;
    // Right Shift - AutoCommands GUI
    public static KeyBinding openGui;

    @Override
    public void onInitializeClient() {
        toggleFreeCam = reg("key.topka.freecam",     GLFW.GLFW_KEY_KP_ENTER);
        showInvisOn    = reg("key.topka.showinvis_on",  GLFW.GLFW_KEY_KP_4);
        showInvisOff   = reg("key.topka.showinvis_off", GLFW.GLFW_KEY_KP_5);
        hitboxUp       = reg("key.topka.hitbox_up",     GLFW.GLFW_KEY_KP_7);
        hitboxDown     = reg("key.topka.hitbox_down",   GLFW.GLFW_KEY_KP_8);
        hitboxReset    = reg("key.topka.hitbox_reset",  GLFW.GLFW_KEY_KP_9);
        openGui        = reg("key.topka.open_gui",      GLFW.GLFW_KEY_RIGHT_SHIFT);

        AutoCommandsManager.init();

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            while (toggleFreeCam.wasPressed()) FreeCamController.toggle();
            while (showInvisOn.wasPressed())   ShowInvisController.setActive(true);
            while (showInvisOff.wasPressed())  ShowInvisController.setActive(false);
            while (hitboxUp.wasPressed())      HitboxController.increase();
            while (hitboxDown.wasPressed())    HitboxController.decrease();
            while (hitboxReset.wasPressed())   HitboxController.reset();
            while (openGui.wasPressed()) {
                if (mc.player != null) mc.setScreen(new AutoCommandsScreen());
            }
        });
    }

    private KeyBinding reg(String id, int glfw) {
        return KeyBindingHelper.registerKeyBinding(
            new KeyBinding(id, InputUtil.Type.KEYSYM, glfw, "key.categories.topka")
        );
    }
}
