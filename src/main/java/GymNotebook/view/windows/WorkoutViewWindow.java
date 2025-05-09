package GymNotebook.view.windows;

import GymNotebook.model.Workout;
import GymNotebook.model.WorkoutService;
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
        System.out.println(WorkoutService.WorkoutToString(workout));
    }

    @Override
    public List<Command> HandleInput(String input){

        return new ArrayList<>();
    }
}
