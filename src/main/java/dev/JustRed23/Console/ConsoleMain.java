package dev.JustRed23.Console;

import dev.JustRed23.Utils.Logger;
import org.jetbrains.annotations.Nullable;

public class ConsoleMain {

    public static void main(String[] args) {
        Logger.init();
        Logger.info("Starting...");
        exit(null);
    }

    public static void exit(@Nullable Throwable t) {
        if (t != null)
            Logger.fatal("A fatal error occurred: " + t.getMessage(), t);
        Logger.info("Shutting down...");
        Logger.exit();
        System.exit(0);
    }
}