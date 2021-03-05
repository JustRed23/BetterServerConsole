package dev.JustRed23.Console.Screen.Screens;

import dev.JustRed23.Console.Screen.Screen;

import static dev.JustRed23.Utils.Logger.log;
import static dev.JustRed23.Console.ConsoleMain.*;

public class OptionsScreen implements Screen {

    public void printScreen() {
        displayHeader("Options");
        log("This is still a work in progress... you will be automatically returned to the main page in 3 seconds");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            exit(e);
        }

        displayScreen(new MainScreen());
    }
}
