package GymNotebook.view.windows;

import GymNotebook.presenter.Presenter;

import java.util.ArrayList;

public class MainMenuWindow extends Window {

    private final ArrayList<String> options;
    private final Presenter presenter;
    private String info;

    public MainMenuWindow(Presenter presenter){
        this.presenter = presenter;

        header = "Main Menu";
        footer = "Enter option number to open";
        options = new ArrayList<>();
        options.add("New Workout");
        options.add("View Workouts");
        info = "";
    }

    @Override
    protected void SendBody() {
        for(int i = 0; i < options.size(); i++){
            System.out.printf("%d. %s%n", i+1, options.get(i));
        }
    }

    public void HandleInput(String input){
        info = "";

        try{
            int selected = Integer.parseInt(input);
            selected -= 1;
            if (selected >= 0 && selected < options.size()) {
                switch (selected){
                    case 0:
                        presenter.OpenNewWorkoutCreation();
                        break;
                    case 1:
                        presenter.OpenWorkoutListView();
                        break;
                }
            } else {
                info = "ERR: Invalid option number";
            }
        }
        catch(NumberFormatException e){
            info = "ERR: Invalid input. Please enter a number.";
        }
    }
}