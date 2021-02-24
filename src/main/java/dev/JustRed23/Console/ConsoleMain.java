package dev.JustRed23.Console;

import static dev.JustRed23.Utils.Logger.*;

import dev.JustRed23.Config.Config;
import dev.JustRed23.Config.ConfigLoader;
import dev.JustRed23.Config.ConfigWriter;
import dev.JustRed23.Server.MinecraftServer;
import dev.JustRed23.Server.Server;
import dev.JustRed23.Utils.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

public class ConsoleMain {

    private static File configDirectory;

    public static void main(String[] args) {
        try {
            Logger.init();

            String configPath = System.getProperty("user.dir") + File.separator + "config";

            debug("Current working directory is " + System.getProperty("user.dir"));

            Config.setConfigLocation(configPath);

            configDirectory = new File(configPath);
            if (!configDirectory.exists()) {
                warn("Config directory does not exist... Creating one");
                if (configDirectory.mkdir())
                    info("Created config directory");
                else
                    error("Could not create config directory");
            }

            if (!new File(configDirectory + File.separator + "servers.json").exists())
                ConfigWriter.writeBlankConfig("servers.json");
        } finally {
            List<Server> servers = ConfigLoader.getServersFromConfig();
            servers.forEach(server -> debug("Added server " + server.name + " located at " + server.directory));
        }

        exit(null);
    }

    public static void exit(@Nullable Throwable t) {
        if (t != null)
            fatal("A fatal error occurred: " + t.getMessage(), t);
        info("Shutting down...");
        Logger.exit();
        System.exit(0);
    }
}