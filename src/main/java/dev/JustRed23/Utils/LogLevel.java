package dev.JustRed23.Utils;

import org.fusesource.jansi.Ansi;

public enum LogLevel {

    INFO(Ansi.Color.DEFAULT),
    WARN(Ansi.Color.YELLOW),
    ERROR(Ansi.Color.RED, true),
    FATAL(Ansi.Color.RED),
    DEBUG(Ansi.Color.CYAN, true),
    DEFAULT(Ansi.Color.DEFAULT);


    private Ansi.Color color;
    private boolean bold;

    LogLevel(Ansi.Color color) {
        this.color = color;
        this.bold = false;
    }

    LogLevel(Ansi.Color color, boolean bold) {
        this.color = color;
        this.bold = bold;
    }

    public Ansi.Color getColor() {
        return color;
    }

    public boolean isBold() {
        return bold;
    }
}
