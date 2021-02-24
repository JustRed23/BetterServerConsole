package dev.JustRed23.Utils;

import dev.JustRed23.Console.ConsoleMain;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

import static org.fusesource.jansi.Ansi.*;
import static dev.JustRed23.Utils.AnsiUtils.*;
import static dev.JustRed23.Utils.LogLevel.*;

public class Logger {

    private static boolean logAnsi;

    public static void init() {
        logAnsi = (System.getenv("LogAnsi") != null && Boolean.parseBoolean(System.getenv("LogAnsi")));
        if (!logAnsi)
            AnsiConsole.systemInstall();
    }

    public static void exit() {
        AnsiConsole.systemUninstall();
    }

    public static void log(LogLevel logLevel, Object message) {
        if (!logAnsi) {
            Ansi ansi = ansi();
            ansi.a("[");
            if (logLevel.isBold()) ansi.bold();
            ansi.fg(logLevel.getColor()).a(logLevel.name()).boldOff().reset().a("] ").fg(logLevel.getColor());
            if (logLevel.isBold()) ansi.bold();
            System.out.println(ansi.a(message).boldOff().reset());
        } else {
            AnsiUtils ansi = ansiUtils();
            ansi.a("[");
            if (logLevel.isBold()) ansi.bold();
            ansi.fg(logLevel.getColor()).a(logLevel.name()).boldOff().reset().a("] ").fg(logLevel.getColor());
            if (logLevel.isBold()) ansi.bold();
            System.out.println(ansi.a(message).boldOff().reset());
        }
    }

    public static void logStackTrace(LogLevel logLevel, Throwable t) {
        if (t == null)
            return;

        if (!logAnsi) {
            Ansi ansi = ansi().bold();
            ansi.fg(logLevel.getColor());
            ansi.a(t).newline();
            Arrays.stream(t.getStackTrace()).forEach(stackTraceElement -> ansi.a("\tat " + stackTraceElement).newline());
            System.out.println(ansi.reset());
        } else {
            AnsiUtils ansi = ansiUtils().bold();
            ansi.fg(logLevel.getColor());
            ansi.a(t).newline();
            Arrays.stream(t.getStackTrace()).forEach(stackTraceElement -> ansi.a("\tat " + stackTraceElement).newline());
            System.out.println(ansi.reset());
        }
    }

    private static void log(LogLevel logLevel, Object message, Throwable t) {
        log(logLevel, message);
        logStackTrace(logLevel, t);
    }

    public static void info(Object message) {
        log(INFO, message);
    }

    public static void warn(Object message) {
        log(WARN, message);
    }

    public static void error(Object message) {
        error(message, null);
    }

    public static void error(Object message, Throwable t) {
        log(ERROR, message, t);
    }

    public static void fatal(Object message) {
        fatal(message, null);
    }

    public static void fatal(Object message, Throwable t) {
        log(FATAL, message, t);
    }

    public static void debug(Object message) {
        log(DEBUG, message);
    }
}
