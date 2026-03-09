package com.dezc.betterfps;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoCommandsManager {

    public static CommandEntry[] commands = new CommandEntry[5];
    private static ScheduledExecutorService[] schedulers = new ScheduledExecutorService[5];

    public static void init() {
        for (int i = 0; i < 5; i++) {
            commands[i] = new CommandEntry(false, "", 5);
            schedulers[i] = Executors.newSingleThreadScheduledExecutor();
        }
        ConfigManager.load();
        startAll();
    }

    public static void startAll() {
        for (int i = 0; i < 5; i++) {
            startScheduler(i);
        }
    }

    public static void restartScheduler(int index) {
        schedulers[index].shutdownNow();
        schedulers[index] = Executors.newSingleThreadScheduledExecutor();
        startScheduler(index);
    }

    private static void startScheduler(int index) {
        CommandEntry cmd = commands[index];
        if (!cmd.enabled || cmd.command.trim().isEmpty()) return;

        schedulers[index].scheduleAtFixedRate(() -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player != null) {
                mc.execute(() -> mc.player.networkHandler.sendChatMessage(cmd.command));
            }
        }, cmd.delay, cmd.delay, TimeUnit.MINUTES);
    }

    public static class CommandEntry {
        public boolean enabled;
        public String command;
        public int delay;

        public CommandEntry(boolean enabled, String command, int delay) {
            this.enabled = enabled;
            this.command = command;
            this.delay = delay;
        }
    }
}
