package com.dezc.betterfps;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class TopkaMod implements ClientModInitializer {

    public static KeyBinding freecamKey;
    public static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        freecamKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.topka.freecam",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_ENTER,
            "key.categories.topka"
        ));

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
            while (openGuiKey.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new AutoCommandsScreen());
                }
            }
        });
    }
}
