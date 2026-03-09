package com.dezc.betterfps;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.*;

public class ConfigManager {

    private static final Path CONFIG_PATH = FabricLoader.getInstance()
        .getConfigDir().resolve("topka_autocommands.json");

    public static void load() {
        if (!Files.exists(CONFIG_PATH)) return;
        try {
            JsonArray arr = JsonParser.parseReader(new FileReader(CONFIG_PATH.toFile())).getAsJsonArray();
            for (int i = 0; i < Math.min(arr.size(), 5); i++) {
                JsonObject obj = arr.get(i).getAsJsonObject();
                AutoCommandsManager.commands[i].enabled = obj.get("enabled").getAsBoolean();
                AutoCommandsManager.commands[i].command = obj.get("command").getAsString();
                AutoCommandsManager.commands[i].delay = obj.get("delay").getAsInt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            JsonArray arr = new JsonArray();
            for (AutoCommandsManager.CommandEntry cmd : AutoCommandsManager.commands) {
                JsonObject obj = new JsonObject();
                obj.addProperty("enabled", cmd.enabled);
                obj.addProperty("command", cmd.command);
                obj.addProperty("delay", cmd.delay);
                arr.add(obj);
            }
            try (FileWriter w = new FileWriter(CONFIG_PATH.toFile())) {
                new GsonBuilder().setPrettyPrinting().create().toJson(arr, w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
