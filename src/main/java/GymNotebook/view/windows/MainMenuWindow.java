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
    public List<Command> HandleInput(String input){
        List<Command> commands = new ArrayList<>();
        try{
            int selected = Integer.parseInt(input);
            if (selected > 0 && selected <= options.size()) {
                switch (selected) {
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
            } else {
                info = "ERR: Invalid option number";
            }
        }
        catch(NumberFormatException e){
            info = "ERR: Invalid input. Please enter a number.";
        }

        return commands;

    }
}