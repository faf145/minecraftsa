package com.dezc.topka;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class TopkaMod implements ClientModInitializer {

    public static KeyBinding freecamKey;
    public static KeyBinding showInvisOnKey;
    public static KeyBinding showInvisOffKey;
    public static KeyBinding hitboxUpKey;
    public static KeyBinding hitboxDownKey;
    public static KeyBinding hitboxResetKey;
    public static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        freecamKey = reg("key.topka.freecam", GLFW.GLFW_KEY_KP_ENTER);
        showInvisOnKey = reg("key.topka.showinvis_on", GLFW.GLFW_KEY_KP_4);
        showInvisOffKey = reg("key.topka.showinvis_off", GLFW.GLFW_KEY_KP_5);
        hitboxUpKey = reg("key.topka.hitbox_up", GLFW.GLFW_KEY_KP_7);
        hitboxDownKey = reg("key.topka.hitbox_down", GLFW.GLFW_KEY_KP_8);
        hitboxResetKey = reg("key.topka.hitbox_reset", GLFW.GLFW_KEY_KP_9);
        openGuiKey = reg("key.topka.open_gui", GLFW.GLFW_KEY_RIGHT_SHIFT);

        AutoCommandsManager.init();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (freecamKey.wasPressed()) FreeCam.toggle();
            while (showInvisOnKey.wasPressed()) ShowInvis.setActive(true);
            while (showInvisOffKey.wasPressed()) ShowInvis.setActive(false);
            while (hitboxUpKey.wasPressed()) Hitboxes.scaleUp();
            while (hitboxDownKey.wasPressed()) Hitboxes.scaleDown();
            while (hitboxResetKey.wasPressed()) Hitboxes.reset();
            while (openGuiKey.wasPressed()) {
                if (client.player != null)
                    client.setScreen(new AutoCommandsScreen());
            }
        });
    }

    private KeyBinding reg(String id, int key) {
        return KeyBindingHelper.registerKeyBinding(
            new KeyBinding(id, InputUtil.Type.KEYSYM, key, "key.categories.topka")
        );
    }
}
