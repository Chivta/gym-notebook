package GymNotebook.view;

import GymNotebook.presenter.Presenter;
import GymNotebook.presenter.commands.ICommand;
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
    private final List<ICommand> commands;

    public UIManager(){
        this.presenter = new Presenter(this);
        CurrentWindow = new MainMenuWindow();
        history = new Stack<>();
        commands = new ArrayList<>();
    }

    public void Start() {
        Scanner scanner = new Scanner(System.in);
        clearScreen();
        CurrentWindow.Render();

        while (true) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                commands.addAll(CurrentWindow.AcceptInput(line));

                RunCommands();

                clearScreen();
                CurrentWindow.Render();

            }

        }
    }

    private void RunCommands(){
        if(!commands.isEmpty()){
            for(ICommand ICommand : commands){
                ICommand.SetPresenter(presenter);
                ICommand.Execute();
            }
        }
        commands.clear();
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

