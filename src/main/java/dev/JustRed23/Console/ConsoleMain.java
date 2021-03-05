package dev.JustRed23.Console;

import static dev.JustRed23.Utils.Logger.*;

import dev.JustRed23.Config.Config;
import dev.JustRed23.Config.ConfigLoader;
import dev.JustRed23.Config.ConfigWriter;
import dev.JustRed23.Console.Screen.Screen;
import dev.JustRed23.Console.Screen.Screens.MainScreen;
import dev.JustRed23.Server.Server;
import dev.JustRed23.Server.ServerManager;
import dev.JustRed23.Utils.LogLevel;
import dev.JustRed23.Utils.Logger;
import dev.JustRed23.Version.Version;
import org.apache.commons.lang3.StringUtils;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiMain;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleMain {

    private static File configDirectory;
    public static final Version CURRENT_VERSION = new Version(0,1);

    private static final ServerManager serverManager = new ServerManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Logger.init(Arrays.asList(args).contains("-debug"));

            info("Starting BetterServerConsole " + CURRENT_VERSION.toString());

            debug("Current working directory is " + System.getProperty("user.dir"));

            String configPath = System.getProperty("user.dir") + File.separator + "config";
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
        } catch (Exception e) {
            exit(e);
        } finally {
            serverManager.init();
        }

        displayScreen(new MainScreen());
        displayHeader("");
    }

    public static void displayHeader(String submenu) {
        String title = " BetterServerConsole " + CURRENT_VERSION.toString() + (!submenu.equals("") ? " - " + submenu : "") + " ";
        String header = StringUtils.repeat('-', title.length());

        log(header);
        log(title);
        log(header);
        log("");
    }

    public static void displayScreen(Screen screen) {
        screen.printScreen();
    }

    public static void exit(@Nullable Throwable t) {
        if (t != null)
            fatal("A fatal error occurred: " + t.getMessage(), t);
        info("Shutting down...");
        scanner.close();
        Logger.exit();
        System.exit(0);
    }

    public static String getAnswer() {
        return scanner.nextLine();
    }

    public static int getYesOrNoAnswer() {
        switch (getAnswer().toLowerCase().charAt(0)) {
            case 'y':
                return 1;
            case 'n':
                return 0;
            default:
                return -1;
        }
    }

    public static int getMultiOptionAnswer(int optionCount) {
        int answer;
        try {
            answer = Integer.parseInt(getAnswer().substring(0, 1));
        } catch (NumberFormatException ignored) {
            return -1;
        }
        return (answer > optionCount || answer <= 0 ? -1 : answer);
    }
}