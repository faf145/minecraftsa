package com.dezc.topka;

import net.minecraft.client.MinecraftClient;
import java.util.concurrent.*;

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
        for (int i = 0; i < 5; i++) startScheduler(i);
    }

    public static void restartScheduler(int i) {
        schedulers[i].shutdownNow();
        schedulers[i] = Executors.newSingleThreadScheduledExecutor();
        startScheduler(i);
    }

    private static void startScheduler(int i) {
        CommandEntry cmd = commands[i];
        if (!cmd.enabled || cmd.command.trim().isEmpty()) return;
        schedulers[i].scheduleAtFixedRate(() -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player != null)
                mc.execute(() -> mc.player.networkHandler.sendChatMessage(cmd.command));
        }, cmd.delay, cmd.delay, TimeUnit.MINUTES);
    }

    public static class CommandEntry {
        public boolean enabled;
        public String command;
        public int delay;
        public CommandEntry(boolean e, String c, int d) { enabled=e; command=c; delay=d; }
    }
}
