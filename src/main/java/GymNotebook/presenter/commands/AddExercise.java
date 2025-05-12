package GymNotebook.presenter.commands;

import GymNotebook.model.Exercise;
import GymNotebook.model.ExerciseService;
import GymNotebook.presenter.Presenter;

public class AddExercise implements Command{
    private Presenter presenter;

    public void SetPresenter(Presenter presenter){
        this.presenter = presenter;
    }

    public void Execute(){
        presenter.AddExerciseToCurrentWorkout();
    }
}