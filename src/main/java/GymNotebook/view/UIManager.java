package GymNotebook.view;

import GymNotebook.presenter.Presenter;
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

    public UIManager(){
        Presenter presenter = new Presenter(this);
        CurrentWindow = new MainMenuWindow(presenter);
    }

    public void Start() {
        Scanner scanner = new Scanner(System.in);
        clearScreen();
        CurrentWindow.Render();
        while (true) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                CurrentWindow.HandleInput(line);

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

