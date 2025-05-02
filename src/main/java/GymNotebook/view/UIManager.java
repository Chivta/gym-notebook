package GymNotebook.view;

import GymNotebook.presenter.Presenter;
import GymNotebook.presenter.commands.Command;
import GymNotebook.view.windows.MainMenuWindow;
import GymNotebook.view.windows.Window;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;


public class UIManager {
    @Getter
    @Setter
    private Window CurrentWindow;
    private final Stack<Window> history;
    private final Presenter presenter;

    public UIManager(){
        this.presenter = new Presenter(this);
        CurrentWindow = new MainMenuWindow();
        history = new Stack<>();
    }

    public void Start() {
        Scanner scanner = new Scanner(System.in);
        clearScreen();
        CurrentWindow.Render();

        List<Command> commands = new ArrayList<>();

        while (true) {
            commands.clear();
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.equalsIgnoreCase("b") && !history.isEmpty()){
                    GoBack();
                }
                else{
                    commands = CurrentWindow.HandleInput(line);
                }
                if(!commands.isEmpty()){
                    for(Command command : commands){
                        command.SetPresenter(presenter);
                        command.Execute();
                    }
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

