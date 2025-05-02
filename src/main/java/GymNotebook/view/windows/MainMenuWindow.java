package GymNotebook.view.windows;

import GymNotebook.presenter.Presenter;
import GymNotebook.presenter.commands.OpenMainWindow;
import GymNotebook.presenter.Command;
import GymNotebook.presenter.commands.OpenWorkoutViewWindow;

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

    public Command HandleInput(String input){
        input = input.toLowerCase();
        Command command = null;
        try{
            int selected = Integer.parseInt(input);
            if (selected > 0 && selected <= options.size()) {
                switch (selected) {
                    case 1:
                        command = new OpenWorkoutViewWindow();
//                    case 2:
//                        presenter.OpenWorkoutListView();
//                        break;
//                    case 3:
//                        presenter.Quit();
                }
            } else {
                info = "ERR: Invalid option number";
            }
        }
        catch(NumberFormatException e){
            info = "ERR: Invalid input. Please enter a number.";
        }

        return command;

    }
}