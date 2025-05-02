package GymNotebook.presenter.commands;

import GymNotebook.model.Exercise;
import GymNotebook.presenter.Presenter;

public class AddExerciseToCurrentWorkout  implements Command{
    private Presenter presenter;
    private Exercise exercise;

    public void SetExercise(Exercise exercise){
        this.exercise = exercise;
    }

    public void SetPresenter(Presenter presenter){
        this.presenter = presenter;
    }

    public void Execute(){
        presenter.AddExerciseToCurrentWorkout(exercise);
    }
}