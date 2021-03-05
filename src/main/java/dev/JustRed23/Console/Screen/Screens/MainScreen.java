package dev.JustRed23.Console.Screen.Screens;

import dev.JustRed23.Console.ConsoleMain;
import dev.JustRed23.Console.Screen.Screen;
import org.apache.commons.lang3.StringUtils;

import static dev.JustRed23.Utils.Logger.log;
import static dev.JustRed23.Console.ConsoleMain.*;

public class MainScreen implements Screen {

    public void printScreen() {
        displayHeader("");
        log("Select an option:");
        log("\t1 - Server Management");
        log("\t2 - Options");
        log("\t3 - Quit");

        int answer;
        do {
            answer = getMultiOptionAnswer(3);
        } while (answer == -1);

        switch (answer) {
            case 1:
                displayScreen(new ServerScreen());
                break;
            case 2:
                displayScreen(new OptionsScreen());
                break;
            case 3:
                ConsoleMain.exit(null);
                break;
        }
    }
}
