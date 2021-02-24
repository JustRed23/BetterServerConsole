package dev.JustRed23.Config;

import dev.JustRed23.Utils.Logger;

import java.io.File;

public abstract class Config {

    public static String configLocation;

    public static void setConfigLocation(String location) {
        Logger.debug("Config location set to " + location);
        configLocation = location;
    }
}
