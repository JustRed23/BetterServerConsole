package dev.JustRed23.Console.Screen.Screens;

import dev.JustRed23.Console.Screen.Screen;
import dev.JustRed23.Server.Server;
import dev.JustRed23.Server.ServerManager;

import java.util.List;

import static dev.JustRed23.Utils.Logger.log;
import static dev.JustRed23.Console.ConsoleMain.*;

public class ServerManageScreen implements Screen {

    private Server server;

    public ServerManageScreen(Server server) {
        this.server = server;
    }

    public void printScreen() {
        displayHeader("Manage server " + server.name);
        log("Select an option:");

        log("\t1 - Start server");
        log("\t2 - Stop server");
        log("\t3 - Delete server");
        log("\t4 - Back");

        int answer;
        do {
            answer = getMultiOptionAnswer(4);
        } while (answer == -1);

        switch (answer) {
            case 1:
                server.start(false);
                break;
            case 2:
                server.stop(false);
                break;
            case 3:
                ServerManager.removeServer(server, true);
                displayScreen(new ServerScreen());
                break;
            case 4:
                displayScreen(new ServerScreen());
        }
    }
}
