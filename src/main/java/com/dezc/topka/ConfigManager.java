package com.dezc.topka;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import java.io.*;
import java.nio.file.*;

public class ConfigManager {
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("topka_autocommands.json");

    public static void load() {
        if (!Files.exists(PATH)) return;
        try {
            JsonArray arr = JsonParser.parseReader(new FileReader(PATH.toFile())).getAsJsonArray();
            for (int i = 0; i < Math.min(arr.size(), 5); i++) {
                JsonObject o = arr.get(i).getAsJsonObject();
                AutoCommandsManager.commands[i].enabled = o.get("enabled").getAsBoolean();
                AutoCommandsManager.commands[i].command = o.get("command").getAsString();
                AutoCommandsManager.commands[i].delay = o.get("delay").getAsInt();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void save() {
        try {
            JsonArray arr = new JsonArray();
            for (AutoCommandsManager.CommandEntry cmd : AutoCommandsManager.commands) {
                JsonObject o = new JsonObject();
                o.addProperty("enabled", cmd.enabled);
                o.addProperty("command", cmd.command);
                o.addProperty("delay", cmd.delay);
                arr.add(o);
            }
            try (FileWriter w = new FileWriter(PATH.toFile())) {
                new GsonBuilder().setPrettyPrinting().create().toJson(arr, w);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
