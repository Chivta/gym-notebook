package GymNotebook.view.windows;

import GymNotebook.model.Workout;
import GymNotebook.presenter.Presenter;


public class NewWorkoutCreationWindow extends Window{
    private final Presenter presenter;
    private State state;
    private final Workout workout;

    enum State {
        TitleInput,
        OptionSelection,
    }

    public NewWorkoutCreationWindow(Presenter presenter, Workout workout) {
        this.presenter = presenter;

        header = "New Workout Creation";

        state = State.TitleInput;

        this.workout = workout;

        options.add("Add Exercise");

    }

    @Override
    public void SendBody(){
        switch (state){
            case TitleInput:
                SendTitleWaiting();
                break;
            case OptionSelection:
                SendOptions();
                break;
        }
    }

    private void SendTitleWaiting(){
        System.out.println("Enter Workout Title");
    }

    @Override
    public void HandleInput(String input) {
        switch (state){
            case TitleInput:
                HandleTitleInput(input);
                break;
            case OptionSelection:
                HandleOptionSelection(input);
                break;
        }
    }

    private void HandleTitleInput(String title){
        workout.setTitle(title);
        state = State.OptionSelection;
    }

    private void HandleOptionSelection(String input){
        try{
            int selected = Integer.parseInt(input);

            switch (selected){
                case 1:
                    presenter.OpenNewExercise();
            }
        }catch (NumberFormatException e){
            info = "ERR: Please enter a valid number!";
        }
    }
}
