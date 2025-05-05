package GymNotebook.view.windows;

import GymNotebook.model.Workout;
import GymNotebook.presenter.WorkoutPrinter;
import GymNotebook.presenter.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class WorkoutViewWindow extends Window{
    Workout workout;

    public WorkoutViewWindow(Workout workout){
        this.workout = workout;

        header = "Workout view";
    }

    @Override
    public void SendBody(){
        WorkoutPrinter.PrintWorkout(workout);
    }

    @Override
    public List<Command> HandleInput(String input){
        List<Command> commands = new ArrayList<>();

        return commands;
    }
}
