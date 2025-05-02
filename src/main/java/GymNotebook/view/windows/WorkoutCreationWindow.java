package GymNotebook.view.windows;

import GymNotebook.model.Workout;
import GymNotebook.presenter.WorkoutPrinter;
import GymNotebook.presenter.commands.ChangeUnitsForCurrentWorkout;
import GymNotebook.presenter.commands.Command;
import GymNotebook.presenter.commands.OpenNewExercise;
import GymNotebook.presenter.commands.SaveCurrentWorkout;

import java.util.ArrayList;
import java.util.List;


public class WorkoutCreationWindow extends Window{
    private State state;
    private final Workout workout;

    enum State {
        TitleInput,
        OptionSelection,
    }

    public WorkoutCreationWindow(Workout workout) {
        header = "New Workout Creation";

        state = State.TitleInput;

        this.workout = workout;

        options.add("Add Exercise");
        options.add("Save Workout");
        options.add("Change units");
    }

    @Override
    public void SendBody(){
        switch (state){
            case TitleInput:
                SendTitleWaiting();
                break;
            case OptionSelection:
                SendWorkoutOverview();
                SendOptions();
                break;
        }
    }


    private void SendTitleWaiting(){
        System.out.println("Enter Workout Title");
    }

    private void SendWorkoutOverview(){
        WorkoutPrinter.PrintWorkout(workout);
    }

    @Override
    public List<Command> HandleInput(String input) {
        List<Command> commands = new ArrayList<>();

        switch (state){
            case TitleInput:
                HandleTitleInput(input);
                break;
            case OptionSelection:
                commands.addAll( HandleOptionSelection(input));
                break;
        }

        return commands;
    }

    private void HandleTitleInput(String title){
        if(!title.isEmpty()){
            workout.setTitle(title);
            state = State.OptionSelection;
        }
        else{
            info = "ERR: Title cannot be empty";
        }

    }

    private List<Command> HandleOptionSelection(String input){
        List<Command> commands = new ArrayList<Command>();
        try{
            int selected = Integer.parseInt(input);

            switch (selected){
                case 1:
                    commands.add(new OpenNewExercise());
                    break;
                case 2:
                    commands.add(new SaveCurrentWorkout());
                    break;
                case 3:
                    commands.add(new ChangeUnitsForCurrentWorkout());
                    break;
            }
        }catch (NumberFormatException e){
            info = "ERR: Please enter a valid number!";
        }
        return commands;
    }
}
