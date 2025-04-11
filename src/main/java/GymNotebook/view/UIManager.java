package GymNotebook.view;

import GymNotebook.presenter.Presenter;
import GymNotebook.view.windows.MainMenuWindow;
import GymNotebook.view.windows.Window;
import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;
import java.util.Stack;


public class UIManager {
    @Getter
    @Setter
    private Window CurrentWindow;
    private final Stack<Window> history;

    public UIManager(){
        Presenter presenter = new Presenter(this);
        CurrentWindow = new MainMenuWindow(presenter);
        history = new Stack<Window>();
    }

    public void Start() {
        Scanner scanner = new Scanner(System.in);
        clearScreen();
        CurrentWindow.Render();
        while (true) {


            if (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.equalsIgnoreCase("b") && !history.isEmpty()){
                    GoBack();
                }
                else{
                    CurrentWindow.HandleInput(line);
                }
                clearScreen();
                CurrentWindow.Render();

            }

        }
    }

    public void GoBack(){
        CurrentWindow = history.pop();
    }

    public void ChangeWindow(Window newWindow){
        history.push(CurrentWindow);
        CurrentWindow = newWindow;
    }

    public void ClearHistory(){
        history.clear();
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

