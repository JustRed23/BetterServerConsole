package dev.JustRed23.Server;

import java.util.jar.JarFile;

public abstract class Server {

    public String name;
    public String directory;
    public String serverJar;

    public void start(boolean restart) {}

    public void stop(boolean force) {}

    public void sendCommand(String command) {}

    public void startListener() {}
}
