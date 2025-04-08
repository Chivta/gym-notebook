package GymNotebook.view;

import GymNotebook.view.windows.MainMenuWindow;
import GymNotebook.view.windows.Window;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;


public class UIManager {
    @Getter
    @Setter
    private Window CurrentWindow;
    private Scanner scanner;

    public UIManager(){
        CurrentWindow = new MainMenuWindow();
    }

    public void Start() {
        scanner = new Scanner(System.in);
        clearScreen();
        CurrentWindow.Render();
        while (true) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.equals("exit")){
                    CurrentWindow.HandleEnter();
                }
                else{
                    CurrentWindow.HandleInput(line);
                }

                clearScreen();
                System.out.printf("%n");
                CurrentWindow.Render();
            }

        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

