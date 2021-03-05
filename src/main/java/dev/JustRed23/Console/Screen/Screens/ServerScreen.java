package dev.JustRed23.Console.Screen.Screens;

import dev.JustRed23.Console.Screen.Screen;
import dev.JustRed23.Server.Server;
import dev.JustRed23.Server.ServerManager;

import java.util.List;

import static dev.JustRed23.Utils.Logger.log;
import static dev.JustRed23.Console.ConsoleMain.*;

public class ServerScreen implements Screen {

    public void printScreen() {
        List<Server> servers = ServerManager.getServers();

        displayHeader("Servers");
        log("Select an option:");

        int i;
        for (i = 0; i < servers.size(); i++)
            log("\t" + (i + 1) + " - " + servers.get(i).name);

        log("\t" + (i + 1) + " - Back");

        int answer;
        do {
            answer = getMultiOptionAnswer(i + 1);
        } while (answer == -1);

        if (answer == (i + 1))
            displayScreen(new MainScreen());
        else
            displayScreen(new ServerManageScreen(servers.get(i)));
    }
}
