package GymNotebook.view.windows;

import GymNotebook.presenter.commands.Command;
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

    @Override
    public List<Command> HandleOptionIndex(int index){
        List<Command> commands = new ArrayList<>();

        switch (index) {
            case 1:
                commands.add(new OpenNewWorkoutCreationWindow());
                break;
            case 2:
                commands.add(new OpenWorkoutListView());
                break;
            case 3:
                commands.add(new QuitProgram());
                break;
        }
        return commands;

    }

    @Override
    public List<Command> HandleInput(String input){
        return TryHandleOptionIndex(input);
    }
}