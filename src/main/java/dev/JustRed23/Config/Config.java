package dev.JustRed23.Config;

import java.io.File;

public abstract class Config {

    public static String configLocation;

    public static void setConfigLocation(String location) {
        configLocation = location;
    }
}
