package dev.JustRed23.Config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.JustRed23.Console.ConsoleMain;
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

public class ConfigLoader extends Config {

    public static List<Server> getServersFromConfig() {
        if (configLocation == null)
            ConsoleMain.exit(new IllegalStateException("Config location directory must be specified before loading a config file!"));

        JsonObject jsonObject = readFromConfig(new File(configLocation + File.separator + "servers.json"));
        if (jsonObject == null)
            return new ArrayList<>();

        debug("Attempting to create servers from config");
        try {
            JsonArray serverList = jsonObject.getAsJsonArray("servers");
            List<Server> servers = new ArrayList<>();

            if (serverList == null) {
                debug("There are no servers to load");
                return new ArrayList<>();
            }

            for (JsonElement jsonElement : serverList) {
                JsonObject object = jsonElement.getAsJsonObject();
                Server server = new MinecraftServer(
                        object.get("name").getAsString(),
                        object.get("directory").getAsString(),
                        object.get("jar").getAsString());
                servers.add(server);
            }

            return servers;
        } catch (Exception e) {
            error("An error occurred while creating servers", e);
            return new ArrayList<>();
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
            error("An error occurred while reading file " + configFile.getName(), e);
            return null;
        }
    }
}
