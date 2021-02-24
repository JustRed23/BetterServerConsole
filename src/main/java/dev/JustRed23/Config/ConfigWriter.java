package dev.JustRed23.Config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.JustRed23.Console.ConsoleMain;
import dev.JustRed23.Server.Server;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static dev.JustRed23.Utils.Logger.*;

public class ConfigWriter extends Config {

    public static boolean writeServerConfig(@NotNull List<Server> servers) {
        JsonObject json = new JsonObject();

        JsonArray serverList = new JsonArray();

        for (Server server : servers) {
            JsonObject serverObj = new JsonObject();
            serverObj.addProperty("name", server.name);
            serverObj.addProperty("directory", server.directory);
            serverObj.addProperty("jar", server.serverJar);
            serverList.add(serverObj);
        }

        json.add("servers", serverList);

        return writeConfig(json.toString(), "servers.json");
    }

    public static void writeBlankConfig(String fileName) {
        writeConfig(new JsonObject().toString(), fileName);
    }

    private static boolean writeConfig(String json, String fileName) {
        if (configLocation == null)
            ConsoleMain.exit(new IllegalStateException("Config location directory must be specified before writing a config file!"));

        File configFile = new File(configLocation + File.separator + fileName);

        debug("Attempting to write file " + configFile.getName() + " with content: " + json);
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(json);
            writer.flush();
            debug("Successfully written file " + configFile.getName() + " to " + configFile.getAbsolutePath());
            return true;
        } catch (IOException e) {
            error("An error occurred while writing file " + configFile.getName(), e);
            return false;
        }
    }
}
