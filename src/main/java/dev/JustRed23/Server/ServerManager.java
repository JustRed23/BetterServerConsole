package dev.JustRed23.Server;

import dev.JustRed23.Config.ConfigLoader;
import dev.JustRed23.Config.ConfigWriter;
import dev.JustRed23.Console.ConsoleMain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static dev.JustRed23.Utils.Logger.*;

public class ServerManager {

    private static List<Server> servers = new ArrayList<>();

    public void init() {
        info("Loading servers... this will take a moment");
        List<Server> servers = ConfigLoader.getServersFromConfig();

        if (!servers.isEmpty()) {
            servers.forEach(ServerManager::addServer);
            return;
        }

        int answer;
        do {
            warn("There are no servers to load! Would you like to create one now? (Y/N)");
            answer = ConsoleMain.getYesOrNoAnswer();
        } while (answer == -1);

        if (answer == 1) {
            createServer();
            ConfigWriter.writeServerConfig(ServerManager.getServers());
        }
    }

    private void createServer() {
        info("Give your server a name: ");
        String serverName = ConsoleMain.getAnswer();
        info("Enter the directory where the server is located (Ex. C:\\Users\\username\\Desktop\\MinecraftServer)");
        String serverDir = ConsoleMain.getAnswer();
        info("Enter the name of the server jar file (Ex. server.jar)");
        String serverJar = ConsoleMain.getAnswer();
        if (!serverJar.endsWith(".jar")) serverJar += ".jar";

        int create;
        do {
            info("Do you want to create the server with name: " + serverName + ", directory: " + serverDir + " and jar file: " + serverJar + "? (Y/N)");
            create = ConsoleMain.getYesOrNoAnswer();
        } while (create == -1);

        if (create == 1)
            addServer(new MinecraftServer(serverName, serverDir, serverJar));
        else
            createServer();
    }

    public static void addServer(Server server) {
        servers.add(server);
        debug("Added server " + server.name + " located at " + server.directory);
    }

    public static void removeServer(Server server, boolean delete) {
        servers.remove(server);

        if (delete) {
            try {
                server.stop(true);
            } finally {
                File serverDir = new File(server.directory);

                if (serverDir.delete())
                    info("Server " + server.name + " has been deleted");
                else
                    warn("Server directory " + server.directory + " could not be deleted");
            }
        }

        debug("Removed server " + server.name + " located at " + server.directory + (delete ? " and deleted contents" : ""));
    }

    public static Server getServerByName(String serverName) {
        return servers.stream().filter(server -> server.name.equals(serverName)).findFirst().get();
    }

    public static Server getServerByDirectory(String serverDir) {
        return servers.stream().filter(server -> server.directory.equals(serverDir)).findFirst().get();
    }

    public static List<Server> getServers() {
        return servers;
    }
}
