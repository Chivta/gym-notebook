package GymNotebook.view.windows;

import GymNotebook.model.Workout;
import GymNotebook.presenter.commands.ICommand;

import java.util.ArrayList;
import java.util.List;

import static GymNotebook.service.WorkoutItemFormatter.WorkoutItemToString;

public class WorkoutViewWindow extends Window{
    Workout workout;

    public WorkoutViewWindow(Workout workout){
        this.workout = workout;

        header = "Workout view";
    }

    @Override
    public void SendBody(){
        System.out.println(WorkoutItemToString(workout));
    }

    @Override
    public List<ICommand> HandleInput(String input){

        return new ArrayList<>();
    }
}
