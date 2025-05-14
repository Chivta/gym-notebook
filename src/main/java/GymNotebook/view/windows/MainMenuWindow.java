package GymNotebook.view.windows;

import GymNotebook.presenter.commands.ICommand;
import GymNotebook.presenter.commands.OpenNewWorkoutCreationWindow;
import GymNotebook.presenter.commands.OpenWorkoutListView;
import GymNotebook.presenter.commands.QuitProgram;

import java.util.ArrayList;
import java.util.List;

public class MainMenuWindow extends Window {
    public MainMenuWindow(){
        header = "Main Menu";
        options.add("New Workout");
        options.add("View Workouts");
        options.add("Quit");
    }

    @Override
    protected void SendBody() {
        SendOptions();
    }

    public List<ICommand> HandleOptionIndex(int index){
        List<ICommand> ICommands = new ArrayList<>();

        switch (index) {
            case 1:
                ICommands.add(new OpenNewWorkoutCreationWindow());
                break;
            case 2:
                ICommands.add(new OpenWorkoutListView());
                break;
            case 3:
                ICommands.add(new QuitProgram());
                break;
        }
        return ICommands;

    }

    @Override
    public List<ICommand> HandleInput(String input) throws WindowException{
        List<ICommand> ICommands = new ArrayList<>();

        ICommands.addAll(TryHandleOptionIndex(input, options));

        return ICommands;
    }
}