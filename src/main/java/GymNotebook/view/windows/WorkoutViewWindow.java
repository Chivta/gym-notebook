package GymNotebook.view.windows;

import GymNotebook.model.Workout;
import GymNotebook.presenter.Presenter;

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
    public void HandleInput(String input){}
}
