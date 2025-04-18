package GymNotebook.view.windows;

import GymNotebook.presenter.Presenter;

public class MainMenuWindow extends Window {
    private final Presenter presenter;

    public MainMenuWindow(Presenter presenter){
        this.presenter = presenter;

        header = "Main Menu";
        options.add("New Workout");
        options.add("View Workouts");
        options.add("Quit");
    }

    @Override
    protected void SendBody() {
        SendOptions();
    }

    public void HandleInput(String input){
        input = input.toLowerCase();

        try{
            int selected = Integer.parseInt(input);
            if (selected > 0 && selected <= options.size()) {
                switch (selected){
                    case 1:
                        presenter.OpenNewWorkoutCreation();
                        break;
                    case 2:
                        presenter.OpenWorkoutListView();
                        break;
                    case 3:
                        presenter.Quit();
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