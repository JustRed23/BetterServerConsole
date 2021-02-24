package dev.JustRed23.Server;

import java.io.File;
import java.util.jar.JarFile;

public class MinecraftServer extends Server {

    public MinecraftServer(String name, String directory, String serverJar) {
        this.name = name;
        this.directory = directory;
        this.serverJar = serverJar;
    }

    public void start(boolean restart) {

    }

    public void stop(boolean force) {

    }

    public void sendCommand(String command) {

    }

    public void startListener() {

    }
}
