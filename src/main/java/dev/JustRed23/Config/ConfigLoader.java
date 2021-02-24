package dev.JustRed23.Config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.JustRed23.Server.MinecraftServer;
import dev.JustRed23.Server.Server;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

import static dev.JustRed23.Utils.Logger.*;

public class ConfigLoader {

    @Nullable
    public static List<Server> getServersFromConfig() {
        JsonObject jsonObject = readFromConfig(new File("servers.json"));
        if (jsonObject == null) return null;

        debug("Attempting to create servers from config");
        try {
            JsonArray serverList = jsonObject.getAsJsonArray("servers");
            List<Server> servers = new ArrayList<>();

            for (JsonElement jsonElement : serverList) {
                JsonObject object = jsonElement.getAsJsonObject();
                Server server = new MinecraftServer();
                server.name = object.get("name").getAsString();
                server.directory = object.get("directory").getAsString();
                server.serverJar = new JarFile(new File(server.directory + File.pathSeparator + object.get("jar").getAsString()));
                servers.add(server);
            }

            debug("Successfully created servers");
            return servers;
        } catch (IOException e) {
            error("An error occurred while creating servers: " + e.getMessage(), e);
            return null;
        }
    }

    @Nullable
    private static JsonObject readFromConfig(File configFile) {
        debug("Attempting to read file " + configFile.getName());
        try {
            JsonObject object = (JsonObject) JsonParser.parseReader(new FileReader(configFile));
            debug("Successfully read file " + configFile.getName());
            return object;
        } catch (FileNotFoundException e) {
            error("An error occurred while reading file " + configFile.getName() + ": " + e.getMessage(), e);
            return null;
        }
    }
}
