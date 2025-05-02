package GymNotebook.view.windows;

import GymNotebook.model.Workout;
import GymNotebook.presenter.commands.Command;
import GymNotebook.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

public class WorkoutViewWindow extends Window{
    Presenter presenter;
    Workout workout;

    public WorkoutViewWindow(Presenter presenter,Workout workout){
        this.presenter = presenter;
        this.workout = workout;

        header = "Workout view";
    }

    @Override
    public void SendBody(){
        presenter.PrintWorkout(workout);
    }

    @Override
    public List<Command> HandleInput(String input){
        List<Command> commands = new ArrayList<>();

        return commands;
    }
}
